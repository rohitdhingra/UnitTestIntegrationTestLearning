package com.learning;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.MethodName.class)
class MethodsOrderedByNameTest {
    @Test
    void testD()
    {
        System.out.println("Executing testD");
    }
    @Test
    void testA()
    {
        System.out.println("Executing testA");
    }
    @Test
    void testC()
    {
        System.out.println("Executing testC");
    }
    @Test
    void testB()
    {
        System.out.println("Executing testB");
    }


}
