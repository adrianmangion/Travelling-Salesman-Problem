/**
 * Author: Adrian Mangion
 * Date: 10/12/2020
 * Module Code: CST3170
 * Description: Object Model - Edge (used in CheapestLink algorithm)
 */


import java.util.Comparator;

public class Edge {

    //#region Properties
    private int edgeNo, originCityNo, destCityNo;
    private double distance;
    //#endregion

    /**
     * Constructor
     */
    public Edge()
    {

    }

    public Edge(int _originCityNo, int _destCityNo, double _distance, int size) 
    {
        edgeNo = size;
        distance = _distance;
        originCityNo = _originCityNo;
        destCityNo = _destCityNo;
	}

    //#region getters and setters

	public int getEdgeNo()
    {
        return edgeNo;
    }

    public double getDistance()
    {
        return distance;
    }

    public int getOriginCityNo()
    {
        return originCityNo;
    }

    public int getDestCityNo()
    {
        return destCityNo;
    }

    public void setDistance(double distance)
    {
        this.distance = distance;
    }

    public void setOriginCityNo(int cityNo)
    {
        originCityNo = cityNo;
    }

    public void setDestCityNo(int cityNo)
    {
        destCityNo = cityNo;
    }

    public void setEdgeNo(int noOfEdges) 
    {
        edgeNo = noOfEdges;
	}

    //#endregion

    //#region Comparators
    public static Comparator<Edge> DistanceComparator = new Comparator<Edge>()
    {
        @Override
        public int compare(Edge e1, Edge e2) 
        {
            return Double.compare(e1.getDistance(), e2.getDistance());
        }
    };
    //#endregion
}
