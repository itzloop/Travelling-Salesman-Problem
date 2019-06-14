package TSP.api;

import TSP.GV;
import TSP.graph.Node;
import TSP.graph.Vector2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;

public class Views  {
    Parent root;
    HBox toolbar;
    Pane centerPane;
    BorderPane borderPane;
    private State state;
    private Button btnAddEdge;
    private Button btnAddVertex;
    private Button btnIdle;
    private Text lblState;
    
    
    public Views()
    {
        this.state = State.IDLE;
        setLayout();
        setToolbar();
    }
    
    private void setLayout()
    {
        toolbar = new HBox();
        centerPane = new Pane();
        toolbar.setStyle("-fx-background-color: red");
        centerPane.setStyle("-fx-background-color: blue");
        centerPane.setPrefWidth(800);
        centerPane.setPrefHeight(600);
        borderPane = new BorderPane();
        borderPane.setCenter(centerPane);
        borderPane.setTop(toolbar);
        root = new Pane(borderPane);
    }
    
    private void setToolbar()
    {

        //btn add vertex setup
        btnAddVertex= new Button("add vertex");
        btnAddVertex.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> this.state = State.ADD_VERTEX);

        //btn add Edge setup
        btnAddEdge = new Button("add edge");
        btnAddEdge.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> this.state = State.ADD_EDGE);

        //btn Idle setup
        btnIdle = new Button("idle");
        btnIdle.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> this.state = State.IDLE );


        toolbar.getChildren().addAll(btnAddVertex,btnAddEdge , btnIdle);
    }
    
    public Parent getParent() {
        return root;

    }


    public void handleEvents()
    {
        centerPane.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            System.out.println(event.getSceneY()+","+event.getSceneY());
               switch (state)
               {
                   case ADD_VERTEX:
                       Node node = new Node("a" , new Vector2D(event.getX() , event.getY()));
//                       Line line = new Line(event.getX() , event.getY() , 100 , 100);
                       centerPane.getChildren().addAll(node);
                       break;
                   case ADD_EDGE:
                       System.out.println(event);
                       ((Circle)event.getTarget()).setFill(Color.RED);
                       break;
                   case IDLE:
                       break;
               }

        });
    }

}
