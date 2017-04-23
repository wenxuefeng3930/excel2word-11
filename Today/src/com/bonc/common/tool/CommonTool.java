package com.bonc.common.tool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonTool {

	/**
	 * 判断输入是否为空
	 * 
	 * @param input
	 * @return
	 */
	public static boolean filterNull(String input) {
		boolean flag = false;

		if (input.equals("") || input == null) {
			flag = true;
		}

		return flag;
	}

	/**
	 * 从各种不同形式的开始时间中获取数据日期
	 * 
	 * @return 获取的数据日期
	 */
	public static String getDateTime(String starttime) {

		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dataTime = "";
		if (!filterNull(starttime)) {
			if (starttime.contains("-")) {
				dataTime = starttime.substring(0, 10).replaceAll("-", "");
			} else {
				if (starttime.contains(".")) {
					starttime = starttime.split("\\.")[0] + "000";
					dataTime = format.format(new Date(Long.parseLong(starttime)));
				} else {
					if (starttime.length() == 13) {
						dataTime = format.format(new Date(Long.parseLong(starttime)));
					} else {
						dataTime = starttime.substring(0, 8);
					}
				}
			}
		}
		return dataTime;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
