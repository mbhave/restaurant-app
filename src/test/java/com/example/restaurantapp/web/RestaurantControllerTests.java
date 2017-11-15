package com.example.restaurantapp.web;

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
		given(repository.findByCategoryAndPricePerPersonLessThan("sushi", 20.00))
				.willReturn(Flux.just(new Restaurant("Sushi2Go", 35.00, "sushi")));
		this.webTestClient.get()
				.uri("/restaurants?category={c}&maxPrice={s}", "sushi", 20.00)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$[0].name").isEqualTo("Sushi2Go")
				.jsonPath("$.length()", 1);
	}

}
