package com.backend.personalization.services;

import java.util.Optional;

import com.backend.personalization.model.Profile;

public interface ProfileService {


	Profile getProfile(Long userId);
}
