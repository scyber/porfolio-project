package com.backend.personalization.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.personalization.model.ContentItem;

@Repository
public interface ContentRepository extends JpaRepository<ContentItem,Long> {
	
	ContentItem findById(long id);

}
