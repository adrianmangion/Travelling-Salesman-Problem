/**
 * Author: Adrian Mangion
 * Date: 10/12/2020
 * Module Code: CST3170
 * Description: Object Model - Path (used for TSP solution generation)
 */

import java.util.ArrayList;

public class Path {

    //#region Class Variables
    String algorithmUsed;
    ArrayList<City> cities;
    double totalDistance;
    //#endregion

    public Path()
    {
        
    }

    public Path(ArrayList<Edge> edgesOrganised)
    {
        totalDistance = 0;
        cities = new ArrayList<City>();

        for (Edge edge : edgesOrganised)
        {
            City city = new City();
            city.cityNo = edge.getOriginCityNo();
            totalDistance += edge.getDistance();
            cities.add(city);
        }

        City lastCity = new City();
        lastCity.cityNo = edgesOrganised.get(cities.size()-1).getDestCityNo();
        totalDistance += edgesOrganised.get(cities.size()-1).getDistance();
        cities.add(lastCity);

	}

	public ArrayList<City> getCities()
    {
        return cities;
    }

}

