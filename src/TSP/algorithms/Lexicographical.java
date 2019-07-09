package TSP.algorithms;

import TSP.graph.Edge;
import TSP.graph.Node;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Lexicographical {


    public static Node[] bestRoute;
    public static Group bestRouteGroup = new Group();

    public static void getBestRoute()
    {
        Platform.runLater(() -> bestRouteGroup.getChildren().clear());
        for (int i = 0; i < bestRoute.length ; i++) {
            if(i == bestRoute.length-1)
            {
                Edge edge = new Edge(bestRoute[0] , bestRoute[i] , bestRoute[0].distance(bestRoute[i]));
                edge.getLine().setStroke(Color.RED);
                edge.getLine().setStrokeWidth(1);
                Node node = bestRoute[i];
                Platform.runLater(() -> bestRouteGroup.getChildren().addAll(edge , node));
                edge.toBack();
            }
            else {
                Edge edge = new Edge(bestRoute[i] , bestRoute[i+1] , bestRoute[i].distance(bestRoute[i+1]));
                edge.getLine().setStroke(Color.RED);
                edge.getLine().setStrokeWidth(1);
                Node node = bestRoute[i];
                Platform.runLater(() -> bestRouteGroup.getChildren().addAll(edge , node));
            }
        }
    }

    //give a lexico Graphical order
    public static boolean order(Node[] nodes)
    {
        if(nodes == null || nodes.length == 0 )
            return false;

        int largestI = -1, largestJ = -1;

        //find the largest i where nodes[i-1] < nodes[i]
        for (int i = 0; i < nodes.length - 1; i++) {
            if(nodes[i].getIndex() < nodes[i+1].getIndex())
            {
                largestI = i;
            }
        }
        if(largestI == -1)
        {
            System.out.println("finished");
            return false;
        }


        //find the largest j where nodes[largesI] < nodes[j]
        for (int j = 0; j < nodes.length; j++) {
            if(nodes[largestI].getIndex() < nodes[j].getIndex())
            {
                largestJ = j;
            }
        }
        //swap the node at position largestI and largestJ
        swap(nodes , largestI , largestJ);

        //reverse order[largestI + 1 ... end]
        Node[] temp = new Node[nodes.length - (largestI+1)];
        for (int i = largestI+1 , j = 0; i < nodes.length ; i++ , j++) {
            temp[j] = nodes[i];
        }
        for (int i = largestI+1 , j = temp.length - 1; i < nodes.length ; i++ , j--) {
            nodes[i] = temp[j];
        }
        return true;

    }

    private static void swap(Node[] nodes , int i , int j)
    {
        Node temp = nodes[i];
        nodes[i] = nodes[j];
        nodes[j] = temp;
    }


    public static double calculateDistance(Node[] order)
    {
        double distance = 0;
        for (int i = 0; i < order.length - 1; i++) {
            distance += order[i].distance(order[i+1]);
        }
        distance += order[order.length-1].distance(order[0]);
        return distance;
    }

}
