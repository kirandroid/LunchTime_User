package com.lunchtime.network.apiObjects.wrappers;

import com.lunchtime.network.apiObjects.models.MyOrder;

import java.util.List;

public class OrderWrapper {
    private List<MyOrder> order;

    public List<MyOrder> getOrder() {
        return order;
    }

    public OrderWrapper(List<MyOrder> order) {
        this.order = order;
    }
}
