package com.example.producer;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
public class ReservationTest {

	@Test
	public void create() throws Exception {
		Reservation reservation = new Reservation(null, "Bob");

		String name = reservation.getReservationName();

		Assert.assertEquals(name, "Bob");
		Assert.assertThat(name, Matchers.equalTo("Bob"));
		Assertions.assertThat(name).isEqualToIgnoringCase("bob");
	}
}
