/**
 * Author: Adrian Mangion
 * Date: 10/12/2020
 * Module Code: CST3170
 * Description: Object Model - City (used for TSP solution)
 */

import java.util.Comparator;

public class City {

    //#region Class Variables
        int cityNo, x, y;
        Boolean visited = false;
        Boolean isStart;
    //#endregion

    //#region Constructors
        public City(){ }
        public City(int cityNo, int x, int y)
        {
            this.cityNo = cityNo;
            this.x = x;
            this.y = y;
        }
    //#endregion

    //#region Getters
        public int getCityNo()
        {
            return cityNo;
        }

        public int getX()
        {
            return x;
        }

        public int getY()
        {
            return y;
        }

        public Boolean Visited()
        {
            return visited;
        }

        public Boolean IsStart()
        {
            return isStart;
        }
    //#endregion

    //#region Setters
        public void setCityNo(int cityNo)
        {
            this.cityNo = cityNo;
        }
        public void setX(int x)
        {
            this.x = x;
        }
        public void setY(int y)
        {
            this.y = y;
        }
    //#endregion

    //#region Methods
        public void setCityByArray(String[] line)
        {
            cityNo = Integer.parseInt(line[0]);
            x = Integer.parseInt(line[1]);
            y = Integer.parseInt(line[2]);
        }
    //#endregion

    //#region Comparators
    public static Comparator<City> xComparator = new Comparator<City>()
    {
        @Override
        public int compare(City c1, City c2) 
        {
            return Integer.compare(c1.getX(), c2.getX());
        }
    };

    public static Comparator<City> yComparator = new Comparator<City>()
    {
        @Override
        public int compare(City c1, City c2) 
        {
            return Integer.compare(c1.getY(), c2.getY());
        }
    };
    //#endregion
}