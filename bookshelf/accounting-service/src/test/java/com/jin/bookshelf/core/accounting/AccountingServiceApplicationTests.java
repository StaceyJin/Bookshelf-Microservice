package com.jin.bookshelf.core.accounting;

import com.jin.api.core.accounting.Accounting;
import com.jin.bookshelf.core.accounting.datalayer.AccountingEntity;
import com.jin.bookshelf.core.accounting.datalayer.AccountingRepository;
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

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = { "spring.datasource.url=jdbc:h2:mem:accounting-db" })
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class AccountingServiceApplicationTests {

	private static final int BOOK_ID_OK = 1;
	private static final int BOOK_ID_NOT_FOUND = 113;
	private static final String BOOK_ID_INVALID_STRING = "not-integer";
	private static final int BOOK_ID_INVALID_NEGATIVE_VALUE = -1;
	private static final int BOOK_ID_INVALID_WRONG_GENRE = 201;

	private static final int ACCOUNTING_ID = 1;

	@Autowired
	private WebTestClient client;

	@Autowired
	AccountingRepository repository;

	@BeforeEach
	public void setupDb() { repository.deleteAll(); }

	@Test
	public void getAccountingByBookId(){

		int expectedLength = 1;

		AccountingEntity entity = new AccountingEntity(BOOK_ID_OK, ACCOUNTING_ID, 9.99, 4.99, 5.00, "m");
		repository.save(entity);

		assertEquals(expectedLength, repository.findByBookId(BOOK_ID_OK).size());

		client.get()
				.uri("/accounting?bookId=" + BOOK_ID_OK)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.length()").isEqualTo(expectedLength)
				.jsonPath("$[0].bookId").isEqualTo(BOOK_ID_OK);

	}

	@Test
	public void createAccounting(){

		int expectedSize = 1;

		Accounting accounting =  new Accounting(BOOK_ID_OK, ACCOUNTING_ID, 9.99, 4.99, 5.00, "m" + ACCOUNTING_ID,"SA");

		client.post()
				.uri("/accounting")
				.body(just(accounting), Accounting.class)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody();

		assertEquals(expectedSize, repository.findByBookId(BOOK_ID_OK).size());

	}

	@Test
	public void deleteAccountDetails(){

		AccountingEntity entity1 = new AccountingEntity(BOOK_ID_OK, ACCOUNTING_ID, 10.99, 5.99, 4.00, "m-1");
		repository.save(entity1);

		assertEquals(1,repository.findByBookId(BOOK_ID_OK).size());

		client.delete()
				.uri("/accounting?bookId=" + BOOK_ID_OK)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody();

		assertEquals(0, repository.findByBookId(BOOK_ID_OK).size());

	}

	@Test
	public void getAccountingMissingParameter() {

		client.get()
				.uri("/accounting")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/accounting")
				.jsonPath("$.message").isEqualTo("Required int parameter 'bookId' is not present");
	}

	@Test
	public void getAccountingInvalidParameterString() {

		client.get()
				.uri("/accounting?bookId=" + BOOK_ID_INVALID_STRING)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/accounting")
				.jsonPath("$.message").isEqualTo("Type mismatch.");
	}

	@Test
	public void getAccountingNotFound(){

		int expectedLength = 0;

		client.get()
				.uri("/accounting?bookId=" + BOOK_ID_NOT_FOUND)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.length()").isEqualTo(expectedLength);
	}

	@Test
	public void getAccountingInvalidParameterNegativeValue() {

		client.get()
				.uri("/accounting?bookId=" + BOOK_ID_INVALID_NEGATIVE_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/accounting")
				.jsonPath("$.message").isEqualTo("Invalid bookId: " + BOOK_ID_INVALID_NEGATIVE_VALUE);
	}

	@Test
	public void getAccountingInvalidParameterWrongGenre() {

		client.get()
				.uri("/accounting?bookId=" + BOOK_ID_INVALID_WRONG_GENRE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.NOT_ACCEPTABLE)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/accounting")
				.jsonPath("$.message").isEqualTo("Genre not recognized for bookId: " + BOOK_ID_INVALID_WRONG_GENRE);
	}

	@Test
	void contextLoads() {
	}

}
