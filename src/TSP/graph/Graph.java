package TSP.graph;


import TSP.GV;

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
        adj.get(edge.getTo()).add(edge.reverse());
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


    public List<Edge> getAdj(Node node)
    {
        return adj.get(node);
    }
    public void addNode(Node vertex)
    {
        if(!adj.containsKey(vertex))
            adj.put(vertex , new LinkedList());
    }

    public List<Edge> removeNode(Node node)
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
                        adj.get(e.getTo()).remove(tempEdges.get(i));

                    }
                }
            }
        }
        adj.remove(node);
        System.out.println(edges);
        return edges;
    }




    public void moveNode(Node node){

        node.setLayoutX(GV.mousePosition.getX() - GV.radius);
        node.setLayoutY(GV.mousePosition.getY()- GV.radius);
        node.getLocation().set(GV.mousePosition.getX() , GV.mousePosition.getY());
        this.getAdj(node).forEach(e -> {
            e.getWeightLabel().setLayoutX((e.getFrom().getLocation().getX() + e.getTo().getLocation().getX())/2 - (e.getWeightLabel().getWidth()/2));
            e.getWeightLabel().setLayoutY((e.getFrom().getLocation().getY() + e.getTo().getLocation().getY())/2 - (e.getWeightLabel().getHeight()/2));
            e.getWeightLabel().setText(String.format("%.2f" ,e.getFrom().distance(e.getTo()) ));
            e.getLine().setStartX(e.getTo().getLocation().getX());
            e.getLine().setStartY(e.getTo().getLocation().getY());
            e.getLine().setEndX(e.getFrom().getLocation().getX());
            e.getLine().setEndY(e.getFrom().getLocation().getY());
        });
    }




    @Override
    public String toString() {
        StringBuilder graph = new StringBuilder();
        for (Node n : adj.keySet())
        {
            graph.append(n.getLabel().getText()+ n.getLocation()+ ": {");
            for (Edge e : adj.get(n))
            {
                graph.append(e.getTo().getLabel().getText()+ " ," );
            }
            graph.append("\n*********************************\n");
        }
        return graph.toString();
    }
}
