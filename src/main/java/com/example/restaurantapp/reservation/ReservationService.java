package com.example.restaurantapp.reservation;

import com.example.restaurantapp.domain.Restaurant;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ReservationService {

	private final WebClient webClient;

	public ReservationService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
	}

	public Mono<RestaurantAvailability> getAvailabilityFor(Restaurant restaurant) {
		return this.webClient.get()
				.uri("/restaurants/{name}/availability", restaurant.getName())
				.retrieve().bodyToMono(RestaurantAvailability.class);
	}

}
