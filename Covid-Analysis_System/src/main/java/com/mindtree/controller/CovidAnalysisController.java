package com.mindtree.controller;

import java.util.List;
import java.util.Map;

import com.mindtree.exceptions.ServiceException;
import com.mindtree.model.Player;
import com.mindtree.service.CovidAnalysisService;

public class CovidAnalysisController {

	CovidAnalysisService covidAnalysisService;

	public CovidAnalysisController() throws ServiceException {
		covidAnalysisService = new CovidAnalysisService();
	}

	public List<String> getStateNames() throws ServiceException {
		return covidAnalysisService.getStateCode();
	}

	public List<String> getDistrictName(String stateCode) throws ServiceException {
		return covidAnalysisService.getDistrictNames(stateCode);
	}

	public List<Player> getDataForDateRange(String startDate, String endDate) throws ServiceException {
		return covidAnalysisService.getDataForDateRange(startDate, endDate);

	}

	public Map<String, List<Player>> getDataForTwoStatesForGivenDateRange(String startDate, String endDate,
			String firstState, String secondState) throws ServiceException {
		return covidAnalysisService.getDataForTwoStatesForGivenDateRange(startDate, endDate, firstState, secondState);

	}
           
	public void end() {
		covidAnalysisService.end();		
	}
}
