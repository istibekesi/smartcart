package com.smartcart.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.smartcart.mygraph.MyGraph;

@Service
public class GraphPngService {

	private final Logger log = LoggerFactory.getLogger(GraphPngService.class);
	
	@Inject
	MyGraph myGraphService;

	public void drawGraphPng() {
		
		myGraphService.addNode();
		
		myGraphService.drawIt();
		
		
	}
	
}
