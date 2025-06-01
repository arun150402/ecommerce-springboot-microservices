package com.programming.techie.notificationservice.service;

import com.programming.techie.common.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService{

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics="order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent){
        System.out.println("Got message from orderplaced topic: "+orderPlacedEvent);
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springshop@email.com");
            messageHelper.setTo("arunselva1504@gmail.com");
//          messageHelper.setTo(orderPlacedEvent.getEmail());
            messageHelper.setSubject(String.format("Your Order with OrderNumber %s is placed successfully", orderPlacedEvent.getOrderNumber()));
            messageHelper.setText(String.format("""
                            Hi

                            Your order with order number %s is now placed successfully.

                            Best Regards
                            Spring Shop
                            """,
                    orderPlacedEvent.getOrderNumber()));
        };
        try {
            javaMailSender.send(messagePreparator);
//            log.info("Order Notifcation email sent!!");
            System.out.println("Order Notification email sent!!");
        } catch (MailException e) {
//            log.error("Exception occurred when sending mail", e);
            System.out.println("Exception occurred when sending mail "+e);
            throw new RuntimeException("Exception occurred when sending mail to springshop@email.com", e);
        }
    }


}
