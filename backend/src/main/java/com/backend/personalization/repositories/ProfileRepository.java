package com.backend.personalization.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.personalization.model.Profile;

public interface ProfileRepository extends JpaRepository<Long,Profile> {
	
	Profile findById(long id);

}
