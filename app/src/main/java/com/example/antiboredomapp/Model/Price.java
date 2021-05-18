package com.example.antiboredomapp.Model;

public class Price {

    //For future implementations, i added the minimal price
    //For this project, I put it to 0 since Bored API
    //does not have many activities
    private double price_min = 0;
    private double price_max;

    public Price () {}

    public Price(double price_max) {
        this.price_max = price_max;
    }

    public double getPrice_min() {
        return price_min;
    }

    public void setPrice_min(double price_min) {
        this.price_min = price_min;
    }

    public double getPrice_max() {
        return price_max;
    }

    public void setPrice_max(double price_max) {
        this.price_max = price_max;
    }

    public String returnEstimate() {
        if(price_max != 0)
        {
            if (price_max == 0) {
                return "Free";
            } else if (price_max >= 0 && price_max <= 0.2) {
                return "Around $2 - $8";
            } else if (price_max >= 0.3 && price_max <= 0.6) {
                return "Around $8 - $14";
            } else if (price_max >= 0.7 && price_max <= 1) {
                return "More than $14";
            }
        }

        //By default
        return "Free";
    }
}
