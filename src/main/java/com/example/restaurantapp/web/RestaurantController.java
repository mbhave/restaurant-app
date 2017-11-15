package com.example.restaurantapp.web;

import com.example.restaurantapp.domain.Restaurant;
import com.example.restaurantapp.domain.RestaurantAvailability;
import com.example.restaurantapp.domain.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class RestaurantController {

	private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

	private final RestaurantRepository repository;

	private final WebClient webClient;

	public RestaurantController(RestaurantRepository repository,
			WebClient.Builder webClientBuilder) {
		this.repository = repository;
		this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
	}

	@GetMapping(path = "/restaurants", produces = MediaType.TEXT_PLAIN_VALUE)
	public Flux<String> getByCategoryAndPrice(@RequestParam String category,
			@RequestParam Double maxPrice) {
		return this.repository.findByCategoryAndPricePerPersonLessThan(category, maxPrice)
				.map(Restaurant::getName);
	}

	@GetMapping(path = "/restaurants/available", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<RestaurantAvailability> getAvailableRestaurants() {
		return this.repository.findAll()
				.flatMap(this::getAvailability)
				.filter(RestaurantAvailability::isAvailable)
				.take(3);
	}

	private Mono<RestaurantAvailability> getAvailability(Restaurant restaurant) {
		logger.info("Checking availability for {}", restaurant.getName());
		return this.webClient.get()
				.uri("/restaurants/{name}/availability", restaurant.getName())
				.retrieve().bodyToMono(RestaurantAvailability.class);
	}

}
