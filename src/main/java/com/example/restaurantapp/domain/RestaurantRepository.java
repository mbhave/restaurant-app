package com.example.restaurantapp.domain;

import reactor.core.publisher.Flux;

import org.springframework.stereotype.Component;

@Component
public class RestaurantRepository {

	public Flux<Restaurant> findAll() {
		return Flux.empty();
	}

}
