package com.programming.techie.common.event;


public class OrderPlacedEvent {

    private String orderNumber;
//    private String email;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderPlacedEvent(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
