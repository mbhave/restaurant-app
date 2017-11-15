package com.example.restaurantapp.web;

import com.example.restaurantapp.domain.Restaurant;
import com.example.restaurantapp.domain.RestaurantRepository;
import com.example.restaurantapp.reservation.ReservationService;
import com.example.restaurantapp.reservation.RestaurantAvailability;
import reactor.core.publisher.Flux;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {

	private final RestaurantRepository repository;

	private final ReservationService reservationService;

	public RestaurantController(RestaurantRepository repository,
			ReservationService reservationService) {
		this.repository = repository;
		this.reservationService = reservationService;
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
				.flatMap(this.reservationService::getAvailabilityFor)
				.take(3);
	}

}
