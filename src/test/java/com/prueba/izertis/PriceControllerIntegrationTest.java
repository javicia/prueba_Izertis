package com.prueba.izertis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class PriceControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final String BASE_URL = "/prices";
    private static final String USER = "izertisUser";
    private static final String PASS = "izertisPass";
    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_DATE_TIME;

    private WebTestClient.RequestHeadersSpec<?> requestAt(LocalDateTime dateTime) {
        String iso = dateTime.format(ISO);
        return webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("applicationDate", iso)
                        .queryParam("productId", 35455)
                        .queryParam("brandId", 1)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBasicAuth(USER, PASS));
    }

    @Test
    @DisplayName("Test 1: 2020-06-14T10:00:00 returns priceList 1, price 35.50")
    void test1() {
        requestAt(LocalDateTime.parse("2020-06-14T10:00:00", ISO))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.priceList").isEqualTo(1)
                .jsonPath("$.price").isEqualTo(35.50)
                .jsonPath("$.startDate").isEqualTo("2020-06-14T00:00:00")
                .jsonPath("$.endDate").isEqualTo("2020-12-31T23:59:59");
    }

    @Test
    @DisplayName("Test 2: 2020-06-14T16:00:00 returns priceList 2, price 25.45")
    void test2() {
        requestAt(LocalDateTime.parse("2020-06-14T16:00:00", ISO))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.priceList").isEqualTo(2)
                .jsonPath("$.price").isEqualTo(25.45)
                .jsonPath("$.startDate").isEqualTo("2020-06-14T15:00:00")
                .jsonPath("$.endDate").isEqualTo("2020-06-14T18:30:00");
    }

    @Test
    @DisplayName("Test 3: 2020-06-14T21:00:00 returns priceList 1, price 35.50")
    void test3() {
        requestAt(LocalDateTime.parse("2020-06-14T21:00:00", ISO))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.priceList").isEqualTo(1)
                .jsonPath("$.price").isEqualTo(35.50)
                .jsonPath("$.startDate").isEqualTo("2020-06-14T00:00:00")
                .jsonPath("$.endDate").isEqualTo("2020-12-31T23:59:59");
    }

    @Test
    @DisplayName("Test 4: 2020-06-15T10:00:00 returns priceList 3, price 30.50")
    void test4() {
        requestAt(LocalDateTime.parse("2020-06-15T10:00:00", ISO))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.priceList").isEqualTo(3)
                .jsonPath("$.price").isEqualTo(30.50)
                .jsonPath("$.startDate").isEqualTo("2020-06-15T00:00:00")
                .jsonPath("$.endDate").isEqualTo("2020-06-15T11:00:00");
    }

    @Test
    @DisplayName("Test 5: 2020-06-16T21:00:00 returns priceList 4, price 38.95")
    void test5() {
        requestAt(LocalDateTime.parse("2020-06-16T21:00:00", ISO))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.priceList").isEqualTo(4)
                .jsonPath("$.price").isEqualTo(38.95)
                .jsonPath("$.startDate").isEqualTo("2020-06-15T16:00:00")
                .jsonPath("$.endDate").isEqualTo("2020-12-31T23:59:59");
    }
}
