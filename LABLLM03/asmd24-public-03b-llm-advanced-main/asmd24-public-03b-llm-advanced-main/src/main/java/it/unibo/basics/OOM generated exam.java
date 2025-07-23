// File: LibraryItem.java

public abstract class LibraryItem {
    protected String title;
    protected String itemId;
    protected boolean isCheckedOut;

    public LibraryItem(String title, String itemId) {
        this.title = title;
        this.itemId = itemId;
        this.isCheckedOut = false; // Default value
    }

    public void checkOut() {
        this.isCheckedOut = true;
    }

    public void checkIn() {
        this.isCheckedOut = false;
    }

    // Abstract method - must be implemented by subclasses
    public abstract void display();
}

// File: Book.java

public class Book extends LibraryItem {
    private String author;

    public Book(String title, String itemId, String author) {
        // Call the constructor of the superclass (LibraryItem)
        super(title, itemId);
        this.author = author;
    }

    @Override // Annotation to indicate we are overriding a method from the superclass
    public void display() {
        String status = isCheckedOut ? "Checked Out" : "Available";
        System.out.println("Book Title: " + this.title);
        System.out.println("Author: " + this.author);
        System.out.println("Item ID: " + this.itemId);
        System.out.println("Status: " + status);
    }
}

// File: DVD.java

public class DVD extends LibraryItem {
    private String director;

    public DVD(String title, String itemId, String director) {
        // Call the constructor of the superclass (LibraryItem)
        super(title, itemId);
        this.director = director;
    }

    @Override // Annotation to indicate we are overriding a method from the superclass
    public void display() {
        String status = isCheckedOut ? "Checked Out" : "Available";
        System.out.println("DVD Title: " + this.title);
        System.out.println("Director: " + this.director);
        System.out.println("Item ID: " + this.itemId);
        System.out.println("Status: " + status);
    }
}

// File: Main.java

public class Main {
    public static void main(String[] args) {
        // Create library items
        Book book1 = new Book("The Hitchhiker's Guide to the Galaxy", "B001", "Douglas Adams");
        DVD dvd1 = new DVD("The Matrix", "D001", "Wachowskis");

        System.out.println("--- Initial Library Status ---");
        book1.display();
        System.out.println("--------------------");
        dvd1.display();

        // Demonstrate checking out
        System.out.println("\n--- Checking out 'The Hitchhiker's Guide to the Galaxy' ---");
        book1.checkOut();
        book1.display();

        System.out.println("\n--- Current Library Status ---");
        System.out.println("--------------------");
        dvd1.display();

        // Demonstrate checking in
        System.out.println("\n--- Checking in 'The Hitchhiker's Guide to the Galaxy' ---");
        book1.checkIn();
        book1.display();
    }
}