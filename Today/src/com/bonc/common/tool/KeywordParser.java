package com.bonc.common.tool;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordParser {

	/**
	 * 解析搜索关键词
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * System.out. println(getKeyword(
		 * "http://ht.www.sogou.com/websearch/features/yun1.jsp?pid=sogou-brse-596dedf4498e258e&w=1366&v=1423&st=1387716708574&od=227&ls=1387716628695&lc=&lk=1387714754640&sd=137&cd=0&kd=9&u=1387620564473483&y=FE3CC78AC20739D05261B8662FD53502&query=%E7%AC%91%E8%AF%9D%E5%A4%A7%E5%85%A8%EF%BC"
		 * ,"www.sogou.com",""));
		 */

		Map<String, List<Map<String, String>>> domain_keyword = new HashMap<String, List<Map<String, String>>>();
		List<Map<String, String>> rel_keyword = null;

		rel_keyword = new ArrayList<Map<String, String>>();
		Map map = new HashMap<String, String>();
		map.put("DOMAIN_COMP", "search.suning.com");
		map.put("VALUE", "search.suning.com/");

		if (domain_keyword.get("baidu.com") != null) {
			domain_keyword.get("baidu.com").add(map);
		} else {
			rel_keyword.add(map);

			domain_keyword.put("baidu.com", rel_keyword);
		}

		String host = "m.baidu.com";
		List<Map<String, String>> keyword_rule = null;
		String host_str[] = host.split("\\.");
		String pre = "";
		String keyword = "";
		StringBuffer templines = new StringBuffer();
		for (int i = 0; i < host_str.length - 2; i++) {
			pre = host_str[i] + "." + pre;
			keyword_rule = domain_keyword.get(host.substring(pre.length(), host.length()));

			if (keyword_rule != null && keyword_rule.size() > 0) {
				for (int j = 0; j < keyword_rule.size(); j++) {

					keyword = KeywordParser.getKeyword(
							"http://search.suning.com/%E6%99%BA%E8%83%BD%E7%94%B5%E8%A7%86/cityId=9017",
							host.split("\\:")[0], keyword_rule.get(j).get("VALUE"));
					if (keyword != null && keyword.length() > 0)
						break;
				}
				break;
			}

		}
		// pre = pre+host_str[host_str.length-1] ;

		// String[][] siteWords =prop.getProperty("expression");
	}

	// 截取原始数据的一级域名 比如：www.baidu.com 截取出 baidu.com
	public static String subOneGradeHost(String host) {
		String temp = "";
		if (!CommonTool.filterNull(host)) {
			String[] str = host.split("\\.", -1);
			if (str.length >= 2) {
				String string = str[str.length - 2];
				Pattern pattern = Pattern.compile(
						"^(com|net|gov|edu|org|int|mil|top|tel|biz|name|info|mobi|cd|travel|pro|coop|aero|museum|cc|tv)$");
				Matcher matcher = pattern.matcher(string);
				if (matcher.find() && str.length > 2) {
					temp = str[str.length - 3] + "." + str[str.length - 2] + "." + str[str.length - 1];

				} else {
					temp = str[str.length - 2] + "." + str[str.length - 1];
				}
			}
		}
		return temp;
	}

	// 解析搜狗类型的关键字，以%25开头的关键字 URL2343%25u505c%25u8f66312321%25u573a3456
	// 源码为URL2343停车312321场3456
	public static String decodeSogou(String code) {
		String result = "";
		try {
			String[] arr = code.split("%25u");
			StringBuffer sbu = new StringBuffer("%FE%FF");
			sbu.append(arr[0]);
			for (int i = 1; i < arr.length; i++) {
				String app = arr[i];
				if (!"".equals(app) && app != null) {
					String noise = "";
					if (app.length() > 4) {
						noise = app.substring(4);
					}
					app = "%" + app.substring(0, 2) + "%" + app.substring(2, 4);

					sbu.append(app.toUpperCase() + noise);
				}
			}
			result = URLDecoder.decode(sbu.toString(), "unicode");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getKeyword(String url, String host, String keyword_rule) {
		String result = url.trim();
		if (result.length() > 0) {

			result = getUrlKeyword(url, keyword_rule);
			if (result.length() > 0) {
				return translate(result);

			}

		}
		return "";
	}

	private static String getUrlKeyword(String url, String siteWords) {
		int index = 0;
		String[] words = siteWords.split("\\,");
		for (String word : words) {
			if (index != -1 && url.length() >= word.length()) {
				index = url.indexOf(word.trim());
				url = url.substring(index + word.trim().length());
				// System.out.println(index+"=="+url+"=="+word.trim());
			} else
				return "";

		}

		if (index == -1) {

			return "";
		} else {
			int endIndex = url.indexOf("&", 0);
			// System.out.println(endIndex);
			if (endIndex == -1) {
				// endIndex = url.length();
				int subIndex = url.indexOf("/", index);
				if (subIndex != -1) {
					return url.substring(0, subIndex).trim();
				} else
					return url.substring(0).trim();

				// return url.substring( index + words[ words.length - 1
				// ].trim().length()).trim();
			}

			return url.substring(0, endIndex).trim();
		}
	}

	private static String translate(String s) {
		s = s.replace("%%", "%");
		s = s.replace("%25", "%");
		// System.out.println(s);
		Pattern PATTERN_UTF8 = Pattern.compile(
				"^(?:[\\x00-\\x7f]|[\\xfc-\\xff][\\x80-\\xbf]{5}|[\\xf8-\\xfb][\\x80-\\xbf]{4}|[\\xf0-\\xf7][\\x80-\\xbf]{3}|[\\xe0-\\xef][\\x80-\\xbf]{2}|[\\xc0-\\xdf][\\x80-\\xbf])+$");
		Pattern PATTERN_GBK = Pattern.compile(
				"(([\\x00-\\x7f])|([\\xa1-\\xa9][\\xa1-\\xfe])|([\\xb0-\\xf7][\\xa1-\\xfe])|([\\x81-\\xa0][\\x40-\\x7e\\x80\\xfe])|([\\xaa-\\xfe][\\x40-\\x7e\\x80\\xfe])|([\\xa8-\\xa9][\\x40-\\x7e\\x80\\xfe]))+");

		String keywordsTmp = s.toString().replace("http://www.", "");
		String unescapeString = unescape(keywordsTmp);
		if (unescapeString.isEmpty()) {
			return "";
		}
		String encodeString = "utf-8";
		if ("".equals(unescapeString)) {
			encodeString = "utf-8";
		} else {
			if (PATTERN_UTF8.matcher(unescapeString).matches()) {
				encodeString = "utf-8";
			} else if (PATTERN_GBK.matcher(unescapeString).matches()) {
				encodeString = "gbk";
			} else
				encodeString = "gb2312";

			/*
			 * if (!encodeMat.matches()) encodeString = "gb2312";
			 */
		}

		String tmp = "";
		try {
			tmp = URLDecoder.decode(keywordsTmp, encodeString);
		} catch (Exception e) {
			System.out.println(keywordsTmp);
			e.printStackTrace();
		}

		return tmp;
	}

	private static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;

		char ch;
		try {
			while (lastPos < src.length()) {
				pos = src.indexOf("%", lastPos);
				// System.out.println(pos);
				if (pos == lastPos) {
					if (pos + 1 > src.length() - 1) {
						return "";
					}

					if (src.charAt(pos + 1) == 'u') {
						ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
						tmp.append(ch);
						lastPos = pos + 6;
					} else {
						if (pos + 3 > src.length()) {
							return "";
						}
						ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
						tmp.append(ch);
						lastPos = pos + 3;
					}
				} else {
					if (pos == -1) {
						tmp.append(src.substring(lastPos));
						lastPos = src.length();
					} else {
						tmp.append(src.substring(lastPos, pos));
						lastPos = pos;
					}
				}
			}
		} catch (Exception e) {
			tmp.delete(0, tmp.length());

		}
		return tmp.toString();
	}

}
