package com.backend.personalization.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.personalization.model.Profile;
import com.backend.personalization.services.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {

	
	private final ProfileService profileService;
	
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	public Profile getProfile(@PathVariable String userId) {
		throw new RuntimeException("Not Implemented yet");
		
	}
}
