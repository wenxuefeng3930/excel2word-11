package com.xbk.effective;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class TestComparable {

	public static void main(String[] args) {
		Set<String> set = new TreeSet<String>();
		String[] arg = {"a","b","c"};
		Collections.addAll(set, arg);
		System.out.println(set);
	}
	
}
