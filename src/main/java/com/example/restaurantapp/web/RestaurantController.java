package com.example.restaurantapp.web;

import com.example.restaurantapp.domain.Restaurant;
import com.example.restaurantapp.domain.RestaurantAvailability;
import com.example.restaurantapp.domain.RestaurantRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class RestaurantController {

	private final RestaurantRepository repository;

	private final WebClient webClient;

	public RestaurantController(RestaurantRepository repository,
			WebClient.Builder webClientBuilder) {
		this.repository = repository;
		this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
	}

	@GetMapping("/restaurants")
	public Flux<Restaurant> getByCategoryAndPrice(@RequestParam String category,
			@RequestParam Double maxPrice) {
		return this.repository.findByCategoryAndPricePerPersonLessThan(category, maxPrice);
	}

	@GetMapping(path = "/restaurants/available", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<RestaurantAvailability> getAvailableRestaurants() {
		return this.repository.findAll()
				.flatMap(this::getAvailability)
				.filter(RestaurantAvailability::isAvailable)
				.take(3);
	}

	private Mono<RestaurantAvailability> getAvailability(Restaurant restaurant) {
		return this.webClient.get()
				.uri("/restaurants/{name}/availability", restaurant.getName())
				.retrieve().bodyToMono(RestaurantAvailability.class);
	}

}
