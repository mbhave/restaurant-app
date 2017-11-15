package com.example.restaurantapp.web;

import com.example.restaurantapp.domain.Restaurant;
import com.example.restaurantapp.domain.RestaurantRepository;
import reactor.core.publisher.Flux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {

	private final RestaurantRepository repository;

	public RestaurantController(RestaurantRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/restaurants")
	public Flux<Restaurant> getByCategoryAndPrice(@RequestParam String category,
			@RequestParam Double maxPrice) {
		return this.repository.findByCategoryAndPricePerPersonLessThan(category, maxPrice);
	}

}
