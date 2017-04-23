package com.bonc.storm.wordcount;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.netty.logging.Log4JLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class WordCountBolt extends BaseBasicBolt{

	private static final long serialVersionUID = 1L;
	
	private Map<String, Integer> map = new HashMap<String, Integer>();
	
	private static final Logger LOG = LoggerFactory.getLogger(WordCountBolt.class);
	
	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String word = input.getString(0);
		if(map.containsKey(word)) {
			int count = map.get(word);
			count++;
			map.put(word, count);
		}else{
			map.put(word, 1);
		}
		collector.emit(new Values(word, map.get(word)));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word", "count"));
	}

	@Override
    public void cleanup() {
		for(Map.Entry<String, Integer> entry : map.entrySet()) {
			LOG.debug("****  "+entry.getKey() +" : "+ entry.getValue());
		}
    }
}
