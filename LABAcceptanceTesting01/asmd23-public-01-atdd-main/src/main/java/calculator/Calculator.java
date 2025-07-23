package calculator;

import java.util.*;

public class Calculator {
    private List<Integer> numbers = new LinkedList<>();

    public void enter(int i){
        numbers.add(i);
        if (numbers.size() > 2){
            throw new IllegalStateException("Only two operands allowed");
        }
    }

    public void add(){
        checkOperands();
        numbers.set(0, numbers.get(0) + numbers.get(1));
        numbers.remove(1);
    }

    public void subtract(){
        checkOperands();
        numbers.set(0, numbers.get(0) - numbers.get(1));
        numbers.remove(1);
    }

    public void multiply(){
        checkOperands();
        numbers.set(0, numbers.get(0) * numbers.get(1));
        numbers.remove(1);
    }

    public void divide(){
        checkOperands();
        int divisor = numbers.get(1);
        if (divisor == 0){
            throw new ArithmeticException("Cannot divide by zero");
        }
        numbers.set(0, numbers.get(0) / divisor);
        numbers.remove(1);
    }

    public int getResult(){
        if (numbers.size() != 1){
            throw new IllegalStateException("Result not ready or too many operands");
        }
        return numbers.get(0);
    }

    private void checkOperands(){
        if (numbers.size() != 2){
            throw new IllegalStateException("Operation requires exactly two operands");
        }
    }
}
