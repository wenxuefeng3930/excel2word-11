package com.xiabaike.hadoop.tool;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.slf4j.LoggerFactory;

/**
 * 自定义组合键
 */
public class DoubleKey implements WritableComparable<DoubleKey>{

	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DoubleKey.class);
	
	private Text firstkey;
	private IntWritable secondkey;
	
	public DoubleKey() {
		this.firstkey = new Text();
		this.secondkey = new IntWritable();
	}
	
	public Text getFirstkey() {
		return firstkey;
	}

	public void setFirstkey(Text firstkey) {
		this.firstkey = firstkey;
	}

	public IntWritable getSecondkey() {
		return secondkey;
	}

	public void setSecondkey(IntWritable secondkey) {
		this.secondkey = secondkey;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		this.firstkey.write(out);
		this.secondkey.write(out);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.firstkey.readFields(in);
		this.secondkey.readFields(in);
	}

	/**
	 * 自定义比较策略 
	 * 注意：该比较策略用于mapreduce的第一次默认排序，也就是发生在map阶段的sort小阶段，
	 * 发生地点为环形缓冲区(可以通过io.sort.mb进行大小调整) 
	 */
	@Override
	public int compareTo(DoubleKey o) {
		 LOG.info("-------第一次默认排序    DoubleKey compareTo flag-------");
		 return this.firstkey.compareTo(o.getFirstkey()); 
	}

}
