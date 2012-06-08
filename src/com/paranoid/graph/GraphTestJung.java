
package com.paranoid.graph;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.io.GraphMLReader;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;

import org.apache.commons.collections15.Factory;
import org.xml.sax.SAXException;

import java.awt.Dimension;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

public class GraphTestJung extends JFrame {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        new GraphTestJung();
    }

    public GraphTestJung() throws IOException, ParserConfigurationException, SAXException {

        // Show simple graph creation and 
//        caseSimpleUndirectedGraph();

        // Show simple tree creation by code
//        createSimpleDirectedTree();

        // Show how to load a graph
//        caseDirectedGraphFromFile();

        caseShortestPath();
        //        graph = simpleTree();
//        visualize(graph, new CircleLayout(graph));
    }

    private void caseSimpleUndirectedGraph() {
        Graph<Integer, String> graph = new UndirectedSparseGraph<Integer, String>();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addEdge("1-2", 1, 2);
        graph.addEdge("2-3", 2, 3);
        graph.addEdge("2-4", 2, 4);
        System.out.println(graph.toString());
        Layout layout = new CircleLayout(graph);
        layout.setSize(new Dimension(300, 300));
        visualize(graph, layout);
    }

    private void createSimpleDirectedTree() {
        DirectedGraph<Integer, String> graph = new DirectedSparseGraph<Integer, String>();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addEdge("1-2", 1, 2);
        graph.addEdge("1-3", 1, 3);
        graph.addEdge("2-4", 2, 4);
        graph.addEdge("2-5", 2, 5);
        System.out.println(graph.toString());
        Forest<Integer, String> forest = new DelegateForest<Integer, String>(graph);
        visualize(graph, new TreeLayout<Integer, String>(forest));
    }

    private void caseDirectedGraphFromFile() {
        DirectedGraph<Number, Number> graph = loadDirectedGraph("first.graphml");
        
        visualize(graph, new CircleLayout(graph));
    }

    private void caseShortestPath() {
        Graph graph = loadDirectedGraph("shortest.graphml");
        DijkstraShortestPath<Number,Number> alg = new DijkstraShortestPath(graph);
        List<Number> l = alg.getPath(0, 2);
        System.out.println("The shortest unweighted path from" + 1 + 
    " to " + 4 + " is:");
        System.out.println(l.toString());    }


    private DirectedGraph<Number, Number> loadDirectedGraph(String filename) {
        try {
            GraphMLReader<DirectedGraph<Number, Number>, Number, Number> gmlr = new GraphMLReader<DirectedGraph<Number, Number>, Number, Number>(vertexFactory, edgeFactory);
            final DirectedGraph<Number, Number> graph = new DirectedSparseMultigraph<Number, Number>();
            gmlr.load("/Users/balazsbalazs/Code/github/graph-algorithms/" + filename, graph);
            return graph;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Graph simpleTree() throws IOException, ParserConfigurationException, SAXException {
        GraphMLReader<DirectedGraph<Number, Number>, Number, Number> gmlr = new GraphMLReader<DirectedGraph<Number, Number>, Number, Number>(vertexFactory, edgeFactory);
        final DirectedGraph<Number, Number> graph = new DirectedSparseMultigraph<Number, Number>();
        gmlr.load("/Users/balazsbalazs/Code/github/graph-algorithms/first.graphml", graph);
        return graph;
    }

    private void visualize(Graph graph, Layout layout) {
        BasicVisualizationServer<Integer, String> vv = new BasicVisualizationServer<Integer, String>(layout);
        vv.setPreferredSize(new Dimension(350, 350));
        getContentPane().add(vv);
        pack();
        setSize(1000, 800);
        setVisible(true);
    }

    Factory<Number> vertexFactory = new Factory<Number>() {
        int n = 0;

        public Number create() {
            return n++;
        }
    };
    Factory<Number> edgeFactory = new Factory<Number>() {
        int n = 0;

        public Number create() {
            return n++;
        }
    };

}
