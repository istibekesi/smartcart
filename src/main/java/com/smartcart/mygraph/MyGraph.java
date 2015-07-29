package com.smartcart.mygraph;

import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.FileSinkImages.Resolutions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smartcart.Application;

/**
 * Singleton pattern - we may have only one graph instance
 * 
 * @author Istvan
 */
public class MyGraph {
	
	private static final Logger log = LoggerFactory.getLogger(MyGraph.class);

	private static final Graph graph = new SingleGraph("My First graph 1");
	
	protected MyGraph() {
	}
	
	public static Graph getGraphInstance() {
		return graph;
	}
	
	public static void init() {

		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		 
		graph.addEdge("AB", "A", "B");
		graph.addEdge("AC", "A", "C");
		graph.addEdge("BC", "B", "C");
		 
		drawIt();

	}

	public static void addNode() {
		
		if (graph.getNode("X") != null) {
			graph.removeNode("X");
		} else {
			graph.addNode("X");
		}		
		
		
	}
	
	public static void drawIt(){
		try {
			FileSinkImages pic = new FileSinkImages(OutputType.PNG, Resolutions.VGA);
			 
			pic.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
			
			pic.writeAll(graph, "src/main/webapp/smartAdmin/smartGraph.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}		
	
	
}
