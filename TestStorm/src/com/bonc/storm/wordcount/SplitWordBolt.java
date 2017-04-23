package com.bonc.storm.wordcount;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.MessageId;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class SplitWordBolt extends BaseBasicBolt{
	
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String words = input.getStringByField("word");
//		String sss = input.getStringByField("sss");
//		MessageId mid = input.getMessageId();
//		input.getSourceTask();
		String[] wordArr = words.split(" ");
		for(String word : wordArr) {
			collector.emit(new Values(word));
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}


}
