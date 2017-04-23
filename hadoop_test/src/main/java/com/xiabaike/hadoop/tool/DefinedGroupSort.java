package com.xiabaike.hadoop.tool;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.slf4j.LoggerFactory;

/** 
 * 自定义分组策略
 * 将组合将中第一个值相同的分在一组
 */
public class DefinedGroupSort extends WritableComparator{
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DefinedGroupSort.class);

	public DefinedGroupSort() {
        super(DoubleKey.class, true);
    }
	
	@Override
    public int compare(WritableComparable a, WritableComparable b) {
        LOG.info("-------自定义分组   enter DefinedGroupSort flag-------");
        DoubleKey ck1 = (DoubleKey) a;
        DoubleKey ck2 = (DoubleKey) b;
        LOG.info("-------自定义分组   Grouping result : " + ck1.getFirstkey().compareTo(ck2.getFirstkey()) + "-------");
        LOG.info("-------自定义分组   out DefinedGroupSort flag-------");
        return ck1.getFirstkey().compareTo(ck2.getFirstkey());
    }
}
