package com.bonc.common.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RepeatTool {

	private List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();

	private List<Map<String, String>> delmaplist = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> newmaplist = new ArrayList<Map<String, String>>();

	public void addList(Map<String, String> map) {
		if (maplist.size() == 0) {
			// System.out.println("first"+map.get("type_code_bonc"));
			maplist.add(map);
		} else {
			boolean flag = false;
			for (int i = 0; i < maplist.size(); i++) {

				if (map.get("visit_type").equals("url")) {
					if (map.get("type_code_bonc").indexOf(maplist.get(i).get("type_code_bonc")) != -1) {
						delmaplist.add(maplist.get(i));
						flag = true;
						// System.out.println("repeate"+map.get("type_code_bonc"));
						newmaplist.add(map);

					} else if (maplist.get(i).get("type_code_bonc").indexOf(map.get("type_code_bonc")) != -1) {
						flag = true;
					}
				} else if (map.get("visit_type").equals("app")) {
					if (map.get("type_code_bonc").equals("")) {
						flag = false;
					} else if (map.get("type_code_bonc").indexOf(maplist.get(i).get("type_code_bonc")) != -1) {
						delmaplist.add(maplist.get(i));
						flag = true;
						// System.out.println("repeate"+map.get("type_code_bonc"));
						newmaplist.add(map);

					} else if (maplist.get(i).get("type_code_bonc").indexOf(map.get("type_code_bonc")) != -1
							&& !map.get("type_code_bonc").equals("")) {
						flag = true;
					}
				}
			}
			if (!flag) {
				newmaplist.add(map);
				// System.out.println("newadd"+map.get("type_code_bonc"));
			}
		}

		updateList();
		// System.out.println(maplist.size());
	}

	public void addListNew(Map<String, String> map) {
		if (maplist.size() == 0) {
			// System.out.println("first"+map.get("type_code_bonc"));
			maplist.add(map);
		} else {
			boolean flag = false;
			for (int i = 0; i < maplist.size(); i++) {
				if (map.get("type_code_bonc").indexOf(maplist.get(i).get("type_code_bonc")) != -1) {
					if ("".equals(map.get("app_type_code")) || map.get("app_type_code") == null) {
						if (!"".equals(maplist.get(i).get("app_type_code"))
								&& maplist.get(i).get("app_type_code") != null) {
							// map粒度更细，并且app_type_code为空，并且maplist.get(i)的app_type_code
							// 不为空，
							// 把maplist.get(i)相关项赋值给map，保留map，删除maplist.get(i)
							map.put("visit_type", maplist.get(i).get("visit_type"));
							map.put("app_type_code", maplist.get(i).get("app_type_code"));
							map.put("app_type_name1", maplist.get(i).get("app_type_name1"));
							map.put("app_type_name2", maplist.get(i).get("app_type_name2"));
							map.put("app_type_name3", maplist.get(i).get("app_type_name3"));
							map.put("site_id", maplist.get(i).get("site_id"));
							map.put("site_code", maplist.get(i).get("site_code"));
							delmaplist.add(maplist.get(i));
							flag = true;
							newmaplist.add(map);
						} else if ("".equals(maplist.get(i).get("app_type_code"))
								|| maplist.get(i).get("app_type_code") == null) {
							// map粒度更细，并且app_type_code为空，并且maplist.get(i)的app_type_code为空，
							// 保留map，删除maplist.get(i)
							delmaplist.add(maplist.get(i));
							flag = true;
							newmaplist.add(map);
						}
					} else if (!"".equals(map.get("app_type_code")) && map.get("app_type_code") != null) {
						// map粒度更细，并且app_type_code不为空，无论maplist.get(i)的app_type_code是否为空，
						// 保留map，删除maplist.get(i)
						delmaplist.add(maplist.get(i));
						flag = true;
						newmaplist.add(map);
					}
				} else if (maplist.get(i).get("type_code_bonc").indexOf(map.get("type_code_bonc")) != -1) {
					if ("".equals(maplist.get(i).get("app_type_code")) || maplist.get(i).get("app_type_code") == null) {
						if (!"".equals(map.get("app_type_code")) && map.get("app_type_code") != null) {
							// maplist粒度更细，并且app_type_code为空，并且map的app_type_code不为空，
							// 把map相关项赋值给maplist.get(i)，保留maplist.get(i)，删除map
							maplist.get(i).put("visit_type", map.get("visit_type"));
							maplist.get(i).put("app_type_code", map.get("app_type_code"));
							maplist.get(i).put("app_type_name1", map.get("app_type_name1"));
							maplist.get(i).put("app_type_name2", map.get("app_type_name2"));
							maplist.get(i).put("app_type_name3", map.get("app_type_name3"));
							maplist.get(i).put("site_id", map.get("site_id"));
							maplist.get(i).put("site_code", map.get("site_code"));
							flag = true;
						} else if ("".equals(map.get("app_type_code")) || map.get("app_type_code") == null) {
							// maplist粒度更细，并且app_type_code为空，并且map的app_type_code为空，此情况不作处理，保留maplist
							flag = true;
						}
					} else if (!"".equals(maplist.get(i).get("app_type_code"))
							&& maplist.get(i).get("app_type_code") != null) {
						// 对于maplist粒度更细，并且app_type_code不为空的情况，无论map的app_type_code怎样，都不作处理，保留maplist
						flag = true;
					}
				}
			}
			if (!flag) {
				newmaplist.add(map);
				// System.out.println("newadd"+map.get("type_code_bonc"));
			}
		}
		updateList();
		// System.out.println(maplist.size());
	}

	public void updateList() {
		maplist.removeAll(delmaplist);
		maplist.addAll(newmaplist);
		delmaplist.clear();
		newmaplist.clear();
	}

	public List<Map<String, String>> getList() {

		return maplist;
	}

	/**
	 * @param 去重
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		 * Map<String,String> map1 = new HashMap<String,String>();
		 * map1.put("type_id", "U0101") ; addList(map1);
		 * 
		 * Map<String,String> map2 = new HashMap<String,String>();
		 * map2.put("type_id", "U0101") ; addList(map2);
		 * 
		 * Map<String,String> map3 = new HashMap<String,String>();
		 * map3.put("type_id", "U02") ; addList(map3);
		 * 
		 * getList();
		 * 
		 * for(int i = 0;i<maplist.size();i++){
		 * System.out.println(maplist.get(i)); }
		 */

	}

}
