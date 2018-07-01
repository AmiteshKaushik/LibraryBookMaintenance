package bookmaintenance.model;
	
public class BookDetailsBean {

	private long bookid;
	
	private String bookname;
	
	private String author;
	
	private double price;
	
	private String message;
	
	

	public BookDetailsBean(){
		bookid=0;
	}
	
	public BookDetailsBean(long bookid, String name, String author, double price){
		this.bookid = bookid;
		this.bookname = name;
		this.author = author;
		this.price = price;
	}
	

	
	public long getBookid() {
		return bookid;
	}

	public void setBookid(long bookid) {
		this.bookid = bookid;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (bookid ^ (bookid >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookDetailsBean other = (BookDetailsBean) obj;
		if (bookid != other.bookid)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [bookid=" + bookid + ", bookname=" + bookname + ", author=" + author
				+ ", price=" + price + "]";
	}


}
