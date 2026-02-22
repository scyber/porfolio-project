package com.backend.personalization.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.personalization.model.ContentItem;
import com.backend.personalization.services.ContentService;

@RestController
@RequestMapping("/content")
public class ContentController {
	
	
	private final ContentService contentService;
	
	public ContentController(ContentService contentService) {
		this.contentService = contentService;
	}
	
	@GetMapping
	public List<ContentItem> getContent(@RequestParam String lang){
	
		throw new RuntimeException("Not Implemented Yet");
	}

}
