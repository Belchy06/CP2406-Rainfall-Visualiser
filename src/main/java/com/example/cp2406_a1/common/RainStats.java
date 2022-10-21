package com.example.cp2406_a1.common;

public class RainStats {
    private double minRain = Double.POSITIVE_INFINITY;
    private double maxRain = Double.NEGATIVE_INFINITY;
    private double total = 0;

    private String name = null;

    public RainStats(String name) {
        this.name = name;
    }

    public RainStats(String name, double total, double minRain, double maxRain) {
        this.name = name;
        this.total = total;
        this.minRain = minRain;
        this.maxRain = maxRain;
    }

    public String toString() {
        return name + "," + total + "," + minRain + "," + maxRain;
    }

    public double getMinRain() {
        return minRain;
    }

    public void setMinRain(double minRain) {
        if(minRain < this.minRain) {
            this.minRain = minRain;
        }
    }

    public double getMaxRain() {
        return maxRain;
    }

    public void setMaxRain(double maxRain) {
        if(maxRain > this.maxRain) {
            this.maxRain = maxRain;
        }
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void updateTotal(double rain) {
        this.total += rain;
    }
}
