package com.sciome.bmdexpress2.mvp.view.mainstage.dataview;

import com.sciome.bmdexpress2.mvp.model.BMDExpressAnalysisDataSet;
import com.sciome.bmdexpress2.mvp.model.probe.ProbeResponse;
import com.sciome.bmdexpress2.mvp.presenter.mainstage.dataview.ExpressionDataSetDataViewPresenter;
import com.sciome.bmdexpress2.mvp.view.visualization.DataVisualizationView;
import com.sciome.bmdexpress2.mvp.view.visualization.PCADataVisualizationView;
import com.sciome.bmdexpress2.mvp.viewinterface.mainstage.dataview.IBMDExpressDataView;
import com.sciome.bmdexpress2.shared.eventbus.BMDExpressEventBus;

/*
 * Show the Expression Data.  currently this implementation
 * deals with its own table view because DoseResponseExperiment
 */
public class ExpressionDataSetDataView extends BMDExpressDataView<ProbeResponse>
		implements IBMDExpressDataView
{

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ExpressionDataSetDataView(BMDExpressAnalysisDataSet doseResponseExperiement, String viewTypeKey)
	{
		super(ProbeResponse.class, doseResponseExperiement, viewTypeKey);

		presenter = new ExpressionDataSetDataViewPresenter(this, BMDExpressEventBus.getInstance());

		if (doseResponseExperiement.getColumnHeader().size() == 0)
			return;
		setUpTableView(doseResponseExperiement);
		presenter.showVisualizations(doseResponseExperiement);
		
		//Disable filtering options for now
		splitPane.getItems().remove(filtrationNode);
		hideFilter.setDisable(true);
		enableFilterCheckBox.setDisable(true);

	}

	@Override
	protected DataVisualizationView getDataVisualizationView()
	{
		return new PCADataVisualizationView();
	}
}