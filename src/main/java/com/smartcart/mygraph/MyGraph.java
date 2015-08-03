package com.smartcart.mygraph;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.FileSinkImages.Resolutions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.smartcart.domain.Edge;
import com.smartcart.domain.Product;
import com.smartcart.repository.EdgeRepository;
import com.smartcart.repository.ProductRepository;

/**
 * @author Istvan
 */
@Service
public class MyGraph {
	
	private static final Logger log = LoggerFactory.getLogger(MyGraph.class);

	@Inject
	private ProductRepository productRepository;
	
	@Inject
	private EdgeRepository edgeRepository;
	
	private static final Graph graph = new SingleGraph("My Product Graph");
	
	protected MyGraph() {
	}
	
	public static Graph getGraphInstance() {
		return graph;
	}
	
	@PostConstruct
	public void init() {

		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		 
		graph.addEdge("AB", "A", "B");
		graph.addEdge("AC", "A", "C");
		graph.addEdge("BC", "B", "C");
		 
		drawIt();

	}

	public void addNode() {
		
		Iterator<org.graphstream.graph.Edge> edgeIterator = graph.getEdgeIterator();
		for (; edgeIterator.hasNext() ; ){
			edgeIterator.next();
			edgeIterator.remove();
		}
		
		Iterator<Node> nodeIterator = graph.getNodeIterator();
		for (; nodeIterator.hasNext() ; ){
			nodeIterator.next();
			nodeIterator.remove();
		}
		
		List<Product> nodes = productRepository.findAll();
		List<Edge> edges = edgeRepository.findAll();
		
		nodes.forEach(n -> {
			Node addedNode = graph.addNode(String.valueOf(n.getId()));
			addedNode.setAttribute("ui.label", n.getName());
		});
		edges.forEach(e -> 
			graph.addEdge(
					String.valueOf(e.getSourceProduct().getId())+String.valueOf(e.getTargetProduct().getId()),
					String.valueOf(e.getSourceProduct().getId()),
					String.valueOf(e.getTargetProduct().getId()),
					true
			)
		);
		
		
	}
	
	public void drawIt(){
		try {
			
			graph.addAttribute("ui.stylesheet", "url('"+ new File("src/main/java/com/smartcart/mygraph/graphStyle.css").toURI() +"')");
			//graph.addAttribute("ui.stylesheet", "url('"+servletContext.getContextPath()+"http://smartAdmin/graphStyle.css')");
			
			FileSinkImages pic = new FileSinkImages(OutputType.PNG, Resolutions.VGA);
			
			pic.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
			
			pic.writeAll(graph, "src/main/webapp/smartAdmin/smartGraph.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}		
	
	
}
