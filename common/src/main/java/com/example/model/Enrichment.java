package com.example.model;

public class Enrichment {

	private CategoryEvent category;
	private Device dev;
	private GeoEvent geo;

	public Enrichment() {
	}

	public Enrichment(CategoryEvent category, Device dev, GeoEvent geo) {
		this.category = category;
		this.dev = dev;
		this.geo = geo;
	}

	public CategoryEvent getCategory() {
		return category;
	}

	public void setCategory(CategoryEvent category) {
		this.category = category;
	}

	public Device getDev() {
		return dev;
	}

	public void setDev(Device dev) {
		this.dev = dev;
	}

	public GeoEvent getGeo() {
		return geo;
	}

	public void setGeo(GeoEvent geo) {
		this.geo = geo;
	}
}