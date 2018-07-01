package bookmaintenance.service;


import java.util.List;

import bookmaintenance.model.BookDetailsBean;


public interface BookDetailsService {
	
	BookDetailsBean findById(long id);
	
	BookDetailsBean findByName(String name);
	
	void saveBook(BookDetailsBean bookDetails);
	
	void updateBook(BookDetailsBean bookDetails);
	
	void deleteBookById(long id);

	List<BookDetailsBean> findAllBooks();
	
	void deleteAllBooks();
	
	boolean isUserExist(BookDetailsBean user);
	
}
