package com.example.model;


public class EventContext {

	private String page;
	private String itemId;
	private CategoryEvent category;
	private Device device;
	private GeoEvent geo;

	
	public EventContext() {
	}

	public EventContext(String page, String itemId, CategoryEvent category, Device device, GeoEvent geo) {
		this.page = page;
		this.itemId = itemId;
		this.category = category;
		this.device = device;
		this.geo = geo;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public CategoryEvent getCategory() {
		return category;
	}

	public void setCategory(CategoryEvent category) {
		this.category = category;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public GeoEvent getGeo() {
		return geo;
	}

	public void setGeo(GeoEvent geo) {
		this.geo = geo;
	}
}
