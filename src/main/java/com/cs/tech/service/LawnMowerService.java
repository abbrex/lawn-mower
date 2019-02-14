package com.cs.tech.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cs.tech.dao.*;
import com.cs.tech.exceptions.InitialPositionException;
import com.cs.tech.exceptions.ItineraryException;
import com.cs.tech.exceptions.UpperRightFormatException;
import com.cs.tech.model.LawnMower;
import com.cs.tech.model.UpperRightCorner;

public class LawnMowerService {
	
	private String filePath;	
	
	public LawnMowerService(String filePath) {
		super();
		this.filePath = filePath;
	}

	public List<String> setOutPrint(List<LawnMower> lawnMowers, 
			UpperRightCorner upperRightCorner ) {
		List<String> outPrint = new ArrayList<>();
		for (LawnMower lawnMower : lawnMowers) {
			moveLawnMower(lawnMower, upperRightCorner, outPrint);
		}
		return outPrint;
	}

	private void moveLawnMower(LawnMower lawnMower,
			UpperRightCorner upperRightCorner,
			List<String> outPrint) {
		
		for (int i = 0 ; i <= lawnMower.getItinerary().length() -1 ; i++) {
			char instruction = lawnMower.getItinerary().charAt(i);
			if (isDirection(instruction)) {
				changeDirection(lawnMower, instruction);
			} else {
				moveForward(lawnMower, upperRightCorner);
			}
		}
		outPrint.add(lawnMower.getCurrentX() + " " + 
				lawnMower.getCurrentY() + " " + lawnMower.getCurrentDirection()
				);
	}

	private void changeDirection(LawnMower lawnMower, char instruction) {
		if (instruction == 'R') {
			if (lawnMower.getCurrentDirection() == 'N') {
				lawnMower.setCurrentDirection('E');
			} else if (lawnMower.getCurrentDirection() == 'E') {
				lawnMower.setCurrentDirection('S');
			} else if (lawnMower.getCurrentDirection() == 'S') {
				lawnMower.setCurrentDirection('W');
			} else if (lawnMower.getCurrentDirection() == 'W') {
				lawnMower.setCurrentDirection('N');
			}
		} else if (instruction == 'L') {
			if (lawnMower.getCurrentDirection() == 'N') {
				lawnMower.setCurrentDirection('W');
			} else if (lawnMower.getCurrentDirection() == 'W') {
				lawnMower.setCurrentDirection('S');
			} else if (lawnMower.getCurrentDirection() == 'S') {
				lawnMower.setCurrentDirection('E');
			} else if (lawnMower.getCurrentDirection() == 'E') {
				lawnMower.setCurrentDirection('N');
			}
		}
	}

	private void moveForward(LawnMower lawnMower,
			UpperRightCorner upperRightCorner) {
		if (lawnMower.getCurrentDirection() == 'N' 
				&& upperRightCorner.getPositionY() >= lawnMower.getCurrentY()) {
			lawnMower.setCurrentY(lawnMower.getCurrentY() + 1);
		} else if (lawnMower.getCurrentDirection() == 'S'
				&& 0 <= lawnMower.getCurrentY()) {
			lawnMower.setCurrentY(lawnMower.getCurrentY() - 1);
		} else if (lawnMower.getCurrentDirection() == 'E'
				&& upperRightCorner.getPositionX() >= lawnMower.getCurrentX()) {
			lawnMower.setCurrentX(lawnMower.getCurrentX() + 1);
		} else if (lawnMower.getCurrentDirection() == 'W'
				&& 0 <= lawnMower.getCurrentX()) {
			lawnMower.setCurrentX(lawnMower.getCurrentX() - 1);
		}
	}

	private boolean isDirection(char instruction) {
		if (instruction == 'L' || instruction == 'R')
			return true;
		return false;
	}

	public List<LawnMower> setLawnMowers(List<String> rawData) {
		List<LawnMower> lawnMowers = new ArrayList<>();
		LawnMower lawnMower = null;
		for (int i = 1; i <= rawData.size()-1 ; i++) {
			if (i % 2 != 0 && lawnMower == null) {
				lawnMower = new LawnMower();
				checkAndSetInitialPosition(rawData.get(i), lawnMower);
			} else {
				checkAndSetItinerary(rawData.get(i), lawnMower);
				lawnMowers.add(lawnMower);
				lawnMower = null;
			}
		}
		return lawnMowers;
	}
	
	private void checkAndSetItinerary(String data, LawnMower lawnMower) {
		try {
			checkItinerary(data);
			lawnMower.setItinerary(data);
		} catch (ItineraryException iex) {
			iex.printStackTrace();
		}
	}
	
	private void checkItinerary(String data) throws ItineraryException {
		String allowedChars = "RLF";
		for (char c : data.toCharArray()) {
			if (allowedChars.indexOf(c) == -1) {
				throw new ItineraryException();
			}
		}
	}

	private void checkAndSetInitialPosition(String data, LawnMower lawnMower) {
		try {
			checkInitialPosition(data);
			lawnMower.setCurrentX(Integer.parseInt(String.valueOf(data.charAt(0))));
			lawnMower.setCurrentY(Integer.parseInt(String.valueOf(data.charAt(2))));
			lawnMower.setCurrentDirection(data.charAt(4));
		} catch (InitialPositionException ex) {
			ex.printStackTrace();
		}
	}
 
	private void checkInitialPosition(String data) throws InitialPositionException {
		Matcher mat = Pattern.compile("\\d \\d [NEWS]").matcher(data);
		if (!mat.matches()) {
			throw new InitialPositionException();
		}
	}

	public UpperRightCorner setUpperRightCorner(String line) {
		Matcher mat = null;
		try {
			mat = checkUpperRightCorner(line);
		} catch (UpperRightFormatException e) {
			e.printStackTrace();
		} 
		
		UpperRightCorner upperRightCorner = new UpperRightCorner();
		upperRightCorner.setPositionX(Integer.parseInt(mat.group(1)));
		upperRightCorner.setPositionY(Integer.parseInt(mat.group(2)));
		return upperRightCorner;
	}

	private Matcher checkUpperRightCorner(String line) throws UpperRightFormatException {
		Matcher mat = Pattern.compile("(\\d) (\\d)").matcher(line);
		if (mat.matches()) {
			return mat;
		} else {
			throw new UpperRightFormatException();
		}
	}
	
	public List<String> setRawData() {
		FileInputDao dao = new FileInputDao(filePath);
		return dao.getData();
	}
}
