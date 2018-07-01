package bookmaintenance.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import bookmaintenance.model.BookDetailsBean;



@Service("bookDetailsService")
public class BookDetailsServiceImpl implements BookDetailsService{
	
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<BookDetailsBean> bookDetailsBean;
	
	static{
		bookDetailsBean= populateDummyBooksFromDBJDBC();
	}

	public List<BookDetailsBean> findAllBooks() {
		return bookDetailsBean;
	}
	
	public BookDetailsBean findById(long bookid) {
		for(BookDetailsBean bookDetails : bookDetailsBean){
			if(bookDetails.getBookid() == bookid){
				return bookDetails;
			}
		}
		return null;
	}
	
	public BookDetailsBean findByName(String name) {
		for(BookDetailsBean bookDetails : bookDetailsBean){
			if(bookDetails.getBookname().equalsIgnoreCase(name)){
				return bookDetails;
			}
		}
		return null;
	}
	
	public void saveBook(BookDetailsBean bookDetails) {
		bookDetails.setBookid(counter.incrementAndGet());
		bookDetails.setMessage("The Book details are now created and Saved.");
		bookDetailsBean.add(bookDetails);
	}

	public void updateBook(BookDetailsBean bookDetails) {
		int index = bookDetailsBean.indexOf(bookDetails);
		bookDetailsBean.set(index, bookDetails);
	}

	public void deleteBookById(long bookid) {
		
		for (Iterator<BookDetailsBean> iterator = bookDetailsBean.iterator(); iterator.hasNext(); ) {
			BookDetailsBean bookDetails = iterator.next();
		    if (bookDetails.getBookid() == bookid) {
		        iterator.remove();
		    }
		}
	}

	public boolean isUserExist(BookDetailsBean bookDetails) {
		return findByName(bookDetails.getBookname())!=null;
	}
	
	public void deleteAllBooks(){
		bookDetailsBean.clear();
	}

	private static List<BookDetailsBean> populateDummyBooksFromDBJDBC(){
		List<BookDetailsBean> book = new ArrayList<BookDetailsBean>();
		book.add(new BookDetailsBean(counter.incrementAndGet(),"Hamlet","William Shakespeare", 700));
		book.add(new BookDetailsBean(counter.incrementAndGet(),"Harry Potter","JK Rowling", 500));
		book.add(new BookDetailsBean(counter.incrementAndGet(),"The Lord of the Rings","JRR Tolkien", 300));
		book.add(new BookDetailsBean(counter.incrementAndGet(),"Alice in Wonderland","Lewis Carroll", 400));
		return book;
	}

}
