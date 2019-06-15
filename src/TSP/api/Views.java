package TSP.api;

import TSP.GV;
import TSP.graph.Edge;
import TSP.graph.Graph;
import TSP.graph.Node;
import TSP.graph.Vector2D;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
import javafx.stage.Screen;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Views  {
    private static final AtomicInteger label = new AtomicInteger(0);
    Parent root;
    HBox toolbar;
    Pane centerPane;
    BorderPane borderPane;
    private State state;
    private Button btnIdle;
    private Button btnAddVertex;
    private Button btnMoveVertex;
    private Button btnRemoveVertex;
    private Button btnAddEdge;
    private Button btnRemoveEdge;
    private Button btnReset;

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
        Platform.setImplicitExit(false);
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
        System.out.println(GV.toolbarColor);
        toolbar.setStyle("-fx-background-color: " + GV.toolbarColor);
        centerPane.setStyle("-fx-background-color: " + GV.backgroundColor);
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
        //btn Idle setup
        btnIdle = new Button("idle");
        btnIdle.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            this.state = State.IDLE;
            resetColors();
            btnIdle.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.backgroundColor);
            btnAddVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnMoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnRemoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnAddEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnRemoveEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnReset.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);

        });

        //btn add vertex setup
        btnAddVertex= new Button("add vertex");
        btnAddVertex.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            this.state = State.ADD_VERTEX;
            btnIdle.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnAddVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.backgroundColor);
            btnMoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnRemoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnAddEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnRemoveEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnReset.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        });



        //btn Move Vertex setup
        btnMoveVertex = new Button("Move");
        btnMoveVertex.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            this.state = State.MOVE;
            resetColors();
            btnIdle.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnAddVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnMoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.backgroundColor);
            btnRemoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnAddEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnRemoveEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnReset.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        });

        //btn Remove vertex setup
        btnRemoveVertex = new Button("remove vertex");
        btnRemoveVertex.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            this.state = State.REMOVE_VERTEX;
            btnIdle.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnAddVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnMoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnRemoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.backgroundColor);
            btnAddEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnRemoveEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnReset.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        });

        //btn add Edge setup
        btnAddEdge = new Button("add edge");
        btnAddEdge.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            this.state = State.ADD_EDGE;
            btnIdle.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnAddVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnMoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnRemoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnAddEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.backgroundColor);
            btnRemoveEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnReset.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        });

        //btn Remove Edge setup
        btnRemoveEdge = new Button("remove edge");
        btnRemoveEdge.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            this.state = State.REMOVE_EDGE;
            btnIdle.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnAddVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnMoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnRemoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnAddEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnRemoveEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.backgroundColor);
            btnReset.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        });

        //btn Reset setup
        btnReset = new Button("reset");
        btnReset.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            this.state = State.RESET;
            btnIdle.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnAddVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnMoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnRemoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnAddEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnRemoveEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnReset.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.backgroundColor);
        });

        btnIdle.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.backgroundColor);
        btnAddVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnMoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnRemoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnAddEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnRemoveEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnReset.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);

        toolbar.getChildren().addAll(btnIdle,btnAddVertex,btnMoveVertex,btnRemoveVertex,btnAddEdge,btnRemoveEdge,btnReset);
    }
    
    public Parent getParent() {
        return root;

    }
    private void resetColors()
    {
        for(javafx.scene.Node n : centerPane.getChildren()){
            if(n instanceof Node)
            {
                ((Node)n).getCircle().setFill(GV.nodeFillColor);
            }
        }
    }


    public void handleEvents()
    {


        centerPane.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            Node node;
            switch (state)
               {
                   case ADD_VERTEX:
                       node = new Node(label.getAndIncrement() + "" , new Vector2D(event.getX() , event.getY()));
                       nodes.put(node.getCircle() , node);
                       graph.addVertex(node);
                       node.addEventHandler(MouseEvent.MOUSE_DRAGGED,event1 -> {
                           switch (state)
                           {

                               case MOVE:
                                   node.setLayoutX(mousePosition.getX() - GV.radius);
                                   node.setLayoutY(mousePosition.getY() - GV.radius);
                                   node.getLocation().set(mousePosition.getX() , mousePosition.getY());
                                   graph.edges(node).forEach(e -> {
                                       if(e.isContainsLine())
                                       {
                                           e.setStartX(e.getTo().getLocation().getX());
                                           e.setStartY(e.getTo().getLocation().getY());
                                           e.setEndX(e.getFrom().getLocation().getX());
                                           e.setEndY(e.getFrom().getLocation().getY());
                                       }
                                       else {
                                           Edge edge = graph.getEdge(e.getTo() , e.getFrom());
                                           edge.setStartX(edge.getFrom().getLocation().getX());
                                           edge.setStartY(edge.getFrom().getLocation().getY());
                                           edge.setEndX(edge.getTo().getLocation().getX());
                                           edge.setEndY(edge.getTo().getLocation().getY());

                                       }
                                   });
                                   break;
                           }

                       });
                       centerPane.getChildren().addAll(node);
                       break;
                   case ADD_EDGE:
                       if(event.getTarget() instanceof Circle)
                       {
                           ((Circle)event.getTarget()).setFill(Color.rgb(209, 57, 61));
                           firstNode = nodes.get( event.getTarget());
                           state = State.ADD_SECOND_EDGE;
                       }
                       break;
                   case ADD_SECOND_EDGE:
                       if(event.getTarget() instanceof Circle)
                       {
                           ((Circle)event.getTarget()).setFill(GV.nodeSelectedColor);
                           secondNode= nodes.get(event.getTarget());
                           Edge edge = new Edge(firstNode , secondNode , 10);
                           edge.setContainsLine(true);
                           if(graph.addEdge(edge))
                           {
                                centerPane.getChildren().addAll(edge);
                                for (int i = 0 ; i < centerPane.getChildren().size() ; i++)
                                {
                                    if(centerPane.getChildren().get(i) instanceof Edge)
                                    {
                                        centerPane.getChildren().get(i).toBack();
                                    }
                                }
                           }
                           ((Circle)event.getTarget()).setFill(GV.nodeFillColor);
                       }
                       break;
                   case IDLE:
                       resetColors();
                       break;
                   case REMOVE_EDGE:
                       if(event.getTarget() instanceof Edge)
                       {
                            System.out.println(graph);
                            graph.removeEdge((Edge) event.getTarget());
                            centerPane.getChildren().removeAll((Edge)event.getTarget());
                       }
                       break;
                   case REMOVE_VERTEX:
                        centerPane.getChildren().remove(nodes.get(event.getTarget()));
                        centerPane.getChildren().removeAll(graph.removeVertex(nodes.get(event.getTarget())));
                       break;
                   case RESET:
                       System.out.println(graph);
                       break;
               }

        });
    }

}
