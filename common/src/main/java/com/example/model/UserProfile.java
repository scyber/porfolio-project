package com.example.model;

import java.util.List;
import java.util.Map;

import com.example.model.CategoryEvent;

//TODO: move to model module
public record UserProfile(
                Map<CategoryEvent, Double> interests,
                List<String> recentItems,
                double activityScore) {
}
