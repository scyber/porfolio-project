package com.backend.personalization.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.personalization.model.ContentItem;
import com.backend.personalization.repositories.ContentRepository;

@Service
public class ContentServiceImpl implements ContentService {
	
	private final ContentRepository contentRepository;
	
	public ContentServiceImpl(ContentRepository contentRepository) {
		this.contentRepository = contentRepository;
	}

	@Override
	public List<ContentItem> getContent(String lang) {
		// TODO Auto-generated method stub
		return null;
	}

}
