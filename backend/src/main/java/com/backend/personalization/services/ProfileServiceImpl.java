package com.backend.personalization.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.personalization.model.Profile;
import com.backend.personalization.repositories.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService {
	
	private final ProfileRepository profileRepository;
	
	public ProfileServiceImpl(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

	@Override
	public Profile getProfile(Long userId) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Not implemented yet");
	}

}
