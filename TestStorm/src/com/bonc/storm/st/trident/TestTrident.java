package com.bonc.storm.st.trident;

import java.awt.List;
import java.util.ArrayList;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.operation.builtin.Count;
import storm.trident.testing.FixedBatchSpout;
import storm.trident.testing.MemoryMapState;
import storm.trident.testing.Split;

public class TestTrident {
	
	public static void main(String[] args) {
		FixedBatchSpout spout = new FixedBatchSpout(new Fields("sentence"),2,
				new Values("this is centry!so happy"),
				new Values("我 在 这 里 啊，就 在 这 里 啊"));
		ArrayList<Object> list = new ArrayList<Object>();
		spout = new FixedBatchSpout(new Fields("sentence"),2, list);
		spout.setCycle(true);
		
		// 创建 topology 对象
		TridentTopology topology = new TridentTopology();
		// topology 中的 newStream方法从输入源中读取数据，并创建一个新的输出流
		TridentState woudCounts = topology.newStream("spout", spout).
				// 使用split 函数来拆分stream中的每一个tuple，读取输入流中的sentence字段并将其拆分成若干个word tuple
				each(new Fields("sentence"), new Split(), new Fields("word")).
				// 对word字段进行分组操作
				groupBy(new Fields("word")).
				// 每一个word分组使用count聚合器进行持久化聚合，persistentAggregate会把输入流转换为一个 TridentState 对象
				persistentAggregate(new MemoryMapState.Factory(), new Count(), new Fields("count")).
				// 设置并发度
				parallelismHint(1);
		woudCounts.toString();
		Config conf = new Config();
		conf.setDebug(true);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("test", conf, topology.build());
	}

}
