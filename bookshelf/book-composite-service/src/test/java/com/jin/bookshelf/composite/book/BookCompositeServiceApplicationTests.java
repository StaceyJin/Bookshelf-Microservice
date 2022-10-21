package com.jin.bookshelf.composite.book;

import com.jin.api.composite.book.BookAggregate;
import com.jin.api.core.accounting.Accounting;
import com.jin.api.core.book.Book;
import com.jin.api.core.portal.Portal;
import com.jin.bookshelf.composite.book.integrationlayer.BookCompositeIntegration;
import com.jin.utils.exceptions.InvalidInputException;
import com.jin.utils.exceptions.NotFoundException;
import com.jin.utils.exceptions.WrongGenreException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static java.util.Collections.singletonList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static reactor.core.publisher.Mono.just;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class BookCompositeServiceApplicationTests {

	private static final int BOOK_ID_OK = 1;
	private static final int BOOK_ID_NOT_FOUND = 213;
	private static final String BOOK_ID_INVALID_STRING = "not-integer";
	private static final int BOOK_ID_INVALID_NEGATIVE_VALUE = -1;
	private static final int BOOK_ID_INVALID_WRONG_GENRE = 101;

	@Autowired
	private WebTestClient client;

	@MockBean
	private BookCompositeIntegration compositeIntegration;

	@BeforeEach
	void setup(){

		when(compositeIntegration.getBook(BOOK_ID_OK))
				.thenReturn(new Book(BOOK_ID_OK, "title 1", "author 1", "artist 1", "subject 1", "publisher 1", "type 1", "app 1", "status 1", "mock address"));

		given(compositeIntegration.getBook(BOOK_ID_OK))
				.willReturn(new Book(BOOK_ID_OK, "title 1", "author 1", "artist 1", "subject 1", "publisher 1", "type 1", "app 1", "status 1", "mock address"));

		when(compositeIntegration.getAccounting(BOOK_ID_OK))
				.thenReturn(singletonList(new Accounting(BOOK_ID_OK, 1, 9.99, 5.55, 4.44, "method 1","mock address")));

		when(compositeIntegration.getPortal(BOOK_ID_OK))
				.thenReturn(singletonList(new Portal(BOOK_ID_OK, 1,"https://play.google.com/books", "myBookAccount", "mock address")));

		when(compositeIntegration.getBook(BOOK_ID_NOT_FOUND))
				.thenThrow(new NotFoundException("NOT FOUND: " + BOOK_ID_NOT_FOUND));

		when(compositeIntegration.getBook(BOOK_ID_INVALID_NEGATIVE_VALUE))
				.thenThrow(new InvalidInputException("INVALID: " + BOOK_ID_INVALID_NEGATIVE_VALUE));

		when(compositeIntegration.getBook(BOOK_ID_INVALID_WRONG_GENRE))
				.thenThrow(new WrongGenreException("NOT ACCEPTABLE: " + BOOK_ID_INVALID_WRONG_GENRE));
	}

	@Test
	public void getBookById(){

		int expectedLength = 1;

		client.get()
				.uri("/book-composite/" + BOOK_ID_OK)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.bookId").isEqualTo(BOOK_ID_OK)
				.jsonPath("$.accountings.length()").isEqualTo(expectedLength)
				.jsonPath("$.portals.length()").isEqualTo(expectedLength);
	}

	@Test
	public void createCompositeBook() {

		BookAggregate compositeBook = new BookAggregate(1, "title", "author", "artist", "subject", "publisher", "type", "app", "status", null, null, null);

			client.post()
					.uri("/book-composite")
					.body(just(compositeBook), BookAggregate.class)
					.exchange()
					.expectStatus().isOk();

	}

	@Test
	public void deleteCompositeBook() {

		BookAggregate compositeBook = new BookAggregate(1, "title", "author", "artist", "subject", "publisher", "type", "app", "status", null, null, null);

			client.delete()
					.uri("/book-composite/" + BOOK_ID_OK)
					//.accept(MediaType.APPLICATION_JSON)
					.exchange()
					.expectStatus().isOk();
	}

	@Test
	public void getBookNotFound(){

		client.get()
				.uri("/book-composite/" + BOOK_ID_NOT_FOUND)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isNotFound()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/book-composite/" + BOOK_ID_NOT_FOUND)
				.jsonPath("$.message").isEqualTo("NOT FOUND: " + BOOK_ID_NOT_FOUND);
	}



	@Test
	public void getBookInvalidParameterNegativeValue(){

		client.get()
				.uri("/book-composite/" + BOOK_ID_INVALID_NEGATIVE_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/book-composite/" + BOOK_ID_INVALID_NEGATIVE_VALUE)
				.jsonPath("$.message").isEqualTo("INVALID: " + BOOK_ID_INVALID_NEGATIVE_VALUE);
	}

	@Test
	public void getBookInvalidParameterWrongGenre(){

		client.get()
				.uri("/book-composite/" + BOOK_ID_INVALID_WRONG_GENRE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.NOT_ACCEPTABLE)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/book-composite/" + BOOK_ID_INVALID_WRONG_GENRE)
				.jsonPath("$.message").isEqualTo("NOT ACCEPTABLE: " + BOOK_ID_INVALID_WRONG_GENRE);
	}


	@Test
	public void getBookInvalidParameterString(){

		client.get()
				.uri("/book-composite/" + BOOK_ID_INVALID_STRING)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/book-composite/" + BOOK_ID_INVALID_STRING)
				.jsonPath("$.message").isEqualTo("Type mismatch.");
	}

	@Test
	void contextLoads() {
	}

}
