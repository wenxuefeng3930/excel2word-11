package com.bonc.common.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UAParser {

	public static String UARegex(String encodeUA, Map<String, List<Map<String, String>>> rel_ua_regex) {
		long startTime = System.currentTimeMillis();
		String regexId = "";
		String terminal_model = "";
		String terminal_type = "";
		String os_info = "";
		StringBuffer result = new StringBuffer();
		if (!CommonTool.filterNull(encodeUA)) {

			Pattern pattern = Pattern.compile("(Windows NT|Windows|Mac OS X)[\\s:]+[0-9._/]+");
			Matcher ma = pattern.matcher(encodeUA);

			while (ma.find()) {
				os_info = ma.group();
				break;
			}

			if (os_info.equals("")) {
				try {
					// Pattern patternUA;
					// Matcher maUA;
					// 是否继续匹配
					boolean isGoOn = true;
					// 遍历UA正则规则列表
					for (Map.Entry<String, List<Map<String, String>>> entry : rel_ua_regex.entrySet()) {
						// 判断UA中是否包含关键字
						if (encodeUA.indexOf(entry.getKey()) != -1 || entry.getKey().equals("没有关键字")) {
							// 取出该关键字相关UA正则列表
							List<Map<String, String>> reList = entry.getValue();
							// 遍历该关键字相关UA正则列表
							for (Map<String, String> map : reList) {
								String regex = map.get("regex");
								pattern = Pattern.compile(regex);
								ma = pattern.matcher(encodeUA);
								// System.out.println(maUA.find());
								if (ma.find()) {
									terminal_model = ma.group(1);
									regexId = map.get("regexid");
									terminal_type = "手机";
									isGoOn = false;
								}
								if (!isGoOn) {
									break;
								}
							}
							if (!isGoOn) {
								break;
							}
						}
					}

					pattern = Pattern.compile("(Android|Adr|WPOS|Windows Phone OS|iPhone OS|CPU OS)[\\s:/]+[0-9._/]+");
					ma = pattern.matcher(encodeUA);
					while (ma.find()) {
						os_info = ma.group();
						terminal_type = "手机";
						break;
					}
				} catch (Exception e) {
					terminal_type = "未知";
					e.printStackTrace();
				}
			} else {
				terminal_type = "PC";
			}
		}
		long endTime = System.currentTimeMillis();
		result.append(terminal_type).append("\t").append(regexId).append("\t").append(terminal_model).append("\t")
				.append(os_info).append("\t").append(endTime - startTime);
		return result.toString();
	}

	public static String UAMatch(String encodeUA, Map<String, Map<String, String>> rel_terminalmodel) {
		String result = "";
		String os_info = "";
		String terminal_model = "";
		String temp[] = {};
		if (!CommonTool.filterNull(encodeUA)) {
			try {
				Pattern pattern = Pattern
						.compile("(Android|Adr|WPOS|Windows Phone OS|iPhone OS|CPU OS)[\\s:/]+[0-9._/]+");
				Matcher ma = pattern.matcher(encodeUA);
				while (ma.find()) {
					os_info = ma.group();
					break;
				}
				// 终端型号截取 [Linux, U, Android 4.0.3, HTC T328d Build/IML74K]
				if (encodeUA.split("\\(").length != 1) {
					temp = encodeUA.split("\\(")[1].split("\\)")[0].split("\\;");
					// 是否是手机
					boolean isTel = true;
					for (int i = 0; i < temp.length; i++) {
						if (temp[i].trim().startsWith("Windows NT")) {
							isTel = false;
							break;
						}
					}
					if (isTel) {
						if (Pattern.compile("(iPhone OS|CPU OS|CPU iPhone OS)").matcher(os_info).find()) {
							terminal_model = temp[0];
						} else if (Pattern.compile("(Windows Phone OS|WPOS)").matcher(os_info).find()) {
							terminal_model = temp[temp.length - 1];
						} else {
							if (temp.length >= 5) {
								if (temp[4].indexOf("Build") == -1 && temp[4].indexOf("/") != -1)
									terminal_model = temp[4].substring(0, temp[4].indexOf("/")).trim();
								else if (temp[4].indexOf("Build") == -1 && temp[4].indexOf("/") == -1)
									terminal_model = temp[4].trim();
								else
									terminal_model = temp[4].substring(0, temp[4].indexOf("Build")).split("\\/")[0]
											.trim();
							} else {
								// HTC T328d
								if (temp[temp.length - 1].indexOf("Build") != -1) {
									// System.out.println("----temp:"+Arrays.toString(temp));
									terminal_model = temp[temp.length - 1]
											.substring(0, temp[temp.length - 1].indexOf("Build")).split("\\/")[0]
													.trim();
								}
							}
						}
					}
				} else {
					// temp形式：HW-HUAWEI_C5730/C5730C58B503 OBIGO-BROWSER/5.0
					// CTC/1.0
					temp = encodeUA.split("/");
					terminal_model = temp[0];
				}
				if (terminal_model != null && !"".equals(terminal_model)
						&& rel_terminalmodel.get(terminal_model) == null) {
					if (rel_terminalmodel.get(terminal_model.replace(" ", "-")) == null
							&& rel_terminalmodel.get(terminal_model.replace(" ", "_")) == null) {
						// context.write(new Text(terminal_model), new
						// Text(encodeUA));
						// mos.write(NullWritable.get(),new
						// Text(terminal_model),"unmatch");
						StringBuffer sb = new StringBuffer();
						sb.append(terminal_model).append("\t").append(encodeUA);
						result = sb.toString();
						// System.out.println("---terminal_model:"+result);
					}
				}
			} catch (Exception e) {
				// terminal_model = "未知UA: " + encodeUA;
				// System.out.println("---terminal_model:"+result);
				StringBuffer sb = new StringBuffer();
				sb.append(terminal_model).append("\t").append(encodeUA);
				result = sb.toString();
				// mos.write(NullWritable.get(),new
				// Text(terminal_model),"unmatch");
				// context.write(new Text(terminal_model), new Text(""));
			}
		}
		return result;
	}

	public static String parserUA(String UA, Map<String, Map<String, String>> rel_terminalmodel,
			Map<String, String> rel_terminalmodel_apple) {
		long startTime = System.currentTimeMillis();
		String os_info = "";
		String net = "";
		String terminal_model = "";
		String terminal = "";
		String model = "";
		String terminalid = "";
		StringBuffer sb = new StringBuffer();

		if (!CommonTool.filterNull(UA)) {
			// String UA = Base64.decode(encodeUA) ;

			Pattern pattern = Pattern.compile("(Android|Adr|WPOS|Windows Phone OS|iPhone OS|CPU OS)[\\s:/]+[0-9._/]+");
			Matcher ma = pattern.matcher(UA);

			while (ma.find()) {
				os_info = ma.group();
				break;
			}

			if (!os_info.equals("")) {
				try {
					// System.out.println(os_info);
					// 终端型号截取
					String temp[] = UA.split("\\(")[1].split("\\)")[0].split("\\;");

					if (Pattern.compile("(iPhone OS|CPU OS)").matcher(os_info).find()) {
						boolean istrue = true;
						String temp_model = "";
						terminal_model = UA.trim().replaceAll("\\s+", "");
						terminal_model = terminal_model.toUpperCase();
						Set<String> keys = rel_terminalmodel_apple.keySet();
						Iterator<String> i = keys.iterator();
						while (i.hasNext()) {
							String ua_key = i.next();
							if (terminal_model.indexOf(ua_key) > -1) {
								/*
								 * 这样做是为了最为精确的匹配UA规则，比如：iphone 4与iphone 4s
								 * 假如UA中是iphone 4s 那两个规则都能匹配，而最为精确的匹配就是iphone 4s
								 */
								if (ua_key.length() > temp_model.length()) {
									temp_model = ua_key;
									istrue = false;
								}
							}
						}

						if (!istrue) {
							net = rel_terminalmodel.get(temp_model).get("terminaltype");
							terminalid = rel_terminalmodel.get(temp_model).get("terminalid");
							terminal = rel_terminalmodel.get(temp_model).get("terminal");
							model = rel_terminalmodel.get(temp_model).get("model");
						} else {
							if (terminal_model.indexOf("IPHONE") > -1) {
								terminal = "苹果";
							} else {
								net = "移动未知";
							}
						}

					} else {
						// terminal_model = temp[temp.length-1].substring(0,
						// temp[temp.length-1].indexOf("Build")).split("\\/")[0].trim()
						// ;

						if (temp.length >= 5) {
							if (temp[4].indexOf("Build") == -1 && temp[4].indexOf("/") != -1)
								terminal_model = temp[4].substring(0, temp[4].indexOf("/")).trim();
							else if (temp[4].indexOf("Build") == -1 && temp[4].indexOf("/") == -1)
								terminal_model = temp[4].trim();
							else
								terminal_model = temp[4].substring(0, temp[4].indexOf("Build")).split("\\/")[0].trim();
						} else {
							terminal_model = temp[temp.length - 1].substring(0, temp[temp.length - 1].indexOf("Build"))
									.split("\\/")[0].trim();
						}
						// System.out.println(terminal_model);

						if (rel_terminalmodel.get(terminal_model) != null) {
							net = rel_terminalmodel.get(terminal_model).get("terminaltype");
							terminalid = rel_terminalmodel.get(terminal_model).get("terminalid");
							terminal = rel_terminalmodel.get(terminal_model).get("terminal");
							model = rel_terminalmodel.get(terminal_model).get("model");
						} else
							net = "移动未知";
					}

				} catch (Exception e) {
					net = "移动未知";
				}

			} else {
				ma = Pattern.compile("(Windows NT|Windows|Mac OS X)[\\s:]+[0-9._/]+").matcher(UA);
				while (ma.find()) {
					os_info = ma.group();
					break;
				}

				if (!os_info.equals("")) {
					net = "PC";
				} else
					net = "未知";
			}

		} else {
			net = "";
		}
		long endTime = System.currentTimeMillis();
		// if(!net.equals(""))
		sb.append(net).append("\t").append(terminalid).append("\t").append(terminal).append("\t").append(model)
				.append("\t").append(os_info).append("\t").append(endTime - startTime);
		return sb.toString();
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		InputStream fis = UAParser.class.getResourceAsStream("/d_terminal_model.txt");
		BufferedReader bis = new BufferedReader(new InputStreamReader(fis, "UTF-8"));

		String line;
		String[] temps;
		Map<String, String> map = null;
		Map<String, Map<String, String>> rel_terminalmodel = new HashMap<String, Map<String, String>>();

		while ((line = bis.readLine()) != null) {
			try {
				temps = line.split("\\,", -1);
				map = new HashMap<String, String>();
				map.put("terminalid", temps[0]);
				map.put("terminalmodel", temps[1]);
				map.put("terminal", temps[2]);
				map.put("model", temps[3]);
				map.put("terminaltype", temps[4]);
				// System.out.print("==============="+map.toString()) ;

				rel_terminalmodel.put(temps[1], map);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// System.out.println(parserUA("UCWEB/2.0 (Linux; U; Adr 2.3.6; zh-CN;
		// SCH-I699) U2/1.0.0 UCBrowser/9.0.1.294 U2/1.0.0
		// Mobile",rel_terminalmodel));

	}

}
