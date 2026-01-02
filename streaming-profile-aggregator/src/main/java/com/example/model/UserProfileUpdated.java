package com.example.model;

//TODO: move to model module
public record UserProfileUpdated(
        String userId,
        long updatedAt,
        UserProfile profile) {
}