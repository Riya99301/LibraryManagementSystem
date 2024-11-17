import java.util.ArrayList;

public class user {
    private String name;
    private ArrayList<Book> issuedBooks = new ArrayList<>();

    public user(String name) {
        this.name = name;
    }

    public void issueBook(Book book) {
        issuedBooks.add(book);
    }

    public void returnBook(Book book) {
        issuedBooks.remove(book);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Book> getIssuedBooks() {
        return issuedBooks;
    }
}
