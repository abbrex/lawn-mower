package com.cs.tech.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileInputDao {
	
	private String filePath ;
	

	public FileInputDao(String filePath) {
		super();
		this.filePath = filePath;
	}

	public List<String> getData() {
		String line = null;
		List<String> result = new ArrayList<>();
		
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                result.add(line);
            }   
            bufferedReader.close();         
        } catch(IOException ex) {
            System.out.println(ex.getStackTrace());                
        }
        
		return result;
	}
}
