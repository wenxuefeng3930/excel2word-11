package com.bonc.test;

public class TestClassLoader {

	private ClassLoader classLoader;
	{
		classLoader = Thread.currentThread().getContextClassLoader();
	    if (classLoader == null) {
	      classLoader = TestClassLoader.class.getClassLoader();
	    }
	}
	
	public void test(String name) {
		System.out.println(classLoader.getResource(name));
	}
	
	public static void main(String[] args) {
		TestClassLoader test = new TestClassLoader();
		test.test("file:/F:/");
		System.out.println(ClassLoader.getSystemResource("D:/out.txt"));
	}
}
