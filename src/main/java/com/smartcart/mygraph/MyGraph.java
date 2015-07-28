package com.smartcart.mygraph;

import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.FileSinkImages.Resolutions;

public class MyGraph {

	private Graph graph;
	
	public MyGraph() {
		this.graph = new SingleGraph("My First graph 1");
	}
	
	public void init() {
		//graph.display();
		
		FileSinkImages pic = new FileSinkImages(OutputType.PNG, Resolutions.VGA);
		 
		pic.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
		 
		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		 
		graph.addEdge("AB", "A", "B");
		graph.addEdge("AC", "A", "C");
		graph.addEdge("BC", "B", "C");
		 
		try {
			pic.writeAll(graph, "myGraph.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
