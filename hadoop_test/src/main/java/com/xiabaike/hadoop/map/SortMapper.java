package com.xiabaike.hadoop.map;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.LoggerFactory;

import com.xiabaike.hadoop.tool.DoubleKey;

public class SortMapper extends Mapper<Text, Text, DoubleKey, IntWritable>{

	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SortMapper.class);
	
	DoubleKey combinationKey = new DoubleKey();
    Text sortName = new Text();
    IntWritable score = new IntWritable();
    String[] inputString = null;
    
	@Override
	protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
		 LOG.info("---------enter map function flag---------");
         //过滤非法记录
         if(key == null || value == null || key.toString().equals("")
                 || value.equals("")){
             return;
         }
         sortName.set(key.toString());
         score.set(Integer.parseInt(value.toString()));
         combinationKey.setFirstkey(sortName);
         combinationKey.setSecondkey(score);
         //map输出
         context.write(combinationKey, score);
         LOG.info("---------out map function flag---------");
	}
}
