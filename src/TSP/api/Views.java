package TSP.api;

import TSP.GV;
import TSP.graph.Edge;
import TSP.graph.Graph;
import TSP.graph.Node;
import TSP.graph.Vector2D;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Views  {
    private static final AtomicInteger label = new AtomicInteger(0);
    Parent root;
    HBox toolbar;
    Pane centerPane;
    BorderPane borderPane;
    private State state;
    private Button btnAddEdge;
    private Button btnAddVertex;
    private Button btnMoveVertex;
    private Button btnIdle;
    private Text lblState;
    private Vector2D mousePosition;
    Graph graph = new Graph();
    private Map<Circle , Node> nodes;
    private Node firstNode;
    private Node secondNode;
    private Thread mousePositionListener = new Thread(() ->
       centerPane.addEventHandler(MouseEvent.ANY , event -> {
           mousePosition.set(event.getX() , event.getY());
       }));


    public Views()
    {
        this.state = State.IDLE;
        mousePosition = new Vector2D(0,0);
        setLayout();
        setToolbar();
        this.state = State.IDLE;
        mousePositionListener.start();

    }
    
    private void setLayout()
    {
        toolbar = new HBox();
        centerPane = new Pane();
        toolbar.setStyle("-fx-background-color: gray");
        centerPane.setStyle("-fx-background-color: white");
        centerPane.setPrefWidth(800);
        centerPane.setPrefHeight(600);
        borderPane = new BorderPane();
        borderPane.setCenter(centerPane);
        borderPane.setTop(toolbar);
        root = new Pane(borderPane);
        nodes = new HashMap<>();
    }
    
    private void setToolbar()
    {

        //btn add vertex setup
        btnAddVertex= new Button("add vertex");
        btnAddVertex.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            this.state = State.ADD_VERTEX;
            btnAddVertex.setStyle("-fx-background-radius: 0;-fx-background-color: white");
            btnAddEdge.setStyle("-fx-background-radius: 0;-fx-background-color: gray");
            btnMoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: gray");
            btnIdle.setStyle("-fx-background-radius: 0;-fx-background-color: gray");
        });

        //btn add Edge setup
        btnAddEdge = new Button("add edge");
        btnAddEdge.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            this.state = State.ADD_EDGE;
            btnAddVertex.setStyle("-fx-background-radius: 0;-fx-background-color: gray");
            btnAddEdge.setStyle("-fx-background-radius: 0;-fx-background-color: white");
            btnMoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: gray");
            btnIdle.setStyle("-fx-background-radius: 0;-fx-background-color: gray");
        });

        //btn Move setup
        btnMoveVertex = new Button("Move");
        btnMoveVertex.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            this.state = State.MOVE;
            btnAddVertex.setStyle("-fx-background-radius: 0;-fx-background-color: gray");
            btnAddEdge.setStyle("-fx-background-radius: 0;-fx-background-color: gray");
            btnMoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: white");
            btnIdle.setStyle("-fx-background-radius: 0;-fx-background-color: gray;-fx-border-insets: 0");
        });

        //btn Idle setup
        btnIdle = new Button("idle");
        btnIdle.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            this.state = State.IDLE;
            resetColors();
            btnAddVertex.setStyle("-fx-background-radius: 0;-fx-background-color: gray");
            btnAddEdge.setStyle("-fx-background-radius: 0;-fx-background-color: gray");
            btnMoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: gray");
            btnIdle.setStyle("-fx-background-radius: 0;-fx-background-color: white");

        } );

        btnAddVertex.setStyle("-fx-background-radius: 0;-fx-background-color: gray");
        btnAddEdge.setStyle("-fx-background-radius: 0;-fx-background-color: gray");
        btnMoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: gray");
        btnIdle.setStyle("-fx-background-radius: 0;-fx-background-color: white");

        toolbar.getChildren().addAll(btnIdle,btnAddVertex,btnAddEdge,btnMoveVertex);
    }
    
    public Parent getParent() {
        return root;

    }
    private void resetColors()
    {
        for(javafx.scene.Node n : centerPane.getChildren()){
            if(n instanceof Node)
            {
                ((Node)n).getCircle().setFill(Color.WHITE);
            }
        }
    }


    public void handleEvents()
    {

        centerPane.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            switch (state)
               {
                   case ADD_VERTEX:
                       Node node = new Node(label.getAndIncrement() + "" , new Vector2D(event.getX() , event.getY()));
                       nodes.put(node.getCircle() , node);
                       graph.addVertex(node);
                       node.addEventHandler(MouseEvent.MOUSE_DRAGGED,event1 -> {
                           switch (state)
                           {
                               case MOVE:
                                   node.setLayoutX(mousePosition.getX() - GV.radius);
                                   node.setLayoutY(mousePosition.getY() - GV.radius);
                                   System.out.println(graph.edges(node));
                                   graph.edges(node).forEach(e -> {
                                       e.setStartX(mousePosition.getX());
                                       e.setStartY(mousePosition.getY());
                                   });
                                   break;
                           }

                       });
                       centerPane.getChildren().addAll(node);
                       break;
                   case ADD_EDGE:
                       if(event.getTarget() instanceof Circle)
                       {
                           ((Circle)event.getTarget()).setFill(Color.RED);
                           firstNode = nodes.get( event.getTarget());
                           state = State.ADD_SECOND_EDGE;
                       }
                       break;
                   case ADD_SECOND_EDGE:
                       if(event.getTarget() instanceof Circle)
                       {
                           ((Circle)event.getTarget()).setFill(Color.RED);
                           secondNode= nodes.get(event.getTarget());
                           Edge edge = new Edge(firstNode , secondNode , 10);
                           centerPane.getChildren().addAll(edge , graph.re);
                           graph.addEdge(edge);
                       }
                       break;
                   case IDLE:
                       resetColors();
                       break;
               }

        });
    }

}
