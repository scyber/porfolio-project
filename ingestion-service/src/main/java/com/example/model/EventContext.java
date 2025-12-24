package com.example.model;




public record EventContext(String page, 
							String itemId, 
							CategoryType category,
							Device device,
							String geo) {

	

}
