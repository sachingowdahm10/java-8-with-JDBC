package com.mindtree;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.mindtree.controller.CovidAnalysisController;
import com.mindtree.exceptions.ServiceException;
import com.mindtree.model.Player;

public class Main {

	public static void main(String[] args) {
		String choice;
		Scanner scan = new Scanner(System.in);
		CovidAnalysisController covidAnalysisController;
		do {
			System.out.println("************************************************");
			System.out.println("1. Get States Name");
			System.out.println("2. Get District name for given states");
			System.out.println("3. Display Data by state with in date range");
			System.out.println("4. Display Confirmed cases by comparing two states for a given date range");
			System.out.println("5. Exit");
			System.out.println("Please select Option:");
			choice = scan.next();
			String startDate;
			String endDate;
			try {
				covidAnalysisController = new CovidAnalysisController();
				switch (choice) {
				case "1":

					try {
						List<String> stateCodes = covidAnalysisController.getStateNames();
						stateCodes.forEach(System.out::println);
					} catch (ServiceException e) {
						System.out.println(e.getMessage());
					}
					break;
				case "2":
					System.out.println("Please enter state code :");
					String stateCode = scan.next();
					try {
						List<String> districtList = covidAnalysisController.getDistrictName(stateCode);
						districtList.forEach(System.out::println);

					} catch (ServiceException e) {
						System.out.println(e.getMessage());

					}
					break;
				case "3":
					System.out.println("Please enter start date (yyyy-MM-dd) :");
					startDate = scan.next();
					System.out.println("Please enter end date (yyyy-MM-dd) :");
					endDate = scan.next();
					try {
						List<Player> players = covidAnalysisController.getDataForDateRange(startDate, endDate);
						System.out.printf("    %-8S| %-6S| %-16S%n", "DATE", "STATE", "CONFIRMED TOTAL");
						players.forEach(p -> System.out.printf(" %-11s|   %-4S|        %d%n", p.getDate().toString(),
								p.getState(), p.getConfirmed()));
					} catch (ServiceException e) {
						System.out.println(e.getMessage());
					}
					break;
				case "4":
					System.out.println("Please enter start date (yyyy-MM-dd) :");
					startDate = scan.next();
					System.out.println("Please enter end date (yyyy-MM-dd) :");
					endDate = scan.next();
					System.out.println("Please enter first state code");
					String firstState = scan.next();
					System.out.println("Please enter second state code");
					String secondState = scan.next();
					Map<String, List<Player>> playerMap = covidAnalysisController
							.getDataForTwoStatesForGivenDateRange(startDate, endDate, firstState, secondState);
					List<Player> firstStateData = playerMap.get(firstState);
					List<Player> secondStateData = playerMap.get(secondState);
					System.out.printf("    %-8S| %-12S| %-28S|%-14S|%-30S%n", "DATE", "FIRST STATE",
							"FIRST STATE CONFIRMED TOTAL", " SECOND STATE", " SECOND STATE CONFIRMED TOTAL");
					for (int i = 0; i < firstStateData.size() | i < secondStateData.size(); i++) {
						System.out.printf(" %-11s|      %-7S|               %-14d|      %-8S|          %d%n",
								firstStateData.get(i).getDate().toString(), firstStateData.get(i).getState(),
								firstStateData.get(i).getConfirmed(), secondStateData.get(i).getState(),
								secondStateData.get(i).getConfirmed());
					}
					break;
				case "5":
					scan.close();
					covidAnalysisController.end();
					break;

				default:
					System.out.println("Invalid option, please select valid option");
					break;
				}
			} catch (ServiceException e) {
				System.out.println(e.getMessage());
			}

		} while (!choice.equals("5"));

	}
}
