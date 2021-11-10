package com.pt.classloader.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class HotClassLoader extends ClassLoader{

	 public HotClassLoader(){
		 super(ClassLoader.getSystemClassLoader());
	 }
	 
	 private File objFile;
	 
	 public File getObjFile() {
		return objFile;
	}

	public void setObjFile(File objFile) {
		this.objFile = objFile;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		System.out.println("in HotClassLoader.findClass");
		
		Class clz = null;
		byte[] data;
		try {
			data = getClassFileByte(getObjFile());
			clz = defineClass(name, data, 0, data.length);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(null == clz) {
			System.out.println("have no class file");
		}
		return clz;
	}


	private byte[] getClassFileByte(File objFile2) throws Exception {
		FileInputStream fis = new FileInputStream(objFile2);
		FileChannel channel = fis.getChannel();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		WritableByteChannel outc = Channels.newChannel(baos);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while (true) {
			int i = channel.read(buffer);
			if(i==0 || i==-1) {
				break;
			}
			buffer.flip();
			outc.write(buffer);
			buffer.clear();
		}
		
		fis.close();
		return baos.toByteArray();
	}

	public static void main(String[] args) {
		System.out.println(ClassLoader.getSystemClassLoader());
	}
}
