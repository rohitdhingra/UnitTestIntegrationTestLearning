package com.learning;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MethodsOrderedByIndexTest {
    @Order(1)
    @Test
    void testD()
    {
        System.out.println("Executing testD");
    }
    @Order(2)
    @Test
    void testA()
    {
        System.out.println("Executing testA");
    }
    @Order(3)
    @Test
    void testC()
    {
        System.out.println("Executing testC");
    }
    @Order(4)
    @Test
    void testB()
    {
        System.out.println("Executing testB");
    }
}
