package com.backend.personalization.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.personalization.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {
	
	Profile findById(long id);

}
