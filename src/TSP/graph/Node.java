package TSP.graph;

import TSP.GV;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;


public class Node extends StackPane {

    private int index;
    private Text label;
    private Circle circle;
    private Vector2D location;
    public Node(String label ,Vector2D location){
        circle = new Circle(GV.radius ,GV.nodeFillColor);
        circle.setStroke(GV.nodeStrokeColor);
        circle.setStrokeWidth(GV.nodeStrokeSize);
        index = Integer.parseInt(label);
        this.label = new Text(label);
        this.label.setStyle("-fx-font-size: 25");
        this.location = location;
        super.setLayoutX(location.getX()-GV.radius);
        super.setLayoutY(location.getY()-GV.radius);
        super.getChildren().addAll(circle,this.label);
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Text getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label.setText(label);
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public Vector2D getLocation() {
        return location;
    }

    public void setLocation(Vector2D location) {
        this.location = location;
    }


    public double distance( Node to)
    {
        return this.location.distance(to.getLocation());
    }



    @Override
    public String toString() {
        return label.getText() + " , ";
    }

    @Override
    public boolean equals( Object obj) {
        if(obj instanceof Node)
        {
            Node node = (Node)obj;
            return this.getLabel().getText().equals(node.getLabel().getText());
        }
        return false;
    }
//
//    @Override
//    public Node clone()  {
//        return new Node(this.label.getText() , this.location.clone());
//    }
}