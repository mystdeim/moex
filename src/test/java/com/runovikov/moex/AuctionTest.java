package com.runovikov.moex;

import org.junit.Before;

import com.runovikov.moex.Request.TYPE;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author romanovi
 */
@SuppressWarnings("Duplicates")
public class AuctionTest {

    Auction auction;

    @Before
    public void setUp() {
        auction = new Auction();
    }

    @Test
    public void testAvg() {
        assertEquals(34, auction.avg(100, 3));
    }

    @Test
    public void testNA() {
        auction.add(TYPE.BUY, 100, 1010);
        auction.add(TYPE.SELL, 150, 1020);

        Request op = auction.optimumRequest();
        assertEquals(0, op.count);
        assertEquals(-1, op.price);
    }

    @Test
    public void testCase1() {
        auction.add(TYPE.BUY, 100, 1540);
        auction.add(TYPE.BUY, 100, 1530);
        auction.add(TYPE.SELL, 150, 1530);

        Request op = auction.optimumRequest();
        assertEquals(150, op.count);
        assertEquals(1530, op.price);
    }

    @Test
    public void testCase2() {
        auction.add(TYPE.BUY, 100, 1540);
        auction.add(TYPE.SELL, 150, 1530);

        Request op = auction.optimumRequest();
        assertEquals(100, op.count);
        assertEquals(1530, op.price);
    }

    @Test
    public void testCase3() {
        auction.add(TYPE.BUY, 1000, 1530);
        auction.add(TYPE.SELL, 150, 1530);

        Request op = auction.optimumRequest();
        assertEquals(150, op.count);
        assertEquals(1530, op.price);
    }

    @Test
    public void testCase4() {
        auction.add(TYPE.BUY, 1000, 100);
        auction.add(TYPE.SELL, 10, 110);
        auction.add(TYPE.SELL, 100, 100);

        Request op = auction.optimumRequest();
        assertEquals(100, op.count);
        assertEquals(100, op.price);
    }

    @Test
    public void testCase5() {
        auction.add(TYPE.BUY, 1000, 1000);
        auction.add(TYPE.SELL, 10, 200);
        auction.add(TYPE.SELL, 20, 100);

        Request op = auction.optimumRequest();
        assertEquals(30, op.count);
        assertEquals(200, op.price);
    }

    @Test
    public void testCase6() {
        auction.add(TYPE.BUY, 1000, 11);
        auction.add(TYPE.BUY, 1000, 12);
        auction.add(TYPE.BUY, 1000, 13);
        auction.add(TYPE.SELL, 100, 10);
        auction.add(TYPE.SELL, 100, 11);
        auction.add(TYPE.SELL, 100, 12);

        Request op = auction.optimumRequest();
        assertEquals(300, op.count);
        assertEquals(12, op.price);
    }

    @Test
    public void testCase7() {
        auction.add(TYPE.BUY, 1000, 11);
        auction.add(TYPE.BUY, 100, 12);
        auction.add(TYPE.BUY, 100, 13);
        auction.add(TYPE.SELL, 100, 10);
        auction.add(TYPE.SELL, 100, 11);
        auction.add(TYPE.SELL, 100, 12);

        Request op = auction.optimumRequest();
        assertEquals(200, op.count);
        assertEquals(12, op.price);
    }

    @Test
    public void testCase8() {
        auction.add(TYPE.BUY, 10, 20);
        auction.add(TYPE.SELL, 10, 10);
        auction.add(TYPE.SELL, 10, 20);

        Request op = auction.optimumRequest();
        assertEquals(10, op.count);
        assertEquals(15, op.price);
    }

    @Test
    public void testCase9() {
        auction.add(TYPE.BUY, 10, 10);
        auction.add(TYPE.BUY, 10, 11);
        auction.add(TYPE.SELL, 5, 10);
        auction.add(TYPE.SELL, 5, 10);

        Request op = auction.optimumRequest();
        assertEquals(10, op.count);
        assertEquals(10, op.price);
    }

}
