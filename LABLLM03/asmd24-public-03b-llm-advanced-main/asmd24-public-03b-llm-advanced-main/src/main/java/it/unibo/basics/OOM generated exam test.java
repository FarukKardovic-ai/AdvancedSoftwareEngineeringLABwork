// File: LibrarySystemTest.java

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class LibrarySystemTest {

    private Book book;
    private DVD dvd;

    // To capture console output
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    // This method runs before each test
    @BeforeEach
    public void setUp() {
        book = new Book("The Lord of the Rings", "B002", "J.R.R. Tolkien");
        dvd = new DVD("Inception", "D002", "Christopher Nolan");

        // Redirect System.out to our stream to capture output
        System.setOut(new PrintStream(outContent));
    }

    // This method runs after each test
    @AfterEach
    public void tearDown() {
        // Restore the original System.out
        System.setOut(originalOut);
    }

    @Test
    void testBookCreation() {
        assertEquals("The Lord of the Rings", book.getTitle());
        assertEquals("B002", book.getItemId());
        assertEquals("J.R.R. Tolkien", book.getAuthor());
        assertFalse(book.isCheckedOut());
    }

    @Test
    void testDvdCreation() {
        assertEquals("Inception", dvd.getTitle());
        assertEquals("D002", dvd.getItemId());
        assertEquals("Christopher Nolan", dvd.getDirector());
        assertFalse(dvd.isCheckedOut());
    }

    @Test
    void testCheckOut() {
        book.checkOut();
        assertTrue(book.isCheckedOut(), "Book should be checked out");
        dvd.checkOut();
        assertTrue(dvd.isCheckedOut(), "DVD should be checked out");
    }

    @Test
    void testCheckIn() {
        // First, check them out
        book.checkOut();
        dvd.checkOut();

        // Then, check them in
        book.checkIn();
        assertFalse(book.isCheckedOut(), "Book should be checked in");
        dvd.checkIn();
        assertFalse(dvd.isCheckedOut(), "DVD should be checked in");
    }

    @Test
    void testBookDisplay() {
        book.display();
        // The line separator may vary by OS, so we build the expected string carefully.
        String expectedOutput = "Book Title: The Lord of the Rings" + System.lineSeparator() +
                "Author: J.R.R. Tolkien" + System.lineSeparator() +
                "Item ID: B002" + System.lineSeparator() +
                "Status: Available" + System.lineSeparator();

        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testDvdDisplayCheckedOut() {
        dvd.checkOut();
        dvd.display();
        String expectedOutput = "DVD Title: Inception" + System.lineSeparator() +
                "Director: Christopher Nolan" + System.lineSeparator() +
                "Item ID: D002" + System.lineSeparator() +
                "Status: Checked Out" + System.lineSeparator();

        assertEquals(expectedOutput, outContent.toString());
    }

    /*
     * Note on the 'test_base_class_display_raises_error' test from Python:
     *
     * In Java, the LibraryItem class is 'abstract'. The Java compiler will not allow you
     * to create an instance of an abstract class using 'new LibraryItem(...)'.
     * This prevents the error from ever occurring at runtime.
     *
     * Therefore, a direct translation of that specific test is not possible because
     * the Java language itself enforces this rule at compile time, making the runtime
     * test unnecessary.
     */
}