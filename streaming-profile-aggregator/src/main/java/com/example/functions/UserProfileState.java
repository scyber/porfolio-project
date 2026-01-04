package com.example.functions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.example.model.CategoryEvent;
import com.example.model.UserProfile;

public class UserProfileState {

    private Map<CategoryEvent, Double> interests = new ConcurrentHashMap<>();
    private Deque<String> recentItems = new ArrayDeque<>();
    private double activityScore = 0.0;

    // getters/setters

    public UserProfile toProfile() {
        return new UserProfile(interests, new ArrayList<>(recentItems), activityScore);
    }

    public Map<CategoryEvent, Double> getInterests() {
        return interests;
    }

    public Deque<String> getRecentItems() {
        return recentItems;
    }

    public double getActivityScore() {
        return activityScore;
    }

    public void setActivityScore(double activityScore) {
        this.activityScore = activityScore;
    }

}