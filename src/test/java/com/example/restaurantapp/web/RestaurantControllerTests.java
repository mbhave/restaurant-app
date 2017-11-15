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
				.uri("/restaurants?category={c}&maxPrice={s}", "sushi", 20.00)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$[0].name").isEqualTo("Sushi2Go")
				.jsonPath("$.length()", 1);
	}

	private static Flux<Restaurant> sampleRestaurants() {
		return Flux.fromIterable(Set.of(
				new Restaurant("Sushi2Go", 15.00, "sushi"),
				new Restaurant("Burger Spring", 11.00, "fast-food"),
				new Restaurant("DoMONOs", 10.00, "fast-food"),
				new Restaurant("Cheesecake ProxyFactory", 9.50, "pastry"),
				new Restaurant("Sushi Heaven", 75.00, "sushi")
		));
	}

}
