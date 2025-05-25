package com.programming.techie.orderservice.service;

import com.programming.techie.orderservice.client.InventoryClient;
import com.programming.techie.orderservice.dto.OrderRequest;
import com.programming.techie.orderservice.event.OrderPlacedEvent;
import com.programming.techie.orderservice.model.Order;
import com.programming.techie.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    //<topicname(key),message(value)>
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest){
        var isProductInStock=inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if(isProductInStock) {
            //map orderrequest to order object
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());

            //save order to orderrespository
            orderRepository.save(order);

            //Send the message to kafka topic
            OrderPlacedEvent orderPlacedEvent=new OrderPlacedEvent();
            orderPlacedEvent.setOrderNumber(order.getOrderNumber());
//            orderPlacedEvent.setEmail(orderRequest.userDetails().email());
//            orderPlacedEvent.setFirstName(orderRequest.userDetails().firstName());
//            orderPlacedEvent.setLastName(orderRequest.userDetails().lastName());
//            log.info("Start- Sending OrderPlacedEvent {} to Kafka topic order-placed", orderPlacedEvent);
            System.out.println("Sending OrderPlacedEvent");
            kafkaTemplate.send("order-placed", orderPlacedEvent);
//            log.info("End- Sending OrderPlacedEvent {} to Kafka topic order-placed", orderPlacedEvent);
            System.out.println("Sent OrderPlacedEvent");
        }
        else{
            throw new RuntimeException("Product with Skucode: "+orderRequest.skuCode()+" is not in stock");
        }

    }
}
