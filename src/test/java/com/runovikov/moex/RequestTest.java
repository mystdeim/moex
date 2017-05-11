package com.runovikov.moex;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author romanovi
 */
public class RequestTest {

    @Test
    public void testCorrect() {
        Request request = new Request(10, 100);
        assertEquals("10 1.00", request.toString());
    }

    @Test
    public void testNA() {
        Request request = new Request(0, -1);
        assertEquals("0 n/a", request.toString());
    }
}
