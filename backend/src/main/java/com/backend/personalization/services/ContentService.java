package com.backend.personalization.services;

import java.util.List;

import com.backend.personalization.model.ContentItem;

public interface ContentService {

	List<ContentItem>getContent(String lang);
	
}
