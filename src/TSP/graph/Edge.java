package TSP.graph;

import TSP.GV;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

public class Edge extends Line {
    private Label weightLabel;
    private Node from;
    private Node to;
    private double weight;
    private boolean containsLine;
    public Edge(Node from , Node to , double weight)
    {
        super(from.getLocation().getX() , from.getLocation().getY(),to.getLocation().getX() , to.getLocation().getY());
        super.setStrokeWidth(GV.edgeStrokeSize);
        super.setStroke(GV.edgeColor);
        this.from = from;
        this.to = to;
        this.weight = weight;
        weightLabel = new Label(String.format("%.2f", weight));
        weightLabel.setLayoutX((from.getLocation().getX() + to.getLocation().getX())/2 - weightLabel.getWidth()/2);
        weightLabel.setLayoutY((from.getLocation().getY() + to.getLocation().getY())/2 - weightLabel.getHeight()/2);
        weightLabel.setTextFill(GV.nodeSelectedColor);
        weightLabel.setStyle("-fx-font-size: 15;-fx-background-color: white");

    }

    public Label getWeightLabel() {
        return weightLabel;
    }


    public double getMidPoint()
    {
        return Math.abs(this.getStartX() - this.getEndX())/2 + Math.abs(this.getStartY() - this.getEndY())/2;
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

    public double getWeight() {
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

        return from.getLabel().getText() + " -> " + to.getLabel().getText() + " weight: " + weight;
    }
}



