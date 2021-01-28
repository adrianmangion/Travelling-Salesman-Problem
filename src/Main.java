/**
 * Author: Adrian Mangion
 * Date: 10/12/2020
 * Module Code: CST3170
 * Description: Main Class for Coursework 1
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Main{
    public static void main(String[] args){
        try{
            // Variables
            String fileLine;
            int dataSetIndex;
            long start, end;
            Path shortestPath;
            double timeDiff;
            boolean restart = true;
            boolean duplicate = false;
            String path = "./Test Data";

            // Initialize arraylist of cities which will be used to store all cities in file.
            ArrayList<City> cities = new ArrayList<City>();

            // Create File object based provided path.
            File directory = new File(path);

            // Get array of files in directory.
            File[] contentsOfDirectory = directory.listFiles();

            // Iniatlize scanner object.
            Scanner sc = new Scanner(System.in);

            System.out.println("The travelling sales man problem...");

            while(restart){
                System.out.println("Choose data set folder:");

                for(int i = 0; i < contentsOfDirectory.length; i++)
                {
                    // List folder names. (+ 1 is for menu to start from 1.)
                    System.out.println(i+1+". "+contentsOfDirectory[i].getName());
                }

                dataSetIndex = sc.nextInt()-1; // -1 is to get actual number of file chosen.

                // Retrieve dataset folder and files through the index.
                File dataset = new File(contentsOfDirectory[dataSetIndex].toPath().toString());
                File[] dataSetFiles = dataset.listFiles();

                for(int i = 1; i <= dataSetFiles.length; i++)
                {
                    System.out.println(i +". " + dataSetFiles[i-1].getName());
                }

                System.out.println("Choose index.");
                int userInput = sc.nextInt()-1;

                File file = dataSetFiles[userInput];
                
                // Open streams to read data from file.
                InputStream inputStream = new FileInputStream(file);
                InputStreamReader iStreamReader = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(iStreamReader);

                // Get all lines in textfile and parse to cities.
                while ((fileLine = br.readLine()) != null)
                {
                    fileLine = fileLine.trim();
                    
                    if(fileLine.length() > 0)
                    {
                        String[] cityAttributes = fileLine.split("\\s+");

                        City city = new City();
                        city.setCityByArray(cityAttributes);

                        // Check for duplicates
                        for(City c : cities)
                        {
                            if(city.cityNo == c.cityNo)
                            {
                                duplicate = true;
                            }
                        }

                        // Add only if not duplicate.
                        if(!duplicate)
                        {
                            cities.add(city);
                        }
                    }
                }

                // Close readers and scanner to release resources.
                br.close();
                iStreamReader.close();
                inputStream.close();

                for(int i = 1; i <= 3; i++)
                {
                    userInput = i;

                    switch(userInput)
                    { 
                        // Nearest Neighbor Algorithm
                        case 1:

                        start = System.nanoTime();
                        shortestPath = Algorithm.RunNearestNeighbour(cities, 0);
                        end = System.nanoTime();
                        shortestPath.algorithmUsed = "Nearest Neighbor";
                        timeDiff = (double)(end - start) / 1000000000;
                        Helper.DisplayResult(file, shortestPath, timeDiff);
                        break;

                        // Repetetive Nearest Neighbor Algorithm
                        case 2:
                        
                        start = System.nanoTime();
                        shortestPath = Algorithm.RunNearestNeighbour(cities, 1);
                        end = System.nanoTime();
                        shortestPath.algorithmUsed = "Repetetive Nearest Neighbor";
                        timeDiff = (double)(end - start) / 1000000000;
                        Helper.DisplayResult(file, shortestPath, timeDiff);
                        break;
                        
                        // Cheapest Link Algorithm
                        case 3:

                        start = System.nanoTime();
                        shortestPath = Algorithm.RunCheapestLink(cities);
                        end = System.nanoTime();
                        shortestPath.algorithmUsed = "Cheapest Link Algorithm";
                        timeDiff = (double)(end - start) / 1000000000;
                        Helper.DisplayResult(file, shortestPath, timeDiff);
                        break;
                    }
            }

            // Check if user wants to solve another dataset
            restart = Helper.PrintTryAgainMenu();

    }

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}