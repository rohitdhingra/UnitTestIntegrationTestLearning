package com.learning;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DemoRepeatedTest {

    Calculator calculator;

    @BeforeEach
    void beforeEachMethod()
    {
        calculator = new Calculator();
        System.out.println("Executing @BeforeEach method");
    }

    @AfterEach
    void afterEachMethod()
    {
        calculator = null;
        System.out.println("Executing @AfterEach method");
    }
    @RepeatedTest(value = 3,name = "{displayName}Running test {currentRepetition} of {totalRepetitions}")
    @DisplayName("Test integerSubtraction with [minuend,subtrahend,expectedResult] Parameters")
    void testIntegerSubtractionWithParameters_whenValidValuesProvided_shouldReturnExpectedResult(RepetitionInfo repetitionInfo,TestInfo testInfo) {

        System.out.println("Test Method Name: "+testInfo.getTestMethod().get().getName());
        System.out.println("Repetition #"+repetitionInfo.getCurrentRepetition() +"of"+repetitionInfo.getTotalRepetitions());
        int minuend = 10;
        int subtrahend = 5;
        int expectedResult = 5;
        System.out.println("Running test "+minuend+"-"+subtrahend+"="+expectedResult);
        //Act
        int actualResult = calculator.integerSubtraction(minuend, subtrahend);
        //Assert
        assertEquals(expectedResult, actualResult,"The result of "+minuend+" minus "+subtrahend+" should be "+expectedResult);
    }
}
