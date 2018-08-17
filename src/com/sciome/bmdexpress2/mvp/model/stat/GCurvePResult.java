package com.sciome.bmdexpress2.mvp.model.stat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GCurvePResult extends StatResult
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2275549172312801367L;

	private double				BMDauc;
	private double				BMDLauc;
	private double				BMDUauc;
	private double				BMDwAuc;
	private double				BMDLwAuc;
	private double				BMDUwAuc;
	private List<Float>			correctedDoseResponseValues;

	public GCurvePResult()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> getColumnNames()
	{
		return new ArrayList<String>(Arrays.asList("GCurveP BMD", "GCurveP BMDL", "GCurveP BMDU",
				"GCurveP fitValue", "GCurveP BMD AUC", "GCurveP BMDL AUC", "GCurveP BMDU AUC",
				"GCurveP BMD wAUC", "GCurveP BMDL wAUC", "GCurveP BMDU wAUC",

				"GCurveP adverseDirection", "GCurveP BMD/BMDL", "GCurveP Execution Complete"));

	}

	@Override
	public List<Object> getRow()
	{

		return new ArrayList<Object>(Arrays.asList((this.getBMD()), (this.getBMDL()), (this.getBMDU()),
				(this.getFitPValue()), (this.getBMDauc()), (this.getBMDLauc()), (this.getBMDUauc()),
				(this.getBMDwAuc()), (this.getBMDLwAuc()), (this.getBMDUwAuc()), (this.getAdverseDirection()),
				(this.getBMDdiffBMDL()), this.getSuccess()));
	}

	@Override
	public String toString()
	{
		return "GCurveP";
	}

	@Override
	public List<String> getParametersNames()
	{
		return new ArrayList<String>();
	}

	public double getBMDauc()
	{
		return BMDauc;
	}

	public void setBMDauc(double bMDauc)
	{
		BMDauc = bMDauc;
	}

	public double getBMDLauc()
	{
		return BMDLauc;
	}

	public void setBMDLauc(double bMDLauc)
	{
		BMDLauc = bMDLauc;
	}

	public double getBMDUauc()
	{
		return BMDUauc;
	}

	public void setBMDUauc(double bMDUauc)
	{
		BMDUauc = bMDUauc;
	}

	public double getBMDwAuc()
	{
		return BMDwAuc;
	}

	public void setBMDwAuc(double bMDwAuc)
	{
		BMDwAuc = bMDwAuc;
	}

	public double getBMDLwAuc()
	{
		return BMDLwAuc;
	}

	public void setBMDLwAuc(double bMDLwAuc)
	{
		BMDLwAuc = bMDLwAuc;
	}

	public double getBMDUwAuc()
	{
		return BMDUwAuc;
	}

	public void setBMDUwAuc(double bMDUwAuc)
	{
		BMDUwAuc = bMDUwAuc;
	}

	public List<Float> getCorrectedDoseResponseValues()
	{
		return correctedDoseResponseValues;
	}

	public void setCorrectedDoseResponseValues(List<Float> correctedDoseResponseValues)
	{
		this.correctedDoseResponseValues = correctedDoseResponseValues;
	}

}
