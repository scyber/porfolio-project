package com.example.model;

public record UserProfileUpdated(String userId, long updatedAt, UserProfile profile) {
}