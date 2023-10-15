import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Book {
    private int id;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void checkout() {
        isAvailable = false;
    }

    public void returnBook() {
        isAvailable = true;
    }

    @Override
    public String toString() {
        return "Book [ID: " + id + ", Title: " + title + ", Author: " + author + ", Available: " + isAvailable + "]";
    }
}

class Member {
    private int id;
    private String name;

    public Member(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Member [ID: " + id + ", Name: " + name + "]";
    }
}

class LibraryCard {
    private int cardNumber;
    private Member member;

    public LibraryCard(int cardNumber, Member member) {
        this.cardNumber = cardNumber;
        this.member = member;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    @Override
    public String toString() {
        return "LibraryCard [Card Number: " + cardNumber + ", Member: " + member + "]";
    }
}

class Transaction {
    private int transactionId;
    private LibraryCard libraryCard;
    private Book book;
    private boolean checkedOut;
    private Date date;

    public Transaction(int transactionId, LibraryCard libraryCard, Book book, boolean checkedOut, Date date) {
        this.transactionId = transactionId;
        this.libraryCard = libraryCard;
        this.book = book;
        this.checkedOut = checkedOut;
        this.date = date;
    }

    @Override
    public String toString() {
        String action = checkedOut ? "Checked Out" : "Returned";
        return "Transaction [ID: " + transactionId + ", Card: " + libraryCard.getCardNumber() + ", Book: " +
                book.getTitle() + ", Action: " + action + ", Date: " + date + "]";
    }
}

public class LibraryManagementSystem {
    private List<Book> books = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private List<LibraryCard> libraryCards = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList();
    private int bookIdCounter = 1;
    private int memberIdCounter = 1;
    private int cardNumberCounter = 1;
    private int transactionIdCounter = 1;

    public void addBook(String title, String author) {
        Book book = new Book(bookIdCounter++, title, author);
        books.add(book);
        System.out.println("Book added: " + book);
    }

    public void addMember(String name) {
        Member member = new Member(memberIdCounter++, name);
        members.add(member);
        System.out.println("Member added: " + member);
    }

    public void issueLibraryCard(int memberId) {
        Member member = findMember(memberId);
        if (member != null) {
            LibraryCard card = new LibraryCard(cardNumberCounter++, member);
            libraryCards.add(card);
            System.out.println("Library card issued: " + card);
        } else {
            System.out.println("Member not found.");
        }
    }

    public void checkoutBook(int cardNumber, int bookId) {
        LibraryCard card = findLibraryCard(cardNumber);
        Book book = findBook(bookId);

        if (card != null && book != null && book.isAvailable()) {
            Transaction transaction = new Transaction(transactionIdCounter++, card, book, true, new Date());
            transactions.add(transaction);
            book.checkout();
            System.out.println("Book checked out: " + book);
        } else {
            System.out.println("Unable to check out the book.");
        }
    }

    public void returnBook(int cardNumber, int bookId) {
        LibraryCard card = findLibraryCard(cardNumber);
        Book book = findBook(bookId);

        if (card != null && book != null && !book.isAvailable()) {
            Transaction transaction = new Transaction(transactionIdCounter++, card, book, false, new Date());
            transactions.add(transaction);
            book.returnBook();
            System.out.println("Book returned: " + book);
        } else {
            System.out.println("Unable to return the book.");
        }
    }

    public void displayBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void displayTransactions() {
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private Member findMember(int memberId) {
        for (Member member : members) {
            if (member.getId() == memberId) {
                return member;
            }
        }
        return null;
    }

    private LibraryCard findLibraryCard(int cardNumber) {
        for (LibraryCard card : libraryCards) {
            if (card.getCardNumber() == cardNumber) {
                return card;
            }
        }
        return null;
    }

    private Book findBook(int bookId) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                return book;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Library Management System Menu:");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Library Card");
            System.out.println("4. Check Out Book");
            System.out.println("5. Return Book");
            System.out.println("6. Display Books");
            System.out.println("7. Display Transactions");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    library.addBook(title, author);
                    break;
                case 2:
                    System.out.print("Enter member name: ");
                    String memberName = scanner.nextLine();
                    library.addMember(memberName);
                    break;
                case 3:
                    System.out.print("Enter member ID: ");
                    int memberId = scanner.nextInt();
                    library.issueLibraryCard(memberId);
                    break;
                case 4:
                    System.out.print("Enter library card number: ");
                    int cardNumber = scanner.nextInt();
                    System.out.print("Enter book ID: ");
                    int bookId = scanner.nextInt();
                    library.checkoutBook(cardNumber, bookId);
                    break;
                case 5:
                    System.out.print("Enter library card number: ");
                    cardNumber = scanner.nextInt();
                    System.out.print("Enter book ID: ");
                    bookId = scanner.nextInt();
                    library.returnBook(cardNumber, bookId);
                    break;
                case 6:
                    library.displayBooks();
                    break;
                case 7:
                    library.displayTransactions();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
