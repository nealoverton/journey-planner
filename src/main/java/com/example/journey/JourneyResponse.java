package com.example.journey;

public class JourneyResponse {
    private String roadVehicleName;
    private double roadCost;
    private String outboundAirRoute;
    private double outboundAirCost;
    private String inboundAirRoute;
    private double inboundAirCost;
    private double totalCost;

    public String getRoadVehicleName() {
        return roadVehicleName;
    }

    public void setRoadVehicleName(String roadVehicleName) {
        this.roadVehicleName = roadVehicleName;
    }

    public double getRoadCost() {
        return roadCost;
    }

    public void setRoadCost(double roadCost) {
        this.roadCost = roadCost;
    }

    public String getOutboundAirRoute() {
        return outboundAirRoute;
    }

    public void setOutboundAirRoute(String outboundAirRoute) {
        this.outboundAirRoute = outboundAirRoute;
    }

    public double getOutboundAirCost() {
        return outboundAirCost;
    }

    public void setOutboundAirCost(double outboundAirCost) {
        this.outboundAirCost = outboundAirCost;
    }

    public String getInboundAirRoute() {
        return inboundAirRoute;
    }

    public void setInboundAirRoute(String inboundAirRoute) {
        this.inboundAirRoute = inboundAirRoute;
    }

    public double getInboundAirCost() {
        return inboundAirCost;
    }

    public void setInboundAirCost(double inboundAirCost) {
        this.inboundAirCost = inboundAirCost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
