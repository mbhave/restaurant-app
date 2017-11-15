package com.example.restaurantapp.web;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@WebFluxTest(RestaurantController.class)
public class RestaurantControllerTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void findCheapSushiPlaces() {
		this.webTestClient.get()
				.uri("/restaurants?category={c}&maxPrice={s}", "sushi", 20.00)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$[0].name").isEqualTo("Sushi2Go")
				.jsonPath("$.length()", 1);
	}

}
