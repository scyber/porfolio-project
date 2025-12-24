package com.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum EventType {
	
	VIEW, CLICK, ADD_TO_CHART, PURCHASE;

}
