package com.cs.tech;

import java.util.List;

import com.cs.tech.exceptions.UpperRightFormatException;
import com.cs.tech.model.LawnMower;
import com.cs.tech.model.UpperRightCorner;
import com.cs.tech.service.LawnMowerService;

public class App 
{
	// This could be an args parameter
	static String filePath = "resources\\data.txt";

	/**
	 * program entry point
	 * @param args
	 */
	public static void main( String[] args ) {
		System.out.println( "###### BEGINS LAWNMOWER PROCESSING ############");
		System.out.println( "###############################################");

		List<String> endPositions = App.process();

		for (String endPosition : endPositions) {
			System.out.println(endPosition);
		}

		System.out.println( "###############################################");
		System.out.println( "###### ENDING LAWNMOWER PROCESSING ############");
	}

	/**
	 * Method that control program flow
	 * @return
	 */
	private static List<String> process() {
		LawnMowerService service = new LawnMowerService(filePath); 
		
		// Get data from input file
		List<String> rawData = service.setRawData();

		// Set the upper right corner
		UpperRightCorner upperRightCorner = service.setUpperRightCorner(rawData.get(0));

		// Set the lawn mowers from data
		List<LawnMower> lawnMowers = service.setLawnMowers(rawData);
		
		// Set the end position of all lawn mowers
		return service.setEndPositions(lawnMowers, upperRightCorner);
	}
}
