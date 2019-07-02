package TSP.api;

import TSP.GV;
import TSP.Main;
import TSP.graph.Edge;
import TSP.graph.Graph;
import TSP.graph.Node;
import TSP.graph.Vector2D;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Views  {

    Parent root;
    HBox toolbar;
    Pane centerPane;
    BorderPane borderPane;
    private State state;
    private Button btnIdle;
    private Button btnAddNode;
    private Button btnRemoveNode;
    private Button btnAddEdge;
    private Button btnRemoveEdge;
    private Button btnReset;
    private Button btnTSP;
    private Button btnShowGraph;
    Graph graph = new Graph();
    private Stage showGraphSage;
    private Optional<Node> firstNode;
    private Optional<Node> secondNode ;
    private static double edgeWeight = -1;
    private static AtomicInteger label = new AtomicInteger(0);
    private Thread mousePositionListener = new Thread(() ->
       centerPane.addEventHandler(MouseEvent.ANY , event -> GV.mousePosition.set(event.getX() , event.getY())));

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
            setColors(btnIdle);
        });

        //btn add vertex setup
        btnAddNode = new Button("add node");
        btnAddNode.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            this.state = State.ADD_NODE;
            setColors(btnAddNode);
        });

        //btn Remove vertex setup
        btnRemoveNode = new Button("remove node");
        btnRemoveNode.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            this.state = State.REMOVE_NODE;
            setColors(btnRemoveNode);
        });

        //btn add Edge setup
        btnAddEdge = new Button("add edge");
        btnAddEdge.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            this.state = State.ADD_EDGE;
            setColors(btnAddEdge);
        });

        //btn Remove Edge setup
        btnRemoveEdge = new Button("remove edge");
        btnRemoveEdge.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            this.state = State.REMOVE_EDGE;
            setColors(btnRemoveEdge);
        });

        btnTSP = new Button("run TSP");
        btnTSP.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            setColors(btnTSP);
        });

        //btn Reset setup
        btnReset = new Button("reset");
        btnReset.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            graph = new Graph();
            centerPane.getChildren().clear();
            label = new AtomicInteger(0);
            setColors(btnReset);
        });

        btnShowGraph = new Button("show graph");
        btnShowGraph.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            showGraph();
            setColors(btnShowGraph);
        });
        setColors(btnIdle);
        toolbar.getChildren().addAll(btnIdle, btnAddNode, btnRemoveNode,btnAddEdge,btnRemoveEdge,btnTSP,btnReset , btnShowGraph);
    }


    private void setColors(Button current)
    {
        resetColors();
        btnIdle.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnAddNode.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnRemoveNode.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnAddEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnRemoveEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnTSP.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnReset.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnShowGraph.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        current.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.backgroundColor);
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
               case ADD_NODE:
                   Node node = new Node(label.getAndIncrement() + "" , new Vector2D(event.getX() , event.getY()));
                   graph.addNode(node);
                   node.addEventHandler(MouseEvent.MOUSE_DRAGGED,event1 -> graph.moveNode(node));
                   centerPane.getChildren().addAll(node);
                   break;
               case ADD_EDGE:
                   resetColors();
                   firstNode = Optional.empty();
                   if (event.getTarget() instanceof Circle)
                       firstNode = Optional.ofNullable((Node)((Circle) event.getTarget()).getParent());
                   if (event.getTarget() instanceof Text)
                       firstNode = Optional.ofNullable((Node)((Text) event.getTarget()).getParent());
                   if(!firstNode.isPresent())
                       return;
                   firstNode.get().getCircle().setFill(GV.nodeSelectedColor);
                   state = State.ADD_EDGE_2;
                   break;
               case ADD_EDGE_2:
                   secondNode = Optional.empty();
                   if (event.getTarget() instanceof Circle)
                       secondNode = Optional.ofNullable((Node)((Circle) event.getTarget()).getParent());
                   if (event.getTarget() instanceof Text)
                       secondNode = Optional.ofNullable((Node)((Text) event.getTarget()).getParent());
                   if(!secondNode.isPresent())
                       return;
                   secondNode.get().getCircle().setFill(GV.nodeSelectedColor);
                   edgeWeight = firstNode.get().distance(secondNode.get());
                   Edge edge = new Edge(firstNode.get() , secondNode.get() ,edgeWeight );
                   if(graph.addEdge(edge))
                       toBack(edge);
                   break;
               case IDLE:
                   resetColors();
                   break;
               case REMOVE_EDGE:
                   if(!(event.getTarget() instanceof Edge))
                       return;
                   graph.removeEdge((Edge) event.getTarget());
                   centerPane.getChildren().removeAll(((Edge) event.getTarget()).getWeightLabel() ,(Edge)event.getTarget());
                   break;
               case REMOVE_NODE:
                   Optional<Node> node1 = Optional.empty();
                   if (event.getTarget() instanceof Circle)
                       node1 = Optional.ofNullable((Node)((Circle) event.getTarget()).getParent());
                   if (event.getTarget() instanceof Text)
                       node1= Optional.ofNullable((Node)((Text) event.getTarget()).getParent());
                   if(!node1.isPresent())
                       return;
                   centerPane.getChildren().remove(node1.get());
                   List<Edge> edges = graph.removeNode(node1.get());
                   for (Edge e : edges)
                       centerPane.getChildren().removeAll(e.getWeightLabel());
                   centerPane.getChildren().removeAll(edges);
                   break;
           }
        });
    }


    private void toBack(Edge edge){
        centerPane.getChildren().addAll(edge);
        for (int i = 0 ; i < centerPane.getChildren().size() ; i++)
        {
            if(centerPane.getChildren().get(i) instanceof Edge)
            {
                centerPane.getChildren().get(i).toBack();
            }
        }
    }



}
