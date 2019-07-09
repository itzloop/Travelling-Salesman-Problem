package TSP.graph;


import TSP.GV;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.*;

public class Graph {

    private int vertices;
    Map<Node,LinkedList<Edge>> adj;

    public Graph()
    {
        this.vertices = 0;
        adj = new LinkedHashMap<>();

    }
    public boolean addEdge(Edge edge){
        if(!adj.containsKey(edge.getFrom()) )
            addNode(edge.getFrom());
        if(!adj.containsKey(edge.getTo()))
            addNode(edge.getTo());
        if (edge.getFrom().equals(edge.getTo()))
            return false;
        if(adj.get(edge.getFrom()).contains(edge))
           return false;
        adj.get(edge.getFrom()).add(edge);
        adj.get(edge.getTo()).add(edge.reverse());
        return true;

    }



    public Set<Node> getAdjKeySet() {
        return adj.keySet();
    }



    public void printNode(Node[] nodes)
    {
        for (Node node : nodes)
        {
            System.out.print(node+ " ");
        }
        System.out.println();
    }

    public void clearEdges()
    {
        for (Node n : adj.keySet())
        {
            adj.put(n , new LinkedList<>());
        }
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
    public void addNode(Node node)
    {
        if(!adj.containsKey(node))
        {
            adj.put(node , new LinkedList());
            this.vertices++;
        }
    }

    public Optional<List<Edge>> removeNode(Node node)
    {
        if(!adj.containsKey(node))
            return Optional.empty();
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

        return Optional.ofNullable(edges);
    }




    public void moveNode(Node node){

        node.setLayoutX(GV.mousePosition.getX() - GV.radius);
        node.setLayoutY(GV.mousePosition.getY() - GV.radius);
        node.getLocation().set(GV.mousePosition.getX() , GV.mousePosition.getY());

        System.out.println(this.getAdj(node));
        if(this.adj.get(node) == null)
            return;
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



    public boolean contains(Edge e)
    {
        for (Node n : adj.keySet())
        {
            if(adj.get(n).contains(e) || adj.get(n).contains(e.reverse()))
                return true;
        }
        return false;
    }

    @Override
    public Graph clone() {
        Graph graph = new Graph();
        for (Node n : this.adj.keySet())
        {
            for (Edge e : this.adj.get(n))
            {
                graph.addEdge(new Edge(n , e.getTo() , e.getWeight()));
            }
        }
        return graph;
    }
}
