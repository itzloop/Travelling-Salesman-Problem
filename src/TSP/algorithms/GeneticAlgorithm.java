package TSP.algorithms;

import TSP.graph.Edge;
import TSP.graph.Node;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm {
    public Node[] cities;
    public Node[] bestRoute;
    public final int populationSize;
    public List<Node[]> population = new ArrayList<>();
    public double[] fitness;
    public double bestDistance;
    public Group bestRouteGroup;



    public GeneticAlgorithm(Node[] cities , int populationSize)
    {
        this.cities =cities;
        this.bestRoute = new Node[cities.length];
        this.populationSize = populationSize;
        population = new ArrayList<>();
        fitness = new double[populationSize];
        bestDistance = Double.MAX_VALUE;
        bestRouteGroup = new Group();
        generatePop(cities);
    }
    public void generatePop(Node[] cities){
        for (int i = 0; i < populationSize; i++) {
            population.add(shuffle(cities));
        }
        calculateFitness();
        normalizeFitness();
    }

    public Group getBestRoute()
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
        return bestRouteGroup;
    }





    public List<Node[]> nextGeneration()
    {
        List<Node[]> newPopulation = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            Node[] order = mutate(pickOne(population , fitness) , 0.5);
            newPopulation.add(order);
        }
        population = newPopulation;
        return population;
    }

    private Node[]  mutate(Node[] order , double mutationRate) {
        Node[] temp = TwoOpt.alternate(order);
        while (Math.random() < mutationRate)
        {
            int indexA = (int)(Math.random()*order.length);
            int indexB = (int)(Math.random()*order.length);
            swap(temp , indexA , indexB);
        }
        return temp;
    }

    private  Node[] pickOne(List<Node[]> list , double[] prob)
    {
        int index = 0;
        double random = Math.random();
        while (random > 0)
        {
            random -= prob[index];
            index++;
        }
        index--;
        return list.get(index).clone();
    }


    public  void calculateFitness()
    {
        for (int i = 0; i < populationSize; i++) {
            double distance = calculateDistance(population.get(i));
            if(distance < bestDistance)
            {
                bestDistance = distance;
                bestRoute = population.get(i);
            }
            fitness[i] =1 / (distance + 1);
        }

    }
    public void normalizeFitness()
    {
        double sum = 0;
        for (double fit : fitness)
            sum+=fit;
        for (int i = 0 ; i < fitness.length ; i++)
        {
            fitness[i] /= sum;
        }

    }


    private  Node[] shuffle(Node[] cities)
    {
        Node[] order = cities.clone();
        for (int i = 0; i < order.length; i++) {
            int indexI = (int)(Math.random()*order.length);
            int indexJ = (int)(Math.random()*order.length);
            swap(order , indexI , indexJ);
        }
        return order;
    }


    private void swap(Node[] nodes , int i , int j)
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
