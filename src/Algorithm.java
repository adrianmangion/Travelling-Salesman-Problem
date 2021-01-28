/**
 * Author: Adrian Mangion
 * Date: 10/12/2020
 * Module Code: CST3170
 * Description: Holds algorithms for TSP.
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Algorithm {
    public Algorithm()
    {

    }

    //#region Public Methods

    /**
     * Finds shortest path using nearest neighbour algorithm.
     * @param cities
     * @return
     */
    public static Path RunNearestNeighbour(ArrayList<City> cities, int repetetive)
    {
        Path shortestPath = new Path();
        ArrayList<City> visitedCities;
        double totalDistance = 0;
        Random random;
        int upperbound;
        int randomCityNo;

        switch(repetetive)
        {
            case 0:

            random = new Random();
            upperbound = cities.size();
            randomCityNo = random.nextInt(upperbound);

            visitedCities = new ArrayList<City>();
            City startC = cities.get(randomCityNo);
            startC.isStart = true;
            startC.visited = true;
            visitedCities.add(startC);
            shortestPath = FindNearestNode(cities.get(randomCityNo), cities, visitedCities, totalDistance);

            break;

            case 1:

            // Iterate between all possible starting cities.
            for(City startCity : cities)
            {
                ResetCities(cities);
                startCity.isStart = true;
                startCity.visited = true;

                visitedCities = new ArrayList<City>();
                visitedCities.add(startCity);
                Path path = new Path();
                path = FindNearestNode(startCity, cities, visitedCities, totalDistance);

                if(shortestPath.totalDistance == 0 || path.totalDistance <= shortestPath.totalDistance)
                {
                    shortestPath = path;
                }
            }
                
            break;
        }

        return shortestPath;
    }

    /**
     * Finds the shortest path by using the cheapest link algorithm.
     * @param cities
     * @return
     */
    public static Path RunCheapestLink(ArrayList<City> cities) 
    {
        Path shortestPath = new Path();
        
        ArrayList<Edge> edges = new ArrayList<Edge>();
        ArrayList<Edge> edgesUsed = new ArrayList<Edge>();
        int noOfEdges = 0;

        // Get all edges
        for(int i = 0; i < cities.size(); i++)
        {
            for(int j = 0; j < cities.size(); j++)
            {
                if(i != j)
                {
                    Edge edge = new Edge();
                    edge.setOriginCityNo(cities.get(i).cityNo);
                    edge.setDestCityNo(cities.get(j).cityNo);
                    edge.setEdgeNo(noOfEdges);
                    edge.setDistance(CalculateDistance(cities.get(i), cities.get(j)));
                    edges.add(edge);
                    noOfEdges++;
                }
            }
        }

        // Sort the erdges by smallest to largest distance values.
        Collections.sort(edges, Edge.DistanceComparator);

        shortestPath = FindBestPath(edges, edgesUsed, cities);

        return shortestPath;
    }

    //#endregion

    //#region Private Methods

    /**
     * Finds the best path via cheapest link algorithm
     * 
     * @param edges List of all edges.
     * @param edgesUsed List of edges used in path.
     * @param cities List of cities.
     * @return shortestPath
     */
    private static Path FindBestPath(ArrayList<Edge> edges, ArrayList<Edge> edgesUsed, ArrayList<City> cities) 
    {
        Path bestPath = new Path();
        int indexToAdd = -1;
        int loopCounter = 0;
        boolean hitAtLeastOnce = false;
        boolean circuitComplete = false;

        // Get the shortest edge and use the first one as the starting point.
        edgesUsed.add(edges.get(0));

        while(!circuitComplete)
        {
            // Loop through all edges (which are ordered by distance)
            outerloop: for(Edge e1 : edges)
            {
                // If origin city of edge matches destination city of previous edge store index.
                if(e1.getOriginCityNo() == edgesUsed.get(edgesUsed.size()-1).getDestCityNo() || e1.getDestCityNo() == edgesUsed.get(0).getOriginCityNo())
                {
                    // Loop through all edges used and checks if the current edge completes a circuit.
                    for(Edge e2 : edgesUsed)
                    {   
                        if(edgesUsed.size() != cities.size()-1)
                        {
                            // Check that the destination of the found edge (e1) is not an origin in the current edgesUsed list unless it is the last edge.
                            if(e1.getDestCityNo() != e2.getOriginCityNo())
                            {
                                indexToAdd = loopCounter;
                            }
                            // Premature circuit
                            else if(e1.getDestCityNo() == e2.getOriginCityNo())
                            {
                                hitAtLeastOnce = true;
                            }
                        }
                        else
                        {
                            // Finished circuit with all cities.
                            if(e1.getDestCityNo() == edgesUsed.get(0).getOriginCityNo() && e1.getOriginCityNo() != edgesUsed.get(0).getDestCityNo())
                            {
                                edgesUsed.add(e1);
                                break outerloop;
                            }
                        }
                    }
                }

                if(indexToAdd != -1 && !hitAtLeastOnce)
                {
                    edgesUsed.add(edges.get(indexToAdd));
                }
                    loopCounter++;
                    indexToAdd = -1;
                    hitAtLeastOnce = false;
            }

            if(cities.size() == edgesUsed.size())
            {
                bestPath = new Path(edgesUsed);
                circuitComplete = true;
            }
            else
            {
                loopCounter = 0;
            }
        }
        return bestPath;
    }

    /**
     * Find nearest city neighbour and does so recursively for each city until all
     * cities are visited.
     * 
     * @param startCity
     * @param cities
     * @param visitedCities
     * @param totalDistance
     * @return
     */
    private static Path FindNearestNode(City startCity, ArrayList<City> cities, ArrayList<City> visitedCities, double totalDistance) 
    {
        double shortestDistance = 0;
        City nextCity = new City();
        Path shortestPath = new Path();

        for(City city : cities)
        {
            // Calculate distance to each city.
            double distance = CalculateDistance(startCity, city);

            // Find shortest distance
            // Nearest neighbor is to be unvisted.
            if((shortestDistance == 0 || distance < shortestDistance) && !city.visited)
            {
                shortestDistance = distance;
                nextCity = city;
            }
        }
     
        totalDistance += shortestDistance;
        nextCity.visited = true;
        visitedCities.add(nextCity);

        // If there are any unvisited cities, recursively call this method.
        if(visitedCities.size() < cities.size())
        {
            shortestPath = FindNearestNode(nextCity, cities, visitedCities, totalDistance);
        }

        if(visitedCities.size() == cities.size())
        {
            double distanceBetweenLastAndFirst = CalculateDistance(visitedCities.get(visitedCities.size()-1), visitedCities.get(0));
            totalDistance += distanceBetweenLastAndFirst;
            visitedCities.add(visitedCities.get(0));
            shortestPath.cities = visitedCities;
            shortestPath.totalDistance = totalDistance;
            return shortestPath;
        }

        
        return shortestPath;
    }
    
    /**
     * Calculates disance between two cities
     * using  Euclidean  Standard  Distance.
     */
    private static double CalculateDistance(City city, City startCity) 
    {
        double distance;

        distance = Math.sqrt( Math.pow(city.x - startCity.x, 2) + Math.pow(city.y - startCity.y, 2) );

        return distance;
    }

    /**
     * Resets cities such that all cities are not visited.
     */
    private static void ResetCities(ArrayList<City> cities) 
    {
        for(City city : cities)
        {
            city.visited = false;
        }
    }

    //#endregion
}
