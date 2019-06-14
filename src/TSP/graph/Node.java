package TSP.graph;

import TSP.GV;
import com.sun.javafx.scene.paint.GradientUtils;
import javafx.scene.layout.Border;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Node extends StackPane {


    private Text label;
    private Circle circle;
    private Vector2D location;

    public Node(String label ,Vector2D location){
        circle = new Circle(GV.radius ,Color.WHITE);
        circle.setStroke(Color.BLACK);
        this.label = new Text(label);
        this.label.setStyle("-fx-font-size: 25");
        this.location = location;
        super.setLayoutX(location.getX()-GV.radius);
        super.setLayoutY(location.getY()-GV.radius);
        super.getChildren().addAll(circle,this.label);
    }


    public Text getLabel() {
        return label;
    }

    public void setLabel(Text label) {
        this.label = label;
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
}