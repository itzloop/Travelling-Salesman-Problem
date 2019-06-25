package TSP.api;

import TSP.GV;
import TSP.Main;
import TSP.graph.Edge;
import TSP.graph.Graph;
import TSP.graph.Node;
import TSP.graph.Vector2D;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Views  {

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
    private Button btnTSP;
    Graph graph = new Graph();
    private Stage showGraphSage;
    private Node firstNode;
    private Node secondNode;
    private static double edgeWeight = -1;
    private static AtomicInteger label = new AtomicInteger(0);
    private Thread mousePositionListener = new Thread(() ->
       centerPane.addEventHandler(MouseEvent.ANY , event -> {
           GV.mousePosition.set(event.getX() , event.getY());
       }));



    public Views()
    {
        Platform.setImplicitExit(false);
        this.state = State.IDLE;
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
        Main.stage.widthProperty().addListener((observableValue, number, t1) -> borderPane.setPrefWidth(t1.doubleValue()));
        Main.stage.heightProperty().addListener((observableValue, number, t1) -> borderPane.setPrefHeight(t1.doubleValue()));
        showGraph();
    }


    private void setToolbar() {
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
            btnTSP.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            
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
            btnTSP.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            
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
            btnTSP.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            
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
            btnTSP.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            
            btnReset.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        });

        //btn add Edge setup
        btnAddEdge = new Button("add edge");
        btnAddEdge.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            this.state = State.ADD_EDGE;
            resetColors();
            btnIdle.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnAddVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnMoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnRemoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnAddEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.backgroundColor);
            btnRemoveEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnTSP.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            
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
            btnTSP.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            
            btnReset.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        });

        btnTSP = new Button("run TSP");
        btnTSP.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            btnIdle.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnAddVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnMoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnRemoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnAddEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnRemoveEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnTSP.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.backgroundColor);
            
            btnReset.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        });



        //btn Reset setup
        btnReset = new Button("reset");
        btnReset.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            graph = new Graph();
            centerPane.getChildren().clear();
            label = new AtomicInteger(0);
            btnIdle.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.backgroundColor);
            btnAddVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnMoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnRemoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnAddEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnRemoveEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnTSP.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            
            btnReset.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
            btnIdle.fire();
        });

        btnIdle.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.backgroundColor);
        btnAddVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnMoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnRemoveVertex.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnAddEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnRemoveEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnTSP.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        
        btnReset.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);

        toolbar.getChildren().addAll(btnIdle,btnAddVertex,btnMoveVertex,btnRemoveVertex,btnAddEdge,btnRemoveEdge,btnTSP,btnReset);
    }

    private void showGraph() {
        if(showGraphSage != null)
            return;
        showGraphSage = new Stage();
        Text label = new Text(graph.toString());
        ScrollPane pane = new ScrollPane();
        Scene scene = new Scene(pane , 300,700);
        label.wrappingWidthProperty().bind(scene.widthProperty());
        pane.setContent(label);
        pane.setFitToWidth(true);
        pane.setFitToHeight(true);
        showGraphSage.setScene(scene);
        showGraphSage.setX(Screen.getPrimary().getBounds().getWidth()/2 - 700);
        showGraphSage.setY(Screen.getPrimary().getBounds().getHeight()/2 - 400);
        showGraphSage.setOnCloseRequest(windowEvent -> {
            showGraphSage.close();
            showGraphSage = null;
        });
        new Thread(() -> {
            while (showGraphSage != null)
            {
                Platform.runLater(() -> label.setText(graph.toString()));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        showGraphSage.show();
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

            switch (state)
               {
                   case ADD_VERTEX:
                       addVertex(event);
                       break;
                   case ADD_EDGE:
                       resetColors();
                       addEdge(event);
                       break;
                   case ADD_EDGE_2:
                       addEdge2(event);
                       break;
                   case IDLE:
                       resetColors();
                       break;
                   case REMOVE_EDGE:
                       removeEdge(event);
                       break;
                   case REMOVE_VERTEX:
                       removeVertex(event);
                       break;
               }
        });
    }

    private void addVertex(MouseEvent event) {
        //create the node
        Node node = new Node(label.getAndIncrement() + "" , new Vector2D(event.getX() , event.getY()));
        //add the node to the graph
        graph.addVertex(node);

        //add an event for node movement
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED,event1 -> {
            switch (state)
            {
                case MOVE:
                    Interfaces.moveNode.move(node);
                    //move all the edges connected to this node
                    graph.edges(node).forEach(e -> {
                        Interfaces.moveEdge.move(e,graph.getEdge(e.getTo() , e.getFrom()));
                    });
                    break;
            }

        });
        centerPane.getChildren().addAll(node);
    }

    private void removeEdge(MouseEvent event) {
        if(event.getTarget() instanceof Edge)
        {
            System.out.println(graph);
            graph.removeEdge((Edge) event.getTarget());
            centerPane.getChildren().removeAll(((Edge) event.getTarget()).getWeightLabel() ,(Edge)event.getTarget());
        }
    }

    private void removeVertex(MouseEvent event) {
        if(event.getTarget() instanceof Circle)
        {
            centerPane.getChildren().remove(((Circle)event.getTarget()).getParent());
            List<Edge> edges = graph.removeVertex((Node)((Circle)event.getTarget()).getParent());
            for (Edge e : edges)
            {
                centerPane.getChildren().removeAll(e.getWeightLabel());
            }
            centerPane.getChildren().removeAll(edges);
        }
        else if(event.getTarget()instanceof Text){
            centerPane.getChildren().remove(((Text)event.getTarget()).getParent());
            List<Edge> edges = graph.removeVertex((Node)((Text)event.getTarget()).getParent());
            for (Edge e : edges)
            {
                centerPane.getChildren().removeAll(e.getWeightLabel());
            }
            centerPane.getChildren().removeAll(edges);
        }
    }

    private void addEdge(MouseEvent event) {
        if(event.getTarget() instanceof Circle)
        {
            firstNode = (Node)((Circle)event.getTarget()).getParent();
            ((Node)((Circle)event.getTarget()).getParent()).getCircle().setFill(Color.rgb(209, 57, 61));
            state = State.ADD_EDGE_2;
        }else if(event.getTarget() instanceof Text){
            firstNode = (Node)((Text)event.getTarget()).getParent();
            ((Node)((Text)event.getTarget()).getParent()).getCircle().setFill(Color.rgb(209, 57, 61));
            state = State.ADD_EDGE_2;
        }
    }

    private void addEdge2(Event event) {
        if(event.getTarget() instanceof Circle)
        {
            ((Node)((Circle)event.getTarget()).getParent()).getCircle().setFill(GV.nodeSelectedColor);
            secondNode= (Node)((Circle)event.getTarget()).getParent();
            edgeWeight = Node.distance(firstNode,secondNode);
            Edge edge = new Edge(firstNode , secondNode , edgeWeight);
            edge.setContainsLine(true);
            if(graph.addEdge(edge))
            {
                centerPane.getChildren().addAll(edge , edge.getWeightLabel());
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
        else if ( event.getTarget() instanceof Text)
        {
            ((Node)((Text)event.getTarget()).getParent()).getCircle().setFill(GV.nodeSelectedColor);
            secondNode= (Node)((Text)event.getTarget()).getParent();
            edgeWeight = Node.distance(firstNode,secondNode);
            Edge edge = new Edge(firstNode , secondNode , edgeWeight);
            edge.setContainsLine(true);
            if(graph.addEdge(edge))
            {
                centerPane.getChildren().addAll(edge , edge.getWeightLabel());
                for (int i = 0 ; i < centerPane.getChildren().size() ; i++)
                {
                    if(centerPane.getChildren().get(i) instanceof Edge)
                    {
                        centerPane.getChildren().get(i).toBack();
                    }
                }
            }
            ((Node)((Text)event.getTarget()).getParent()).getCircle().setFill(GV.nodeFillColor);
        }
    }

}
