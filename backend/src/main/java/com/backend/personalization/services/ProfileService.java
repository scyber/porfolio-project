package com.backend.personalization.services;

import com.backend.personalization.model.Profile;

public interface ProfileService {

	Profile getProfile(String userId);
}
