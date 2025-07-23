package calculator;

import io.cucumber.java.en.*;

public class CalculatorSteps {
    private int res = 0;
    private Calculator calculator;

    @Given("I have a Calculator")
    public void iHaveACalculator() {
        this.calculator = new Calculator();
    }

    @When("I add {int} and {int}")
    public void iAddAnd(int arg0, int arg1) {
        this.calculator.enter(arg0);
        this.calculator.enter(arg1);
    }

    @Then("the sum should be {int}")
    public void theSumShouldBe(int arg0) {
        this.calculator.add();
        if (arg0 != this.calculator.getResult()) { // or using Junit's asserts
            throw new IllegalStateException();
        }
    }
    @When("I subtract {int} from {int}")
    public void iSubtractFrom(int arg1, int arg0) {
        this.calculator.enter(arg0); // minuend
        this.calculator.enter(arg1); // subtrahend
    }

    @Then("the difference should be {int}")
    public void theDifferenceShouldBe(int expected) {
        this.calculator.subtract();
        if (expected != this.calculator.getResult()) {
            throw new IllegalStateException();
        }
    }

    @When("I multiply {int} and {int}")
    public void iMultiplyAnd(int arg0, int arg1) {
        this.calculator.enter(arg0);
        this.calculator.enter(arg1);
    }

    @Then("the product should be {int}")
    public void theProductShouldBe(int expected) {
        this.calculator.multiply();
        if (expected != this.calculator.getResult()) {
            throw new IllegalStateException();
        }
    }

    @When("I divide {int} by {int}")
    public void iDivideBy(int dividend, int divisor) {
        this.calculator.enter(dividend);
        this.calculator.enter(divisor);
    }
}
