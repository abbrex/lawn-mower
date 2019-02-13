package com.cs.tech.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.cs.tech.dao.FileInputDao;
import com.cs.tech.exceptions.UpperRightFormatException;
import com.cs.tech.model.LawnMower;
import com.cs.tech.model.UpperRightCorner;

import junit.framework.TestCase;

public class LawnMowerServiceTest extends TestCase {

	LawnMowerService service;
	
	public void setUp() {
		this.service = new LawnMowerService("");
	}
	
	@Test
	public void testSetUpperRightCornerWithValidParameters() {
		UpperRightCorner upperRightCorner = null;
		try {
			upperRightCorner = service.setUpperRightCorner("5 6");
		} catch (UpperRightFormatException e) {
			e.printStackTrace();
		}
		
		assertEquals(upperRightCorner.getPositionX(), new Integer(5));
		assertEquals(upperRightCorner.getPositionY(), new Integer(6));
	}

	@Test
	public void testSetLawnMowers() {
		List<String> rawData = new ArrayList<>();
		rawData.add("5 5");
		rawData.add("1 2 N");
		rawData.add("LFLFLFLFF");
		
		List<LawnMower> lawnMowers = this.service.setLawnMowers(rawData);
		assertEquals("LFLFLFLFF", lawnMowers.get(0).getItinerary());
		assertEquals(new Integer(1), lawnMowers.get(0).getCurrentX());
		assertEquals(new Integer(2), lawnMowers.get(0).getCurrentY());
		assertEquals('N', lawnMowers.get(0).getCurrentDirection());
	}
	
	@Test
	public void testSetOutPrint() {
		UpperRightCorner upperRightCorner = null;
		try {
			upperRightCorner = service.setUpperRightCorner("5 5");
		} catch (UpperRightFormatException e) {
			e.printStackTrace();
		}
		
		List<String> rawData = new ArrayList<>();
		rawData.add("5 5");
		rawData.add("1 2 N");
		rawData.add("LFLFLFLFF");
		
		List<LawnMower> lawnMowers = this.service.setLawnMowers(rawData);
		List<String> outPrint = this.service.setOutPrint(lawnMowers, upperRightCorner);
		
		assertEquals("1 3 N", outPrint.get(0));
	}
	
}
