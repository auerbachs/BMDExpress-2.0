/**
 * PowerFitThread.java
 *
 *
 */

package com.sciome.bmdexpress2.util.bmds.thread;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang3.RandomUtils;

import com.sciome.bmdexpress2.mvp.model.probe.ProbeResponse;
import com.sciome.bmdexpress2.mvp.model.stat.PowerResult;
import com.sciome.bmdexpress2.mvp.model.stat.StatResult;
import com.sciome.bmdexpress2.util.bmds.FilePowerFit;
import com.sciome.bmdexpress2.util.bmds.ModelInputParameters;

public class PowerFitThread extends Thread implements IFitThread
{
	private CountDownLatch			cdLatch;
	private FilePowerFit			fPowerFit			= null;

	private ModelInputParameters	inputParameters;

	private float[]					doses;

	private final int[]				adversDirections	= { 0, 1, -1 };
	private List<ProbeResponse>		probeResponses;
	private List<StatResult>		powerResults;
	private int						numThread;
	private int						instanceIndex;
	private IModelProgressUpdater	progressUpdater;
	private IProbeIndexGetter		probeIndexGetter;
	private boolean					cancel				= false;

	public PowerFitThread(CountDownLatch cdLatch, List<ProbeResponse> probeResponses,
			List<StatResult> powerResults, int numThread, int instanceIndex, int killTime,
			IModelProgressUpdater progressUpdater, IProbeIndexGetter probeIndexGetter)
	{
		this.progressUpdater = progressUpdater;
		this.cdLatch = cdLatch;
		this.probeResponses = probeResponses;
		this.powerResults = powerResults;
		this.numThread = numThread;
		this.instanceIndex = instanceIndex;
		this.probeIndexGetter = probeIndexGetter;

		fPowerFit = new FilePowerFit(killTime);

	}

	public void setFilePowerFit(FilePowerFit fPowerFit)
	{
		this.fPowerFit = fPowerFit;
	}

	public void setDoses(float[] doses)
	{
		this.doses = doses;
	}

	public void setObjects(ModelInputParameters inputParameters)
	{
		this.inputParameters = inputParameters;
	}

	@Override
	public void run()
	{
		if (fPowerFit != null)
		{
			filedPowerFit();
		}

		try
		{
			cdLatch.countDown();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void filedPowerFit()
	{
		inputParameters.setAdversDirection(adversDirections[0]);

		Random rand = new Random(System.nanoTime());
		int randInt = Math.abs(rand.nextInt());

		Integer probeIndex = probeIndexGetter.getNextProbeIndex();
		while (probeIndex != null)
		{

			PowerResult powerResult = (PowerResult) powerResults.get(probeIndex);

			if (cancel)
			{
				break;
			}

			try
			{
				String id = probeResponses.get(probeIndex).getProbe().getId().replaceAll("\\s", "_");
				id = String.valueOf(Math.abs(id.hashCode()))
						+ String.valueOf(Math.abs(RandomUtils.nextInt()));
				float[] responses = probeResponses.get(probeIndex).getResponseArray();
				double[] results = fPowerFit.fitModel(String.valueOf(randInt) + "_" + id, inputParameters,
						doses, responses);

				if (results != null)
				{
					fillOutput(results, powerResult);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			this.progressUpdater.incrementModelsComputed();
			probeIndex = probeIndexGetter.getNextProbeIndex();
		}
	}

	private void fillOutput(double[] results, PowerResult powerResult)
	{
		powerResult.setBMD(results[0]);
		powerResult.setBMDL(results[1]);
		powerResult.setBMDU(results[2]);
		powerResult.setFitPValue(results[3]);
		powerResult.setFitLogLikelihood(results[4]);
		powerResult.setAIC(results[5]);

		int direction = 1;

		if (results[7] < 0)
		{
			direction = -1;
		}
		powerResult.setCurveParameters(Arrays.copyOfRange(results, 6, results.length));
		powerResult.setAdverseDirection((short) direction);
		powerResult.setSuccess("" + fPowerFit.isSuccess());
	}

	@Override
	public void cancel()
	{
		cancel = true;
	}
}
