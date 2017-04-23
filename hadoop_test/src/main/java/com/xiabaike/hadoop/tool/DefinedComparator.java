package com.xiabaike.hadoop.tool;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.slf4j.LoggerFactory;

/**
 * 自定义二次排序策略
 */
public class DefinedComparator extends WritableComparator {
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DefinedComparator.class);

	public DefinedComparator() {
		super(DoubleKey.class, true);
	}
	
	@Override
	public int compare(WritableComparable combinationKeyOne, WritableComparable CombinationKeyOther) { 
        LOG.info("---------自定义二次排序  enter DefinedComparator flag---------"); 
                                                      
        DoubleKey c1 = (DoubleKey) combinationKeyOne; 
        DoubleKey c2 = (DoubleKey) CombinationKeyOther; 
                                                      
        /** 
         * 确保进行排序的数据在同一个区内，如果不在同一个区则按照组合键中第一个键排序 
         * 另外，这个判断是可以调整最终输出的组合键第一个值的排序
         * 下面这种比较对第一个字段的排序是升序的，如果想降序这将c1和c2倒过来（假设1）
         */
        if(!c1.getFirstkey().equals(c2.getFirstkey())){ 
            LOG.info("---------自定义二次排序    out DefinedComparator flag---------"); 
            return c1.getFirstkey().compareTo(c2.getFirstkey()); 
        }else{// 按照组合键的第二个键的升序排序，将c1和c2倒过来则是按照数字的降序排序(假设2) 
        	LOG.info("---------自定义二次排序   out DefinedComparator flag---------"); 
            return c1.getSecondkey().get() - c2.getSecondkey().get(); // 0,负数,正数 
        }
	}
}
