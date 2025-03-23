package com.learning;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestMethodOrder(MethodOrderer.MethodName.class)
class MethodsOrderedByNameTest {

    StringBuilder completed = new StringBuilder("");

    @AfterEach
    void afterEach(){
        System.out.println("The state of each method"+completed);
    }
    @Test
    void testD()
    {
        System.out.println("Executing testD");
        completed.append("1");
    }
    @Test
    void testA()
    {
        System.out.println("Executing testA");
        completed.append("2");
    }
    @Test
    void testC()
    {
        System.out.println("Executing testC");
        completed.append("3");
    }
    @Test
    void testB()
    {
        System.out.println("Executing testB");
        completed.append("4");
    }


}
