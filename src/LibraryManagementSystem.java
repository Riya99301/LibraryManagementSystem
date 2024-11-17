import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class LibraryManagementSystem {
    private static ArrayList<Book> books = new ArrayList<>();
    private static ArrayList<String> issuedBooks = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    // ANSI color codes for output
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";

    public static void main(String[] args) {
        if (authenticateUser()) {
            while (true) {
                printBanner();
                showMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        viewBooks();
                        break;
                    case 3:
                        issueBook();
                        break;
                    case 4:
                        returnBook();
                        break;
                    case 5:
                        sortBooksByTitle();
                        break;
                    case 6:
                        showHelp();
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        printColored("Invalid choice. Please try again.", RED);
                }
            }
        }
    }

    public static boolean authenticateUser() {
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        if (username.equals("admin") && password.equals("password123")) {
            printColored("Login successful! Welcome, " + username + ".", GREEN);
            return true;
        } else {
            printColored("Invalid credentials. Access denied.", RED);
            return false;
        }
    }

    public static void printBanner() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║              LIBRARY MANAGEMENT SYSTEM       ║");
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    private static void printColored(String text, String colorCode) {
        System.out.println(colorCode + text + RESET);
    }

    private static void showMenu() {
        System.out.println("1. Add Book");
        System.out.println("2. View Books");
        System.out.println("3. Issue Book");
        System.out.println("4. Return Book");
        System.out.println("5. Sort Books by Title");
        System.out.println("6. Help");
        System.out.println("7. Exit");
        System.out.println("Enter your choice: ");
    }

    private static void addBook() {
        try {
            System.out.println("Enter book title: ");
            String title = scanner.nextLine();
            System.out.println("Enter author name: ");
            String author = scanner.nextLine();
            System.out.println("Enter ISBN: ");
            String isbn = scanner.nextLine();
            System.out.println("Enter quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            books.add(new Book(title, author, isbn, quantity));
            printColored("Book added successfully!", GREEN);
        } catch (Exception e) {
            printColored("Error adding book. Please try again.", RED);
        }
    }

    private static void viewBooks() {
        if (books.isEmpty()) {
            printColored("No books available in the library.", RED);
        } else {
            System.out.println("===== List of Books =====");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private static void issueBook() {
        System.out.println("Enter the title of the book to issue: ");
        String title = scanner.nextLine();
        boolean bookFound = false;

        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (book.getQuantity() > 0) {
                    book.setQuantity(book.getQuantity() - 1);
                    System.out.println("Enter your name to register the issue: ");
                    String userName = scanner.nextLine();
                    issuedBooks.add(userName + " issued \"" + book.getTitle() + "\"");
                    printColored("Book issued successfully to " + userName + "!", GREEN);
                } else {
                    printColored("Sorry, this book is currently out of stock.", RED);
                }
                bookFound = true;
                break;
            }
        }

        if (!bookFound) {
            printColored("Book not found in the library.", RED);
        }
    }

    private static void returnBook() {
        System.out.println("Enter the title of the book to return: ");
        String title = scanner.nextLine();
        boolean bookFound = false;

        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                book.setQuantity(book.getQuantity() + 1);
                printColored("Book returned successfully!", GREEN);
                bookFound = true;
                break;
            }
        }

        if (!bookFound) {
            printColored("Book not found in the library.", RED);
        }
    }

    private static void sortBooksByTitle() {
        Collections.sort(books, Comparator.comparing(Book::getTitle));
        printColored("Books sorted by title.", GREEN);
        viewBooks();
    }

    private static void showHelp() {
        System.out.println("===== Help Menu =====");
        System.out.println("1. Add Book - Adds a new book to the library.");
        System.out.println("2. View Books - Displays all books in the library.");
        System.out.println("3. Issue Book - Issues a book to a user.");
        System.out.println("4. Return Book - Returns an issued book to the library.");
        System.out.println("5. Sort Books by Title - Sorts the list of books alphabetically by title.");
        System.out.println("6. Help - Displays this help menu.");
        System.out.println("7. Exit - Exits the program.");
    }
}
