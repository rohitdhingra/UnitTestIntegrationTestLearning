package com.learning;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

    //Arrange
    private final Calculator calculator = new Calculator();

    @Test
    void testIntegerDivision_whenValidValuesProvided_shouldReturnExpectedResult() {
        //Act
        int result = calculator.integerDivision(8, 2);
        //Assert
        assertEquals(4, result,"The result of 8 divided by 2 should be 4");
    }
}
