package com.bonc.test;

public class TestString {

	public static void main(String[] args) {
		System.out.println(fibonacci(2));
	}
	public static int fibonacci(int n) {
        if(n == 1) {
            return 0;
        }
        if(n == 2) {
            return 1;
        }
        int iVal = 0;
        int nextVal = 1;
        int val = 1;
        for(int i = 1; i < n - 1; i++) {
            val = nextVal + iVal;
            iVal = nextVal;
            nextVal = val;
        }
        return val;
    }
}