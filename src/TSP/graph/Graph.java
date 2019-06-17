package TSP.graph;


import java.util.*;

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
        if(adj.get(edge.getFrom()).contains(edge))
           return false;
        adj.get(edge.getFrom()).add(edge);
        adj.get(edge.getTo()).add(reverseEdge(edge));
        return true;

    }

    public boolean removeEdge(Edge edge) {
        if (!adj.containsKey(edge.getFrom()) || !adj.containsKey(edge.getTo()))
            return false;
        Iterator<Edge> fromAdj = adj.get(edge.getFrom()).iterator();
        Iterator<Edge> toAdj = adj.get(edge.getTo()).iterator();
        while (fromAdj.hasNext())
        {
            Edge e = fromAdj.next();
            if(e.getTo().equals(edge.getTo()))
            {
                fromAdj.remove();
                break;
            }
        }

        while (toAdj.hasNext())
        {
            Edge e = toAdj.next();
            if(e.getFrom().equals(edge.getTo()))
            {
                toAdj.remove();
                break;
            }
        }
        return true;
    }


    public Edge getEdge(Node from , Node to)
    {
        for(Edge e : adj.get(from))
        {
            if(e.getFrom().equals(from) && e.getTo().equals(to))
                return e;
        }
        return null;
    }


    private Edge reverseEdge(Edge edge)
    {
        Edge e = new Edge(edge.getTo() , edge.getFrom() , edge.getWeight());
        e.setContainsLine(false);
        return e;
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

    public List<Edge> removeVertex(Node node)
    {
        if(!adj.containsKey(node))
            return null;
        List<Edge> edges = new ArrayList<>();
        edges.addAll(adj.get(node));
        for (Edge e : adj.get(node))
        {
            List<Edge> tempEdges = new ArrayList<>();
            if(adj.get(e.getTo()) != null)
            {
                tempEdges.addAll(adj.get(e.getTo()));

                for (int i = 0 ; i < tempEdges.size() ; i++)
                {
                    if(tempEdges.get(i).getTo().equals(node))
                    {
                        edges.add(tempEdges.get(i));

                    }
                }
                adj.get(e.getTo()).removeAll(tempEdges);
            }
        }
        adj.remove(node);
        System.out.println(edges);
        return edges;
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
