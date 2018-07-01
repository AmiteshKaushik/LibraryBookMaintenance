package bookmaintenance.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import bookmaintenance.model.BookDetailsBean;
import bookmaintenance.service.BookDetailsService;
import bookmaintenance.util.CustomErrorType;

@RestController
@RequestMapping("/library")
public class BookMaintenanceController {

	public static final Logger logger = LoggerFactory.getLogger(BookMaintenanceController.class);

	@Autowired
	BookDetailsService bookDetailsService; //Service has got the logic to get executed

	// -------------------Retrieve All the books---------------------------------------------

	@RequestMapping(value = "/books/", method = RequestMethod.GET)
	public ResponseEntity<List<BookDetailsBean>> listAllUsers() {
		List<BookDetailsBean> users = bookDetailsService.findAllBooks();
		if (users.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<BookDetailsBean>>(users, HttpStatus.OK);
	}

	// -------------------Retrieve Single Book------------------------------------------

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/books/{bookid}", method = RequestMethod.GET)
	public ResponseEntity<?> getBook(@PathVariable("bookid") long bookid) {
		logger.info("Fetching results with with bookid {}", bookid);
		BookDetailsBean bookDetails = bookDetailsService.findById(bookid);
		if (bookDetails == null) {
			logger.error("Book with id {} not found.", bookid);
			return new ResponseEntity(new CustomErrorType("Result with bookid " + bookid 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<BookDetailsBean>(bookDetails, HttpStatus.OK);
	}

	// -------------------Create the Book details-------------------------------------------

	@RequestMapping(value = "/books/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody BookDetailsBean bookDetails, UriComponentsBuilder ucBuilder) {
		logger.info("Creating User : {}", bookDetails);

		if (bookDetailsService.isUserExist(bookDetails)) {
			logger.error("Unable to create. A User with name {} already exist", bookDetails.getBookname());
			return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
			bookDetails.getBookname() + " already exist."),HttpStatus.CONFLICT);
		}
		bookDetailsService.saveBook(bookDetails);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/library/books/{bookid}").buildAndExpand(bookDetails.getBookid()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Book record ------------------------------------------------

	@RequestMapping(value = "/books/{bookid}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("bookid") long bookid, @RequestBody BookDetailsBean bookDetails) {
		logger.info("Updating the Book with id {}", bookid);

		BookDetailsBean currentBook = bookDetailsService.findById(bookid);

		if (currentBook == null) {
			logger.error("Unable to update. User with id {} not found.", bookid);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Book with id " + bookid + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentBook.setBookname(bookDetails.getBookname());
		currentBook.setAuthor(bookDetails.getAuthor());
		currentBook.setPrice(bookDetails.getPrice());

		bookDetailsService.updateBook(currentBook);
		return new ResponseEntity<BookDetailsBean>(currentBook, HttpStatus.OK);
	}

	// ------------------- Delete a Book-----------------------------------------

	@RequestMapping(value = "/books/{bookid}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("bookid") long bookid) {
		logger.info("Fetching & Deleting Book with id {}", bookid);

		BookDetailsBean bookDetails = bookDetailsService.findById(bookid);
		if (bookDetails == null) {
			logger.error("Unable to delete. User with id {} not found.", bookid);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Book with id " + bookid + " not found."),
					HttpStatus.NOT_FOUND);
		}
		bookDetailsService.deleteBookById(bookid);
		return new ResponseEntity<BookDetailsBean>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Books-----------------------------

	@RequestMapping(value = "/books/", method = RequestMethod.DELETE)
	public ResponseEntity<BookDetailsBean> deleteAllBooks() {
		logger.info("Deleting All Books");
		bookDetailsService.deleteAllBooks();
		return new ResponseEntity<BookDetailsBean>(HttpStatus.NO_CONTENT);
	}

}