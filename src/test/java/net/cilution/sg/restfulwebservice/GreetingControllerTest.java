package net.cilution.sg.restfulwebservice;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GreetingControllerTest {

    private GreetingController controller;

    @Before
    public void setUp() throws Exception {
        controller = new GreetingController();
    }

    @Test
    public void canGreetWithNullPassed() {
        Greeting greeting = controller.greeting(null);
        assertEquals("Hello, null", greeting.getContent());
        assertEquals(1, greeting.getId());
    }

    @Test
    public void canGreetWithNamePassed() {
        Greeting greeting = controller.greeting("Charlie");
        assertEquals("Hello, Charlie", greeting.getContent());
        assertEquals(1, greeting.getId());
    }

    @Test
    public void greetingIdIsIncrementedForSubsequentCalls() {
        Greeting greeting1 = controller.greeting(null);
        Greeting greeting2 = controller.greeting(null);
        assertEquals(1, greeting1.getId());
        assertEquals(2, greeting2.getId());
    }
}