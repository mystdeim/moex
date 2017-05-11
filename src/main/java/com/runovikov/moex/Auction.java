package com.runovikov.moex;

import com.runovikov.moex.Request.TYPE;

import java.util.*;

/**
 * @author romanovi
 */
public class Auction {

    private List<Request> buyRequests;
    private List<Request> sellRequests;

    public Auction() {
        this.buyRequests = new ArrayList<>();
        this.sellRequests = new ArrayList<>();
    }

    public void add(TYPE type, int count, long price) {
        switch (type) {
            case BUY:
                buyRequests.add(new Request(count, price));
                break;
            case SELL:
                sellRequests.add(new Request(count, price));
                break;
            default:
                throw new RuntimeException("Unknown type");
        }
    }

    public void add(String type, int count, double price) {
        add(mapType(type), count, (long)(price * 100));
    }

    public Request optimumRequest() {

        Collections.sort(buyRequests);
        Collections.sort(sellRequests);

        List<Request> list = countByPrice();
        return getMaxPrice(list);
    }

    List<Request> countByPrice() {
        List<Request> list = new ArrayList<>();

        for (int i=0; i<sellRequests.size(); i++) {

            int count = 0;
            long price = sellRequests.get(i).price;

            int bId = 0;
            int sId = i;

            Request bRequest = buyRequests.get(bId);
            Request sRequest = sellRequests.get(sId);
            while (bId < buyRequests.size() && sId < sellRequests.size()) {
                if (price <= bRequest.price) {
                    if (bRequest.count == sRequest.count) {
                        count += sRequest.count;
                        bRequest = safeGet(buyRequests, ++bId);
                        sRequest = safeGet(sellRequests, ++sId);
                    } else if (bRequest.count > sRequest.count) {
                        count += sRequest.count;
                        bRequest = new Request(bRequest.count - sRequest.count, price);
                        sRequest = safeGet(sellRequests, ++sId);
                    } else if (bRequest.count < sRequest.count) {
                        count += bRequest.count;
                        sRequest = new Request(sRequest.count - bRequest.count, price);
                        bRequest = safeGet(buyRequests, ++bId);
                    }
                } else {
                    break;
                }
            }
            if (count > 0) list.add(new Request(count, price));
        }
        return list;
    }

    Request getMaxPrice(List<Request> list) {
        if (0 == list.size()) return new Request(0, -1);

        Collections.sort(list);
        Request request = list.get(0);
        long price = request.price;
        int i;
        for (i = 1; i<list.size() && list.get(i).count == request.count; i++) {
            price += list.get(i).price;
        }

        return new Request(request.count, avg(price, i));
    }

    // Helpers
    // -----------------------------------------------------------------------------------------------------------------

    long avg(long price, int count) {
        return (long) Math.ceil(price / (double) count);
    }

    Request safeGet(List<Request> list, int i) {
        if (i >= list.size()) return null;
        else return list.get(i);
    }

    TYPE mapType(String type) {
        switch (type) {
            case "B": return TYPE.BUY;
            case "S": return TYPE.SELL;
            default: throw new RuntimeException("Unknown type");
        }
    }
}
