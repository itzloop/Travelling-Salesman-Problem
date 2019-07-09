package TSP.graph;

import TSP.GV;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

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
        //System.out.println((int)(Math.random()*255)  + " | " + (int)(Math.random()*255)  + " | " + (int)(Math.random()*255) );
        //this.line.setStroke(Color.rgb((int)(Math.random()*255) , (int)(Math.random()*255 ), (int)(Math.random()*255)));
        this.line.setStroke(Color.BLACK);
        this.weightLabel = new Label(String.format("%.2f" , weight));
        this.weightLabel.setLayoutX((getFrom().getLocation().getX() + getTo().getLocation().getX())/2 - getWeightLabel().getWidth()/2);
        this.weightLabel.setLayoutY((getFrom().getLocation().getY() + getTo().getLocation().getY())/2 - getWeightLabel().getHeight()/2);
        this.weightLabel.setTextFill(Color.BLACK);
        this.weightLabel.setStyle("-fx-font-size: 15;-fx-background-color: "+GV.backgroundColor);
        this.getChildren().addAll(this.line, this.weightLabel);
        if(!GV.showWeight)
            this.weightLabel.setVisible(false);

    }

    public Edge( Node from , Node to , Line line , Label weightLabel , double weight)
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

    public void setFrom(Node from)
    {
        this.from = from;
        updateLocation();
    }

    public void setTo(Node to) {
        this.to = to;
        updateLocation();
    }

    void updateLocation()
    {
        line.setStartX(from.getLocation().getX());
        line.setStartY(from.getLocation().getY());
        line.setEndX(to.getLocation().getX());
        line.setEndY(to.getLocation().getY());
        weight = from.distance(to);
    }

    public void setWeight(double weight) {
        this.weight = weight;
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



