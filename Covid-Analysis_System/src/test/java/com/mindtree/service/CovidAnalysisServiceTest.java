package com.mindtree.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.mindtree.exceptions.InvalidDateException;
import com.mindtree.exceptions.InvalidDateRangeException;
import com.mindtree.exceptions.InvalidStateCodeException;
import com.mindtree.exceptions.ServiceException;
import com.mindtree.model.Player;

public class CovidAnalysisServiceTest {

	private CovidAnalysisService covidAnalysisService;

	@Before
	public void beforeClass() throws ServiceException {
		covidAnalysisService = new CovidAnalysisService();
	}

	@Test
	public void testGetStateNameValidData() throws ServiceException {
		List<String> expecteds = Arrays.asList("JK", "DL", "HP", "PY", "DN", "HR", "WB", "BR", "KA", "SK", "GA", "MH",
				"UP", "ML", "UT", "KL", "MN", "GJ", "MP", "OR", "CH", "AN", "MZ", "AP", "AR", "CT", "AS", "PB", "TG",
				"LA", "RJ", "TN", "JH", "NL", "TR");
		List<String> actuals = covidAnalysisService.getStateCode();
		assertArrayEquals(expecteds.toArray(), actuals.toArray());
	}

	@Test
	public void testGetDistrictNameValidData() throws ServiceException {
		List<String> expecteds = Arrays.asList("Alappuzha", "Ernakulam", "Idukki", "Kannur", "Kasaragod", "Kollam",
				"Kottayam", "Kozhikode", "Malappuram", "Palakkad", "Pathanamthitta", "Thiruvananthapuram",
				"Thiruvanathapuram", "Thrissur", "Wayanad");
		List<String> actuals = covidAnalysisService.getDistrictNames("KL");
		assertArrayEquals(expecteds.toArray(), actuals.toArray());
	}

	@Test(expected = InvalidStateCodeException.class)
	public void testGetDistrictNameInValidData() throws ServiceException {
		covidAnalysisService.getDistrictNames("AA");
	}

	@Test
	public void testGetDataForDateRangeValidData() throws ServiceException, ParseException {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<Player> expected = Arrays.asList(
				new Player(new Date(sdFormat.parse("2020-03-22").getTime()), "CH", null, 0, 1, 0, 0),
				new Player(new Date(sdFormat.parse("2020-03-22").getTime()), "DL", null, 0, 1, 0, 0),
				new Player(new Date(sdFormat.parse("2020-03-22").getTime()), "TG", null, 0, 6, 0, 0),
				new Player(new Date(sdFormat.parse("2020-03-23").getTime()), "CH", null, 0, 1, 0, 0),
				new Player(new Date(sdFormat.parse("2020-03-23").getTime()), "DL", null, 0, 2, 0, 0),
				new Player(new Date(sdFormat.parse("2020-03-23").getTime()), "TG", null, 0, 6, 0, 0),
				new Player(new Date(sdFormat.parse("2020-03-24").getTime()), "MN", null, 0, 1, 0, 0),
				new Player(new Date(sdFormat.parse("2020-03-24").getTime()), "TG", null, 0, 6, 0, 0));
		List<Player> actual = covidAnalysisService.getDataForDateRange("2020-03-21", "2020-03-25");
		assertArrayEquals(expected.toArray(), actual.toArray());
	}

	@Test(expected = InvalidDateException.class)
	public void testGetDataForDateRangeInValidData() throws ServiceException {
		List<List<String>> invalidInput = Arrays.asList(Arrays.asList("2020-A3-25", "2020-03-21"),
				Arrays.asList("2020-03-25", "2020-B3-21"));
		for (List<String> input : invalidInput) {
			covidAnalysisService.getDataForDateRange(input.get(0), input.get(1));
		}
	}

	@Test(expected = InvalidDateRangeException.class)
	public void testGetDataForDateRangeInValidDateRange() throws ServiceException {
		covidAnalysisService.getDataForDateRange("2020-03-25", "2020-03-21");

	}

	@Test
	public void testGetDataForTwoStatesForGivenDateRangeValidData() throws ServiceException, ParseException {
		String firstState = "DL";
		String secondState = "TG";
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, List<Player>> expected = new HashMap<>();
		expected.put(firstState,
				Arrays.asList(
						new Player(new Date(sdFormat.parse("2020-03-19").getTime()), firstState, null, 0, 3, 0, 0),
						new Player(new Date(sdFormat.parse("2020-03-18").getTime()), firstState, null, 0, 1, 0, 0),
						new Player(new Date(sdFormat.parse("2020-03-17").getTime()), firstState, null, 0, 3, 0, 0),
						new Player(new Date(sdFormat.parse("2020-03-02").getTime()), firstState, null, 0, 1, 0, 0)));
		expected.put(secondState,
				Arrays.asList(
						new Player(new Date(sdFormat.parse("2020-03-19").getTime()), secondState, null, 0, 3, 0, 0),
						new Player(new Date(sdFormat.parse("2020-03-18").getTime()), secondState, null, 0, 8, 0, 0),
						new Player(new Date(sdFormat.parse("2020-03-17").getTime()), secondState, null, 0, 1, 0, 0),
						new Player(new Date(sdFormat.parse("2020-03-02").getTime()), secondState, null, 0, 1, 0, 0)));

		Map<String, List<Player>> actual = covidAnalysisService.getDataForTwoStatesForGivenDateRange("2020-03-01",
				"2020-03-20", firstState, secondState);
		assertEquals(expected, actual);
	}

	@Test(expected = InvalidDateException.class)
	public void testGetDataForTwoStatesForGivenDateRangeInValidData() throws ServiceException {
		List<List<String>> invalidInput = Arrays.asList(Arrays.asList("2020-A3-25", "2020-03-21"),
				Arrays.asList("2020-03-25", "2020-B3-21"));
		for (List<String> input : invalidInput) {
			covidAnalysisService.getDataForTwoStatesForGivenDateRange(input.get(0), input.get(1),"DL","TG");
		}
	}

	@Test(expected = InvalidDateRangeException.class)
	public void testGetDataForTwoStatesForGivenDateRangeInValidDateRange() throws ServiceException {
		covidAnalysisService.getDataForTwoStatesForGivenDateRange("2020-03-25", "2020-03-21","DL","TG");

	}

}
