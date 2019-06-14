package TSP.graph;

import TSP.GV;
import com.sun.javafx.scene.paint.GradientUtils;
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
        this.label = new Text(label);
        super.setLayoutX(location.getX()-GV.radius);
        super.setLayoutY(location.getY()-GV.radius);
        super.getChildren().addAll(circle,this.label);
    }
}