package com.mindtree.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mindtree.dao.CovidAnalysisDao;
import com.mindtree.dao.Dao;
import com.mindtree.exceptions.InvalidDateException;
import com.mindtree.exceptions.InvalidDateRangeException;
import com.mindtree.exceptions.InvalidStateCodeException;
import com.mindtree.exceptions.NoDataFoundException;
import com.mindtree.exceptions.ServiceException;
import com.mindtree.exceptions.UnableToConnectException;
import com.mindtree.model.Player;

public class CovidAnalysisService {

	Dao covidAnalysisDao;

	public CovidAnalysisService() throws ServiceException {
		try {
			covidAnalysisDao = new CovidAnalysisDao();
		} catch (Exception e) {
			throw new UnableToConnectException();
		}
	}

	public List<String> getStateCode() throws ServiceException {
		try {
			return covidAnalysisDao.getStateCode().stream().map(p -> p.getState()).distinct()
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new UnableToConnectException();
		}

	}

	public List<String> getDistrictNames(String stateCode) throws ServiceException {
		try {
			List<Player> players = covidAnalysisDao.getDistrictName(stateCode);
			if (players.isEmpty()) {
				throw new InvalidStateCodeException();
			}
			return players.stream().map(p -> p.getDistrict()).distinct().sorted().collect(Collectors.toList());
		} catch (Exception e) {
			throw new UnableToConnectException();
		}
	}

	public List<Player> getDataForDateRange(String startDate, String endDate) throws ServiceException {
		Date sDate;
		Date eDate;
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			sDate = sdFormat.parse(startDate);
		} catch (ParseException e) {
			throw new InvalidDateException("Invalid Start date, please check your input");
		}
		try {
			eDate = sdFormat.parse(endDate);
		} catch (ParseException e) {
			throw new InvalidDateException("Invalid End date, please check your input");
		}

		if (sDate.compareTo(eDate) > 0) {
			throw new InvalidDateRangeException();
		}
		try {
			List<Player> players = covidAnalysisDao.getDataForDateRange(sDate, eDate);
			if (players.isEmpty()) {
				throw new NoDataFoundException();
			} else {
				return players.stream().sorted((p1, p2) -> p1.getDate().compareTo(p2.getDate()))
						.collect(Collectors.toList());
			}
		} catch (SQLException e) {
			throw new UnableToConnectException();
		}

	}

	public Map<String, List<Player>> getDataForTwoStatesForGivenDateRange(String startDate, String endDate,
			String firstState, String secondState) throws ServiceException {
		List<Player> players = getDataForDateRange(startDate, endDate);
		Map<String, List<Player>> playerMap = new HashMap<>();
		playerMap.put(firstState,
				getDataforCommonDate(getStateData(players, firstState), getStateData(players, secondState)));
		playerMap.put(secondState,
				getDataforCommonDate(getStateData(players, secondState), getStateData(players, firstState)));
		return playerMap;
	}

	private List<Player> getStateData(List<Player> players, String stateCode) {
		return players.stream().filter(p -> p.getState().equals(stateCode)).collect(Collectors.toList());
	}

	private List<Player> getDataforCommonDate(List<Player> firstStateData, List<Player> secondStateData) {
		return firstStateData.stream()
				.filter(p1 -> secondStateData.stream().anyMatch(p2 -> p1.getDate().compareTo(p2.getDate()) == 0))
				.sorted((p1, p2) -> p2.getDate().compareTo(p1.getDate())).collect(Collectors.toList());
	}

	public void end() {
		covidAnalysisDao.end();
	}

}
