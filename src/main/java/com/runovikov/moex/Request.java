package com.runovikov.moex;

import java.util.Locale;

/**
 * @author romanovi
 */
public class Request implements Comparable<Request> {

    enum TYPE { SELL, BUY }

    public final long price;
    public final int count;

    public Request(int count, long price) {
        this.price = price;
        this.count = count;
    }

    @Override
    public int compareTo(Request r) {
        return Long.compare(r.price, price);
    }

    @Override
    public String toString() {
        String pString = price > 0 ? String.format(Locale.US,"%.2f", price / 100.0) : "n/a";
        return String.format("%d %s", count, pString);
    }
}
