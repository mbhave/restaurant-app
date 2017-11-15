package com.example.restaurantapp.domain;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantAvailability {

	private final String name;

	private final boolean available;

	private final URI confirmationUri;

	@JsonCreator
	public RestaurantAvailability(String name, boolean available, URI confirmationUri) {
		this.name = name;
		this.available = available;
		this.confirmationUri = confirmationUri;
	}

	public String getName() {
		return this.name;
	}

	public boolean isAvailable() {
		return this.available;
	}

	public URI getConfirmationUri() {
		return this.confirmationUri;
	}

}
