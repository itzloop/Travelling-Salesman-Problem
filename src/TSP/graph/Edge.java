package TSP.graph;

import javafx.scene.shape.Line;

public class Edge extends Line {
    private Node from;
    private Node to;
    private int weight;
    Edge(Node from , Node to , int weight)
    {
        this.from = from;
        this.to = to;
        this.weight = weight;

    }

}
