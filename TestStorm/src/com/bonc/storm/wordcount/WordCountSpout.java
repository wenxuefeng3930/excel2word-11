package com.bonc.storm.wordcount;

import java.util.Map;
import java.util.Random;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class WordCountSpout extends BaseRichSpout{

	private static final long serialVersionUID = 19900821L;

	private SpoutOutputCollector collector;
	private Random random;
	private static final String[] outArr = new String[]{"one","two two",
			"three three three","four four four four","five five five five five"};
	
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
		this.random = new Random();
	}

	@Override
	public void nextTuple() {
		String sentence = outArr[random.nextInt(outArr.length)];
		collector.emit(new Values(sentence));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

}
