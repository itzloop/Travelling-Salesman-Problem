package TSP.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph {

    int vertices;
    Map<Node,List<Edge>> adj;

    public Graph()
    {
        this.vertices = 0;
        adj = new HashMap<>();

    }
    public void addEdge(Node from , Node to , int weight ){
        if(!adj.containsKey(from))
            adj.put(from , new LinkedList<>());
        if(!adj.containsKey(to))
            adj.put(to , new LinkedList<>());
        Edge edge = new Edge(from , to , weight);
        adj.get(from).add(edge);
        adj.get(to).add(edge);
    }

    public void addVertex(Node vertex)
    {
        if(!adj.containsKey(vertex))
            adj.put(vertex , new LinkedList<>());
    }



}
