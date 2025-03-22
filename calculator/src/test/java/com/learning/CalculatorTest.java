package com.learning;

import org.junit.jupiter.api.Test;

class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    void testIntegerDivision() {
        int result = calculator.integerDivision(8, 2);
        if (result == 4) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }
    }
}
