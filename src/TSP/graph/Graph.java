package TSP.graph;

import sun.security.x509.EDIPartyName;

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
    public boolean addEdge(Edge edge){
        if(!adj.containsKey(edge.getFrom()) )
            adj.put(edge.getFrom() , new LinkedList());
        if(!adj.containsKey(edge.getTo()))
            adj.put(edge.getTo(), new LinkedList());
        if (edge.getFrom().equals(edge.getTo()))
            return false;

        Edge edge2 = reverseEdge(edge);
        if(adj.get(edge.getFrom()).contains(edge))
           return false;
        adj.get(edge.getFrom()).add(edge);
        adj.get(edge.getTo()).add(edge2);
        return true;

    }

    private Edge reverseEdge(Edge edge)
    {
        return new Edge(edge.getTo() , edge.getFrom() , edge.getWeight());
    }

    public List<Edge> edges(Node node)
    {
        return adj.get(node);
    }
    public void addVertex(Node vertex)
    {
        if(!adj.containsKey(vertex))
            adj.put(vertex , new LinkedList());
    }

    @Override
    public String toString() {
        StringBuilder graph = new StringBuilder();
        for (Node n : adj.keySet())
        {
            graph.append(n.getLabel().getText()+": {");
            for (Edge e : adj.get(n))
            {
                graph.append(e.getTo().getLabel().getText()+ " ," );
            }
            graph.append("\n");
        }
        return graph.toString();
    }
}
