package com.pt.file.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;

public class FileTest {

	public static void main(String[] args) throws Exception {
		Enumeration<URL> x = FileTest.class.getClassLoader().getResources("META-INF/MANIFEST.MF");
		
		while(x.hasMoreElements()) {
			URL _x = x.nextElement();
			if(_x.toString().contains("log4j-api")) {
				System.out.println(_x.toString());
				
				InputStream stream = _x.openStream();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(stream));
				StringBuffer buffer = new StringBuffer();
				String line = "";
				while ((line = in.readLine()) != null){
				    buffer.append(line).append("\r\n");
				}
				String input = buffer.toString();
				System.out.println(input);
			}
			
		}
	}
}
