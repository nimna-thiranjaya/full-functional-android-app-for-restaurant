package com.example.grandlegacyresturant.Model;

public class Model {

    String name,feedback,rateCount;

    Model()
    {

    }
    public Model(String name, String feedback, String rateCount) {
        this.name = name;
        this.feedback = feedback;
        this.rateCount = rateCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRateCount() {
        return rateCount;
    }

    public void setRateCount(String rateCount) {
        this.rateCount = rateCount;
    }
}
