package com.pt.spi.test;

import java.util.ServiceLoader;

public class SPIMain {

	private static ServiceLoader<IRun> iRuns = ServiceLoader.load(IRun.class);
	
	public static void main(String[] args) {
	
		for(IRun r: iRuns) {
			r.run();
		}
	}
}
