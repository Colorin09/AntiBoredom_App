package com.example.antiboredomapp.Model;

public class Accessibility {

    private final double QUICK_ACCESS_MIN = 0;
    private final double QUICK_ACCESS_MAX = 0.3;
    private final double MEDIUM_ACCESS_MAX = 0.7;
    private final double HIGH_ACCESS_MAX = 1;

    public double access_level;

    public String level = "Quick";

    public Accessibility() {
    }

    public Accessibility(double access_level) {
        this.access_level = access_level;
    }

    public double[] returnAccessLimits(){
        if(level.equals("Quick"))
        {
            double[] result = {QUICK_ACCESS_MIN, QUICK_ACCESS_MAX};
            return result;
        }
        else if(level.equals("Medium")){
            double[] result = {QUICK_ACCESS_MIN, MEDIUM_ACCESS_MAX};
            return result;
        }
        else if(level.equals("High")){
            double[] result = {QUICK_ACCESS_MIN, HIGH_ACCESS_MAX};
            return result;
        }
        else{
            //return Quick levels by default
            double[] result = {QUICK_ACCESS_MIN, QUICK_ACCESS_MAX};
            return result;
        }
    }

    public String toString(){
        if(level.equals("Quick"))
        {
            return "No set up needed";
        }
        else if(level.equals("Medium")){
            return "You'll need so materials";
        }
        else if(level.equals("High")){
            return "You need to set up first";
        }
        else{
            return "Do it now! :D";
        }
    }

    public double getAccess_level() {
        return access_level;
    }

    public void setAccess_level(double access_level) {
        this.access_level = access_level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
