package com.example.restaurantapp.web;

import com.example.restaurantapp.domain.Restaurant;
import com.example.restaurantapp.domain.RestaurantRepository;
import reactor.core.publisher.Flux;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {

	private final RestaurantRepository repository;

	public RestaurantController(RestaurantRepository repository) {
		this.repository = repository;
	}

	@GetMapping(path = "/restaurants", produces = MediaType.TEXT_PLAIN_VALUE)
	public Flux<String> getByCategoryAndPrice(@RequestParam String category,
			@RequestParam Double maxPrice) {
		return this.repository.findAll()
				.filter(restaurant -> category.equals(restaurant.getCategory()))
				.filter(restaurant -> restaurant.getPricePerPerson() < maxPrice)
				.map(Restaurant::getName);
	}

}
