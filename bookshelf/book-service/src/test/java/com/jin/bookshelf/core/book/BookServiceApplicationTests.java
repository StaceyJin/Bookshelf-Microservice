package com.jin.bookshelf.core.book;

import com.jin.api.core.book.Book;
import com.jin.bookshelf.core.book.datalayer.BookEntity;
import com.jin.bookshelf.core.book.datalayer.BookRepository;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static reactor.core.publisher.Mono.just;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {"spring.data.mongodb.port:0"})
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class BookServiceApplicationTests {

	private static final int BOOK_ID_OK = 1;
	private static final int BOOK_ID_NOT_FOUND = 13;
	private static final String BOOK_ID_INVALID_STRING = "not-integer";
	private static final int BOOK_ID_INVALID_NEGATIVE_VALUE = -1;
	private static final int BOOK_ID_INVALID_WRONG_GENRE = 101;

	@Autowired
	private WebTestClient client;

	@Autowired
	private BookRepository repository;

	@BeforeEach
	public void setupDb() { repository.deleteAll(); }


	@Test
	public void getBookById(){

		BookEntity entity = new BookEntity(BOOK_ID_OK, "title-" + BOOK_ID_OK, "author-", "artist-", "subject-", "publisher-", "type-", "app-", "status-");
		repository.save(entity);

		assertTrue(repository.findByBookId(BOOK_ID_OK).isPresent());

		client.get()
				.uri("/book/" + BOOK_ID_OK)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.bookId").isEqualTo(BOOK_ID_OK);
	}

	@Test
	public void createBook(){

		Book book = new Book(BOOK_ID_OK,"title-", "author-", "artist-", "subject-", "publisher-", "type-", "app-", "status-", "SA");

		client.post()
				.uri("/book/")
				.body(just(book), Book.class)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.bookId").isEqualTo(BOOK_ID_OK);

		assertTrue(repository.findByBookId(BOOK_ID_OK).isPresent());
	}

	@Test
	public void deleteBook(){

		BookEntity entity = new BookEntity(BOOK_ID_OK, "title-" + BOOK_ID_OK, "author-", "artist-", "subject-", "publisher-", "type-", "app-", "status-");
		repository.save(entity);

		assertTrue(repository.findByBookId(BOOK_ID_OK).isPresent());

		client.delete()
				.uri("/book/" + BOOK_ID_OK)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody();

		assertFalse(repository.findByBookId(BOOK_ID_OK).isPresent());
	}

	@Test
	public void getBookInvalidParameterString(){

		client.get()
				.uri("/book/" + BOOK_ID_INVALID_STRING)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/book/" + BOOK_ID_INVALID_STRING)
				.jsonPath("$.message").isEqualTo("Type mismatch.");
	}

	@Test
	public void getBookNotFound(){

		client.get()
				.uri("/book/" + BOOK_ID_NOT_FOUND)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isNotFound()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/book/" + BOOK_ID_NOT_FOUND)
				.jsonPath("$.message").isEqualTo("No book found for bookId: " + BOOK_ID_NOT_FOUND);
	}

	@Test
	public void getBookInvalidParameterNegativeValue(){

		client.get()
				.uri("/book/" + BOOK_ID_INVALID_NEGATIVE_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/book/" + BOOK_ID_INVALID_NEGATIVE_VALUE)
				.jsonPath("$.message").isEqualTo("Invalid bookId: " + BOOK_ID_INVALID_NEGATIVE_VALUE);
	}

	@Test
	public void getBookInvalidParameterWrongGenre(){

		client.get()
				.uri("/book/" + BOOK_ID_INVALID_WRONG_GENRE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.NOT_ACCEPTABLE)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/book/" + BOOK_ID_INVALID_WRONG_GENRE)
				.jsonPath("$.message").isEqualTo("Genre not recognized for bookId: " + BOOK_ID_INVALID_WRONG_GENRE);
	}

	@Test
	void contextLoads() {
	}

}
