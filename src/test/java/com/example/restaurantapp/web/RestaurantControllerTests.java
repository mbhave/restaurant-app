package com.example.restaurantapp.web;

import java.util.Set;

import com.example.restaurantapp.domain.Restaurant;
import com.example.restaurantapp.domain.RestaurantRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebFluxTest(RestaurantController.class)
public class RestaurantControllerTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private RestaurantRepository repository;

	@Test
	public void findCheapSushiPlaces() {
		given(repository.findAll()).willReturn(sampleRestaurants());
		this.webTestClient.get()
				.uri("/restaurants?category={c}&maxPrice={s}", "sushi", 40.00)
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(String.class)
				.contains("Sushi2Go")
				.hasSize(1);
	}

	private static Flux<Restaurant> sampleRestaurants() {
		return Flux.fromIterable(Set.of(
				new Restaurant("Sushi2Go", 35.00, "sushi"),
				new Restaurant("Sushi Heaven", 75.00, "sushi"),
				new Restaurant("Veggie place", 32.00, "vegan"),
				new Restaurant("Whatever", 42.00, "foo")
		));
	}

}
