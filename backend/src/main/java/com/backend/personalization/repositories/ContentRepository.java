package com.backend.personalization.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.personalization.model.ContentItem;

public interface ContentRepository extends JpaRepository<Long,ContentItem> {
	
	ContentItem findById(long id);

}
