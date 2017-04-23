package com.xbk.effective;

import java.util.Random;

public enum Singleton {

	INSTANCE;
	static int a = -1;
	static{
		Random ran = new Random();
		a = ran.nextInt(500);
	}
	public void getIns(){
		
		System.out.println(a);
	}
	
}
