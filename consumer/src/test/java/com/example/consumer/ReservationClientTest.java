package com.example.consumer;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@SpringBootTest
//@AutoConfigureWireMock(port = 8080)
@AutoConfigureStubRunner ( ids = "com.example:producer:+:8080", stubsMode = StubRunnerProperties.StubsMode.LOCAL)
@RunWith(SpringRunner.class)
public class ReservationClientTest {

	@Autowired
	private ReservationClient reservationClient;

	@Test
	public void getAllReservations() {

		/*

		WireMock
			.stubFor(
				WireMock
					.any(WireMock.urlMatching("/reservations"))
					.willReturn(WireMock.aResponse()
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
						.withStatus(HttpStatus.OK.value())
						.withBody("[{\"id\":\"1\",\"name\":\"Jane\"},{\"id\":\"2\",\"name\":\"Bob\"}]")));
		*/
		Flux<Reservation> reservations = this.reservationClient
			.getAllReservations();

		StepVerifier
			.create(reservations)
			.expectNextMatches(r -> StringUtils.hasText(r.getId()) && StringUtils.hasText(r.getReservationName()))
			.expectNextMatches(r -> StringUtils.hasText(r.getId()) && StringUtils.hasText(r.getReservationName()))
			.verifyComplete();


	}


}