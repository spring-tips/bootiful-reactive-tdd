package com.example.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@DataMongoTest
@RunWith(SpringRunner.class)
public class ReservationRepositoryTest {

	@Autowired
	private ReservationRepository reservationRepository;

	@Test
	public void findByReservationName() throws Exception {

		Flux<Reservation> save =
			this.reservationRepository
				.deleteAll()
				.thenMany(
					Flux.just(new Reservation(null, "Jane"), new Reservation(null, "Bob"), new Reservation(null, "Janet"))
						.flatMap(r -> this.reservationRepository.save(r))
						.thenMany(this.reservationRepository.findByReservationName("Bob")));

		StepVerifier
			.create(save)
			.expectNextMatches(r -> r.getReservationName().equalsIgnoreCase("bob"))
			.verifyComplete();


	}
}
