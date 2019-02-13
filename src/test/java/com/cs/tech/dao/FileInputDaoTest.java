package com.cs.tech.dao;

import java.util.List;

import junit.framework.TestCase;

public class FileInputDaoTest extends TestCase {

	public void testGetData() {
		String filePath = "resources\\data.txt";
		FileInputDao dao = new FileInputDao(filePath);
		List<String> data = dao.getData();
		assertEquals("5 5", data.get(0));
		assertEquals("1 2 N", data.get(1));
		assertEquals("LFLFLFLFF", data.get(2));
		assertEquals("3 3 E", data.get(3));
		assertEquals("FFRFFRFRRF", data.get(4));
	}
}
