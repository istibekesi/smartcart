package com.smartcart.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.smartcart.mygraph.MyGraph;

@Service
public class GraphPngService {

	private final Logger log = LoggerFactory.getLogger(GraphPngService.class);

	public void drawGraphPng() {
		
		MyGraph.addNode();
		
		MyGraph.drawIt();
		
		
	}
	
}
