package TSP.algorithms;

import TSP.api.Views;
import TSP.graph.Node;

import java.util.ArrayList;
import java.util.List;

public class TwoOpt {

    public static Views views;


    public static Node[] alternate(Node[] cities) {
        Node[] newTour;
        double bestDist  = GeneticAlgorithm.calculateDistance(cities);
        double newDist;
        int swaps = 1;
        int improve = 0;
        int iterations = 0;
        long comparisons = 0;

        while (swaps != 0) { //loop until no improvements are made.
            swaps = 0;

            //initialise inner/outer loops avoiding adjacent calculations and making use of problem symmetry to half total comparisons.
            for (int i = 1; i < cities.length - 2; i++) {
                for (int j = i + 1; j < cities.length- 1; j++) {
                    comparisons++;
                    //check distance of line A,B + line C,D against A,C + B,D if there is improvement, call swap method.
                    if ((cities[i].distance(cities[i-1]) + cities[j+1].distance(cities[j])) >=
                            (cities[i].distance(cities[j+1]) + cities[i-1].distance(cities[j]))) {

                        newTour = swap(cities, i, j); //pass arrayList and 2 points to be swapped.

                        newDist = GeneticAlgorithm.calculateDistance(newTour);

                        if (newDist < bestDist) { //if the swap results in an improved distance, increment counters and update distance/tour
                            cities = newTour;
                            bestDist = newDist;
                            swaps++;
                            improve++;
                        }
                    }
                }
            }
            iterations++;
        }
        System.out.println("Total comparisons made: " + comparisons);
        System.out.println("Total improvements made: " + improve);
        System.out.println("Total iterations made: " + iterations);
        return cities;
    }

        private static Node[] swap(Node[] cities, int i, int j) {
            //conducts a 2 opt swap by inverting the order of the points between i and j
            List<Node> newTour = new ArrayList<>();

            //take array up to first point i and add to newTour
            int size = cities.length;
            for (int c = 0; c <= i - 1; c++) {
                newTour.add(cities[c]);
            }

            //invert order between 2 passed points i and j and add to newTour
            int dec = 0;
            for (int c = i; c <= j; c++) {
                newTour.add(cities[j - dec]);
                dec++;
            }

            //append array from point j to end to newTour
            for (int c = j + 1; c < size; c++) {
                newTour.add(cities[c]);
            }

            return newTour.stream().toArray(Node[]::new);
        }
}
