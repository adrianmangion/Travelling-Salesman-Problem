
/**
 * Author: Adrian Mangion
 * Date: 10/12/2020
 * Module Code: CST3170
 * Description: Helper Class - Class for system outputs.
 */

import java.io.File;
import java.util.Scanner;

public class Helper {

    // Displays shortest path result
    public static void DisplayResult(File file, Path shortestPath, double timeDiff) 
    {
        System.out.println("\nFile Path: " + file.getPath());
        System.out.println("\nAlgorithm Name: " + shortestPath.algorithmUsed);
        System.out.println("No. of Cities: " +  (shortestPath.cities.size()-1));
        System.out.println("Total Distance: " + shortestPath.totalDistance);
        System.out.println("Total time: " + timeDiff);
        System.out.print("Path: ");

        for(City city : shortestPath.cities)
        {
            System.out.print(" "+city.cityNo);
        }
	}

    // Displays message to run tsp solution on another file.
    public static boolean PrintTryAgainMenu() 
    {
        Scanner sc = new Scanner(System.in);
        int index;
        System.out.println("\nWould you like to try again? Enter index \n1. Yes \n2. No");         
        index = sc.nextInt();
        
        switch(index)
        {
            case 1:
                return true;
            
            case 2:
                return false;
                
            default:
                return true;
        }
	}

}
