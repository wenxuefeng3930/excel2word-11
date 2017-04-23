package com.bonc.storm.wordcount;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class WordCountTopology {
	
	public static void main(String[] args) {
		
		TopologyBuilder topology = new TopologyBuilder();
		topology.setSpout("xbk_spout", new WordCountSpout(),1);	
		topology.setBolt("xbk_split", new SplitWordBolt(), 1).shuffleGrouping("xbk_spout");
		topology.setBolt("xbk_count", new WordCountBolt(), 5).fieldsGrouping("xbk_split", new Fields("word"));
		Config conf = new Config();
		conf.setDebug(true);
		if(args != null && args.length > 0) {
			conf.setNumWorkers(3);
			try {
				StormSubmitter.submitTopology("WordCountTopology", conf, topology.createTopology());
			} catch (AlreadyAliveException e) {
				e.printStackTrace();
			} catch (InvalidTopologyException e) {
				e.printStackTrace();
			}
		} else {
			conf.setMaxTaskParallelism(1);
			
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("WordCountTopology", conf, topology.createTopology());
			try {
				Thread.sleep(2200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			cluster.shutdown();
		}
	}
	
}
