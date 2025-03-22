package com.learning;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Test Math Operations in Calculation Class")
class CalculatorTest {

    Calculator calculator;
    @BeforeAll
    static void setUp()
    {

        System.out.println("Executing @BeforeAll methods");
    }

    @AfterAll
    static void cleanUp()
    {
        System.out.println("Executing @AfterAll methods");
    }

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


    //test<System or Method under test>_<Condition or State Changed>_<Expected Results>
    //testIntegerDivision_whenEightDividedByTwo_shouldReturnFour
    @Test
    @DisplayName("Test 8/2 =4")
    void testIntegerDivision_whenValidValuesProvided_shouldReturnExpectedResult() {
        //Arrange
        int dividend = 8;
        int divisor = 2;
        int expectedResult = 4;

        //Act //when
        int actualResult = calculator.integerDivision(dividend, divisor);
        //Assert //then
        assertEquals(expectedResult, actualResult,"The result of 8 divided by 2 should be 4");
    }

    @Test
    @DisplayName("Test 8/0 = ArithmeticException")
//    @Disabled("look into exception handling in next section")
    void testIntegerDivision_whenDividendIsDivdedByZero_shouldThrowArithmeticException() {
        //arrange
        int dividend = 8;
        int divisor = 0;

        String expectedExceptionMessage ="/ by zero";

        //act & assert
        ArithmeticException actualException = assertThrows(ArithmeticException.class, () -> calculator.integerDivision(dividend, divisor), "Division by zero should have thrown arithmetic exception");

        //assert
        assertEquals(expectedExceptionMessage,actualException.getMessage(),"Unexepected expection message");
    }

    @Test
    @DisplayName("Test8-2 =6")
    void testIntegerSubtraction_whenValidValuesProvided_shouldReturnExpectedResult() {
        //Act
        int result = calculator.integerSubtraction(8, 2);
        //Assert
        assertEquals(6, result,"The result of 8 minus 2 should be 6");
    }
}
