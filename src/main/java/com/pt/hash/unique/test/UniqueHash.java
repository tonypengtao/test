package com.pt.hash.unique.test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UniqueHash {

	static final char[] ALL_VALID_CHARS = new char[] { '0', '1', '2',
			'3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', '_', '-', ' ','.'};
	
	static int getIndexOfChar(char ch) {

		//初始化位置为0
		int i = 0;

		//返回字符在ALL_VALID_CHARS中的位置
		for (; i < ALL_VALID_CHARS.length; i++) {
			if (ch == ALL_VALID_CHARS[i]) {
				return i;
			}
		}
		//抛异常，”根据w3Account产生userid错误，不可调度的数据或者数据为空“
		throw new IllegalArgumentException("invalid account char.");
	}
	
	static long toUid(String account) {
		//转换至小写字母
		account = account.toLowerCase();
		long dataDeci = 0;
		char ci;

		for (int i = 0; i < account.length(); i++) {
			//取每个小写字母
			ci = account.charAt(i);
			//转换
			dataDeci = dataDeci * 63 + ci;
		}
		return dataDeci;
	}
	
	public static void main(String[] args) {
//		String id1 = String.format("%08d", Integer.valueOf("0013483"));
//		System.out.println(id1);
		
		Set<Long> ids = new HashSet<Long>(10000000);
		
		for(int i=0;i<10000000;i++) {
			String idStr = "p"+String.format("%08d", i);
			long newId = toUid(idStr);
			if(ids.contains(newId)) {
				System.out.println(" fail: " + newId + " account: " + idStr);
				return;
			}
			ids.add(newId);
		}
		
		System.out.println("success");
		//System.out.println(toUid("00000009"));  // 36022613264640
	}
}
