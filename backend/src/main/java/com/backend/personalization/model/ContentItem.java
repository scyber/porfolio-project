package com.backend.personalization.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class ContentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_item_seq")
    @SequenceGenerator(name = "content_item_seq", sequenceName = "content_item_seq", allocationSize = 1)
    private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    
    
}
