package org.example;

import org.test.Assertions;
import org.test.annotation.After;
import org.test.annotation.Before;
import org.test.annotation.Test;
import org.test.exception.AssertionException;

public class CalculatorTest {
    @Test
    public void testSumSuccess() throws AssertionException {
        Calculator calculator = new Calculator(1, 2);
        int result = calculator.sum();
        System.out.println(result);
        Assertions.assertEquals(3, result, "Result not equals");
        Assertions.assertNotEquals(2, result, null);
    }

    @Test
    public void testSumFailed() throws AssertionException {
        Calculator calculator = new Calculator(1, 2);
        int result = calculator.sum();
        System.out.println(result);
        Assertions.assertEquals(2, result, "Result not equals");
    }

    @Before
    public void before() {
        System.out.println("message: before");
    }

    @After
    public void after() {
        System.out.println("message: after");
    }
}
