package TSP.api;

import TSP.GV;
import TSP.Main;
import TSP.algorithms.GeneticAlgorithm;
import TSP.algorithms.Lexicographical;
import TSP.graph.Edge;
import TSP.graph.Graph;
import TSP.graph.Node;
import TSP.graph.Vector2D;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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
    private Button btnLexicographical;
    private Button btnShowGraph;
    private Button btnGA;
    private CheckBox chkShowWeights;
    private Graph graph = new Graph();
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

        //btn Lexicographical setup
        btnLexicographical = new Button("Lexicographical");
        btnLexicographical.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            setColors(btnLexicographical);
            this.state = State.LEXICOGRAPH;
            new Thread(() -> {
                //create a copy of existing nodes
                Node[] order = graph.getAdjKeySet().stream().toArray(Node[]::new);

                //calculate the distance based on the current order of the route
                double bestEver = Lexicographical.calculateDistance(order);

                //set the first order to be the best route
                Lexicographical.bestRoute = order.clone();

                //create a group to keep track of the best route and group the nodes and edged together
                Lexicographical.getBestRoute();

                //while there is an order to show
                while (Lexicographical.order(order) && this.state == State.LEXICOGRAPH)
                {
                    // calculate the current distance based on the order
                    double currentDistance = Lexicographical.calculateDistance(order);

                    //if this is a better route save it and continue
                    if(currentDistance < bestEver)
                    {
                        Lexicographical.bestRoute = order.clone();
                        bestEver = currentDistance;
                        Lexicographical.getBestRoute();
                    }

                    //check and show each possible route
                    Platform.runLater(() -> {
                        centerPane.getChildren().clear();
                        graph.clearEdges();
                        centerPane.getChildren().addAll(Lexicographical.bestRouteGroup);
                        Lexicographical.bestRouteGroup.toBack();
                        for (int i = 0; i < order.length - 1; i++) {
                            Edge edge = new Edge(order[i] , order[i+1] , order[i].distance(order[i+1]));
                            if(graph.addEdge(edge))
                                toBack(edge);
                        }
                    });

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //show only the best route
                Platform.runLater(() -> {
                    centerPane.getChildren().clear();
                    centerPane.getChildren().addAll(Lexicographical.bestRouteGroup);
                });

                graph.printNode(Lexicographical.bestRoute);
                System.out.println(bestEver);

            }).start();
        });


        //btn GA setup
        btnGA = new Button("Genetic Algorithm");
        btnGA.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            setColors(btnGA);
            this.state = State.GA;
            new Thread(() -> {
                //create a copy of existing nodes
                Node[] cities = graph.getAdjKeySet().stream().toArray(Node[]::new);
                GeneticAlgorithm ga = new GeneticAlgorithm(cities , 500);

                while (this.state.equals(State.GA))
                {
                    Platform.runLater(() -> {
                        centerPane.getChildren().clear();
                        centerPane.getChildren().addAll(ga.getBestRoute());
                    });
                    System.out.println(ga.bestDistance);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ga.nextGeneration();
                    ga.calculateFitness();
                    ga.normalizeFitness();

                }
            }).start();

        });


        //btn Reset setup
        btnReset = new Button("reset");
        btnReset.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            graph = new Graph();
            this.state = State.IDLE;
            centerPane.getChildren().clear();
            label = new AtomicInteger(0);
            setColors(btnIdle);
        });

        btnShowGraph = new Button("show graph");
        btnShowGraph.addEventHandler(MouseEvent.MOUSE_CLICKED , event -> {
            showGraph();
            setColors(btnShowGraph);
        });


        chkShowWeights = new CheckBox("show weights");
        chkShowWeights.setSelected(true);
        chkShowWeights.setOnAction((event) -> {
            System.out.println("he");
            if(chkShowWeights.isSelected())
                GV.showWeight = true;
            else
                GV.showWeight = false;

            bestRouteEdgeWeightToggle(this.state , GV.showWeight);
        });

        setColors(btnIdle);
        toolbar.getChildren().addAll(btnIdle, btnAddNode, btnRemoveNode,btnAddEdge,btnRemoveEdge,btnReset , btnShowGraph , btnLexicographical , btnGA , chkShowWeights);
    }


    public void updateView(Node[] order)
    {

        Platform.runLater(() -> centerPane.getChildren().clear());
        Group group = new Group();
        for (int i = 0; i < order.length ; i++) {
            if(i == order.length-1)
            {
                Edge edge = new Edge(order[0] , order[i] , order[0].distance(order[i]));
                edge.getLine().setStroke(Color.BLACK);
                edge.getLine().setStrokeWidth(3);
                Node node = order[i];
                Platform.runLater(() -> group.getChildren().addAll(edge , node));
                edge.toBack();
            }
            else {
                Edge edge = new Edge(order[i] , order[i+1] , order[i].distance(order[i+1]));
                edge.getLine().setStroke(Color.RED);
                edge.getLine().setStrokeWidth(1);
                Node node = order[i];
                Platform.runLater(() -> group.getChildren().addAll(edge , node));
            }
        }
        Platform.runLater(() -> centerPane.getChildren().addAll(group));

    }



    private void bestRouteEdgeWeightToggle(State state , boolean show){
        switch (state)
        {
            case LEXICOGRAPH:
                for (int i = 0; i < Lexicographical.bestRouteGroup.getChildren().size(); i++){
                    if(Lexicographical.bestRouteGroup.getChildren().get(i) instanceof Edge)
                        ((Edge) Lexicographical.bestRouteGroup.getChildren().get(i)).getWeightLabel().setVisible(show);
                }
                break;
        }

    }

    private void setColors(Button current)
    {
        resetColors();
        btnIdle.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnAddNode.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnRemoveNode.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnAddEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnRemoveEdge.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnLexicographical.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnReset.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnShowGraph.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
        btnGA.setStyle("-fx-background-radius: 0;-fx-background-color: "+GV.toolbarColor);
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
                   node.addEventHandler(MouseEvent.MOUSE_DRAGGED , event1 -> graph.moveNode(node));
                   centerPane.getChildren().add(node);
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
                   {
                       toBack(edge);
                   }
                   break;
               case IDLE:
                   resetColors();
                   break;
               case REMOVE_EDGE:

                   if(!(event.getTarget() instanceof Line))
                       return;
                   Edge edge1 = (Edge)((Line)event.getTarget()).getParent();
                   graph.removeEdge(edge1);
                   centerPane.getChildren().removeAll(edge1);
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
                   Optional<List<Edge>> edges = graph.removeNode(node1.get());
                   if(!edges.isPresent())
                       return;
                   for (Edge e : edges.get())
                       centerPane.getChildren().removeAll(e.getWeightLabel());
                   centerPane.getChildren().removeAll(edges.get());
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
