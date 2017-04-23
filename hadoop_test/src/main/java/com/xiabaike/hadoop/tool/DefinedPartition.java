package com.xiabaike.hadoop.tool;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;
import org.slf4j.LoggerFactory;

/**
 * 自定义分区
 */
public class DefinedPartition extends Partitioner<DoubleKey, IntWritable> {

	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DefinedPartition.class);
	
	/**
	 * 注意：这里采用默认的hash分区实现方法 
	 * 根据组合键的第一个值作为分区 
	 * 这里需要说明一下，如果不自定义分区的话，mapreduce框架会根据默认的hash分区方法， 
	 * 将整个组合将相等的分到一个分区中，这样的话显然不是我们要的效果 
	 */
	@Override
	public int getPartition(DoubleKey key, IntWritable value, int numPartitions) {
		LOG.info("-----------自定义分区  ----------------");
		return ( key.getFirstkey().hashCode() & Integer.MAX_VALUE ) % numPartitions;
	}

}
