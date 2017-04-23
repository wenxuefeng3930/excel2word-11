package com.xiabaike.hadoop.reduce;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.slf4j.LoggerFactory;

import com.xiabaike.hadoop.tool.DoubleKey;

public class SortReduce extends Reducer<DoubleKey, IntWritable, Text, Text>{
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SortReduce.class);

	StringBuffer sb = new StringBuffer();
    Text sore = new Text();
    
	@Override
	protected void reduce(DoubleKey key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		 sb.delete(0, sb.length());//先清除上一个组的数据
         Iterator<IntWritable> it = values.iterator();
         while(it.hasNext()){
             sb.append(it.next()+",");
         }
         //去除最后一个逗号
         if(sb.length()>0){
             sb.deleteCharAt(sb.length()-1);
         }
         sore.set(sb.toString());
         context.write(key.getFirstkey(),sore);
         LOG.info("---------enter reduce function flag---------");
         LOG.info("reduce Input data:{[" + key.getFirstkey() + "," + key.getSecondkey() + "],[" + sore + "]}");
         LOG.info("---------out reduce function flag---------");
	}
	
}
