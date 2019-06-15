package TSP.graph;

import TSP.GV;
import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

public class Edge extends Line {
    private Node from;
    private Node to;
    private int weight;
    private boolean containsLine;
    public Edge(Node from , Node to , int weight)
    {
        super(from.getLocation().getX() , from.getLocation().getY(),to.getLocation().getX() , to.getLocation().getY());
        super.setStrokeWidth(GV.edgeStrokeSize);
        super.setStroke(GV.edgeColor);
        this.from = from;
        this.to = to;
        this.weight = weight;
        //System.out.println(from.getCircle().getCenterX()+":"+from.getCircle().getLayoutY()+":"+to.getCircle().getCenterX()+":"+to.getCircle().getCenterY());

    }

    public boolean isContainsLine() {
        return containsLine;
    }

    public void setContainsLine(boolean containsLine) {
        this.containsLine = containsLine;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Edge)
        {
            Edge edge = (Edge)obj;
            String from = this.getFrom().getLabel().getText();
            String from2= edge.getFrom().getLabel().getText();
            String to = this.getTo().getLabel().getText();
            String to2 = edge.getTo().getLabel().getText();
            if(from.equals(from2) && to.equals(to2))
                return true;
        }
        return false;
    }


    @Override
    public String toString() {

        return from.getLabel().getText() + " <-> " + to.getLabel().getText();
    }
}



