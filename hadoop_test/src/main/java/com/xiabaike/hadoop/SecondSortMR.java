package com.xiabaike.hadoop;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.xiabaike.hadoop.map.SortMapper;
import com.xiabaike.hadoop.reduce.SortReduce;
import com.xiabaike.hadoop.tool.DefinedComparator;
import com.xiabaike.hadoop.tool.DefinedGroupSort;
import com.xiabaike.hadoop.tool.DefinedPartition;
import com.xiabaike.hadoop.tool.DoubleKey;

public class SecondSortMR extends Configured implements Tool {

	public static void main(String[] args) {
		System.out.println("===========jobstarttime:"+ new SimpleDateFormat("yyyy-MM–dd HH:mm:ss").format(new Date()));
		try {
			ToolRunner.run(new Configuration(), new SecondSortMR(), args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("===========jobendtime:"+ new SimpleDateFormat("yyyy-MM–dd HH:mm:ss").format(new Date()));
	}
	
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf(); //获得配置文件对象
		FileSystem dst = FileSystem.get(conf);
		Path output = new Path(args[1]);
		dst.delete(output,true);
		
        Job job = Job.getInstance(conf, "SoreSort");
        job.setJarByClass(SecondSortMR.class);
        FileInputFormat.addInputPath(job, new Path(args[0])); //设置map输入文件路径
        FileOutputFormat.setOutputPath(job, new Path(args[1])); //设置reduce输出文件路径

        job.setMapperClass(SortMapper.class);
        //设置map的输出key和value类型
        job.setMapOutputKeyClass(DoubleKey.class);
        job.setMapOutputValueClass(IntWritable.class);
        //设置reduce的输出key和value类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setReducerClass(SortReduce.class);
        job.setPartitionerClass(DefinedPartition.class); //设置自定义分区策略
        job.setSortComparatorClass(DefinedComparator.class); //设置自定义二次排序策略
        job.setGroupingComparatorClass(DefinedGroupSort.class); //设置自定义分组策略
        job.setInputFormatClass(KeyValueTextInputFormat.class); //设置文件输入格式
        job.setOutputFormatClass(TextOutputFormat.class);//使用默认的output格式
        job.setNumReduceTasks(2);
        job.waitForCompletion(true);
        return job.isSuccessful()?0:1;
	}

}
