package com.jin.bookshelf.core.portal;

import com.jin.api.core.portal.Portal;
import com.jin.bookshelf.core.portal.datalayer.PortalEntity;
import com.jin.bookshelf.core.portal.datalayer.PortalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static reactor.core.publisher.Mono.just;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = { "spring.datasource.url=jdbc:h2:mem:portal-db" })
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class PortalServiceApplicationTests {

	private static final int BOOK_ID_OK = 1;
	private static final int BOOK_ID_NOT_FOUND = 213;
	private static final String BOOK_ID_INVALID_STRING = "not-integer";
	private static final int BOOK_ID_INVALID_NEGATIVE_VALUE = -1;
	private static final int BOOK_ID_INVALID_WRONG_GENRE = 301;

	private static final int PORTAL_ID = 1;

	@Autowired
	private WebTestClient client;

	@Autowired
	PortalRepository repository;

	@BeforeEach
	public void setupDb() { repository.deleteAll(); }

	@Test
	public void getPortalsByBookId(){

		int expectedLength = 1;

		PortalEntity entity1 = new PortalEntity(BOOK_ID_OK, PORTAL_ID, "link-1", "username-1");
		repository.save(entity1);

		assertEquals(expectedLength, repository.findByBookId(BOOK_ID_OK).size());

		client.get()
				.uri("/portal?bookId=" + BOOK_ID_OK)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.length()").isEqualTo(expectedLength)
				.jsonPath("$[0].bookId").isEqualTo(BOOK_ID_OK);
	}

	@Test
	public void createPortal() {

		int expectedSize = 1;

		Portal portal = new Portal(BOOK_ID_OK, PORTAL_ID, "Link", "Username", "SA");

		client.post()
				.uri("/portal")
				.body(just(portal), Portal.class)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody();

		assertEquals(expectedSize, repository.findByBookId(BOOK_ID_OK).size());
	}

	@Test
	public void deletePortals(){

		PortalEntity entity = new PortalEntity(BOOK_ID_OK, PORTAL_ID, "link-1", "username-1");
		repository.save(entity);

		assertEquals(1, repository.findByBookId(BOOK_ID_OK).size());

		client.delete()
				.uri("/portal?bookId=" + BOOK_ID_OK)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody();

		assertEquals(0, repository.findByBookId(BOOK_ID_OK).size());
	}

	@Test
	public void getPortalMissingParameter() {

		client.get()
				.uri("/portal")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/portal")
				.jsonPath("$.message").isEqualTo("Required int parameter 'bookId' is not present");
	}

	@Test
	public void getPortalInvalidParameterString() {

		client.get()
				.uri("/portal?bookId=" + BOOK_ID_INVALID_STRING)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/portal")
				.jsonPath("$.message").isEqualTo("Type mismatch.");
	}

	@Test
	public void getPortalNotFound(){

		int expectedLength = 0;

		client.get()
				.uri("/portal?bookId=" + BOOK_ID_NOT_FOUND)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.length()").isEqualTo(expectedLength);
	}

	@Test
	public void getPortalInvalidParameterNegativeValue() {

		client.get()
				.uri("/portal?bookId=" + BOOK_ID_INVALID_NEGATIVE_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/portal")
				.jsonPath("$.message").isEqualTo("Invalid bookId: " + BOOK_ID_INVALID_NEGATIVE_VALUE);
	}

	@Test
	public void getPortalInvalidParameterUnknownType() {

		client.get()
				.uri("/portal?bookId=" + BOOK_ID_INVALID_WRONG_GENRE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.NOT_ACCEPTABLE)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/portal")
				.jsonPath("$.message").isEqualTo("Genre not recognized for bookId: " + BOOK_ID_INVALID_WRONG_GENRE);
	}

	@Test
	void contextLoads() {
	}

}
