package TSP.graph;

import TSP.GV;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import sun.security.x509.EDIPartyName;

import java.awt.*;
import java.util.Optional;

public class Edge extends Group {

    private Line line;
    private Label weightLabel;
    private Node from;
    private Node to;
    private double weight;


    public Edge( Node from , Node to , double weight)
    {
        super();
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.line = new Line(from.getLocation().getX() , from.getLocation().getY(),to.getLocation().getX() , to.getLocation().getY());
        this.line.setStrokeWidth(GV.edgeStrokeSize);
        this.line.setStroke(GV.edgeColor);
        this.weightLabel = new Label(String.format("%.2f" , weight));
        this.weightLabel.setLayoutX((getFrom().getLocation().getX() + getTo().getLocation().getX())/2 - getWeightLabel().getWidth()/2);
        this.weightLabel.setLayoutY((getFrom().getLocation().getY() + getTo().getLocation().getY())/2 - getWeightLabel().getHeight()/2);
        this.weightLabel.setTextFill(Color.BLACK);
        this.weightLabel.setStyle("-fx-font-size: 15;-fx-background-color: "+GV.backgroundColor);
        System.out.println(this.weightLabel + " | " + this.getLine());
        this.getChildren().addAll(this.line, this.weightLabel);

    }

    private Edge( Node from , Node to , Line line , Label weightLabel , double weight)
    {
        this.from = from;
        this.to = to;
        this.line = line;
        this.weightLabel = weightLabel;
        this.weight = weight;
    }




    public Label getWeightLabel() {
        return weightLabel;
    }

    public void setWeightLabel(Label weightLabel) {
        this.weightLabel = weightLabel;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void switchEdge(Edge edge)
    {
        Node from = edge.getFrom();
        Node to = edge.getTo();
        Line line = edge.getLine();
        Label weightLabel = edge.getWeightLabel();
        double weight = edge.getWeight();

        edge.setFrom(this.from);
        edge.setTo(this.to);
        edge.setLine(this.line);
        edge.setWeightLabel(this.weightLabel);
        edge.setWeight(this.weight);

        this.setFrom(from);
        this.setTo(to);
        this.setLine(line);
        this.setWeightLabel(weightLabel);
        this.setWeight(weight);
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


    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }


    protected Edge reverse(){
        return new Edge(this.to , this.from , this.line , this.weightLabel , this.weight);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Edge) {
            Edge edge = (Edge)obj;
            if (this.getFrom().equals(edge.getFrom()) &&
                    this.getTo().equals(edge.getTo())) return true;
            if (this.getFrom().equals(edge.getTo()) &&
                    this.getTo().equals(edge.getFrom())) return true;
        }
        return false;
    }

    @Override
    public String toString() {

        return from.getLabel().getText() + " -> " + to.getLabel().getText() + " weight: " + weight;
    }

}



