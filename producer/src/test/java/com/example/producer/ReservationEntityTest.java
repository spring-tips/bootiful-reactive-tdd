package com.example.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@RunWith(SpringRunner.class)
@DataMongoTest
public class ReservationEntityTest {

	private final Reservation one = new Reservation(null, "One");
	private final Reservation two = new Reservation(null, "Two");

	@Autowired
	private ReactiveMongoTemplate reactiveMongoTemplate;

	@Test
	public void persist() throws Exception {

		Flux<Reservation> saved = Flux
			.just(this.one, this.two)
			.flatMap(r -> this.reactiveMongoTemplate.save(r));

		Predicate<Reservation> p = r ->
			StringUtils.hasText(r.getId()) && StringUtils.hasText(r.getReservationName());

		StepVerifier
			.create(saved)
			.expectNextMatches(p)
			.expectNextMatches(p)
			.verifyComplete();

	}

}
