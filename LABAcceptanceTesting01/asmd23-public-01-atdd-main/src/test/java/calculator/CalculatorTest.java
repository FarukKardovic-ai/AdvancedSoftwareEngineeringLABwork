package calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    @Test

    void acceptanceTest(){
        Calculator calculator = new Calculator();
        // Test initial enter and result
        calculator.enter(5);
        assertEquals(5, calculator.getResult());

        // Test addition
        calculator.enter(6);
        calculator.add();
        assertEquals(11, calculator.getResult());

        // Test chained addition
        calculator.enter(3);
        calculator.add();
        assertEquals(14, calculator.getResult());
        assertEquals(14, calculator.getResult());

        // Reset for subtraction
        calculator = new Calculator();
        calculator.enter(10);
        calculator.enter(4);
        calculator.subtract();
        assertEquals(6, calculator.getResult());

        // Reset for multiplication
        calculator = new Calculator();
        calculator.enter(7);
        calculator.enter(3);
        calculator.multiply();
        assertEquals(21, calculator.getResult());

        // Reset for division
        calculator = new Calculator();
        calculator.enter(20);
        calculator.enter(4);
        calculator.divide();
        assertEquals(5, calculator.getResult());

        // Division by zero
        calculator = new Calculator();
        calculator.enter(10);
        calculator.enter(0);
        assertThrows(ArithmeticException.class, calculator::divide);

        // Not enough operands
        calculator = new Calculator();
        calculator.enter(42);
        assertThrows(IllegalStateException.class, calculator::multiply);
    }
}
