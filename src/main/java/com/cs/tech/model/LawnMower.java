package com.cs.tech.model;

public class LawnMower {
	
	private String itinerary;
	private Integer currentX;
	private Integer currentY;
	private char currentDirection;

	public String getItinerary() {
		return itinerary;
	}
	
	public void setItinerary(String itinerary) {
		this.itinerary = itinerary;
	}
	
	public Integer getCurrentX() {
		return currentX;
	}
	
	public void setCurrentX(Integer currentX) {
		this.currentX = currentX;
	}
	
	public Integer getCurrentY() {
		return currentY;
	}
	
	public void setCurrentY(Integer currentY) {
		this.currentY = currentY;
	}
	
	public char getCurrentDirection() {
		return currentDirection;
	}
	
	public void setCurrentDirection(char currentDirection) {
		this.currentDirection = currentDirection;
	}
}
