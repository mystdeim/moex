package com.runovikov.moex;

import java.util.Locale;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * @author romanovi
 */
public class Main {

    public static void main(String[] args) {
        Auction a = new Auction();

        try (Scanner scan = new Scanner(System.in).useLocale(Locale.US)) {
            while (scan.hasNextLine()) {
                String type = scan.next(".");
                int count = scan.nextInt();
                double price = scan.nextDouble();

                a.add(type, count, price);
                scan.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Request request = a.optimumRequest();
        out.println(request);
    }
}
