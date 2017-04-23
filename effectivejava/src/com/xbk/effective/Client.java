package com.xbk.effective;

public class Client {

	public static void main(String[] args) {
		Singleton.INSTANCE.getIns();
		Singleton.INSTANCE.getIns();
		Singleton.INSTANCE.getIns();
	}
	
}
