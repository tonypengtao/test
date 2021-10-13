package com.pt.classloader.test;

import java.net.URL;

public class ClassLoaderTest {

	public static void main(String[] args) {
		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
		for(URL u: urls) {
			System.out.println(u);
		}
	}
}
