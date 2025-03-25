package org.example.springbootrabbitmq.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitCallbackConfig {
    @Autowired
    public void configureCallbacks(RabbitTemplate rabbitTemplate) {
        rabbitTemplate.setConfirmCallback((correlation, ack, cause) -> {
            if (ack) {
                System.out.println("✅ Exchange accepted message: " + correlation.getId());
            } else {
                System.out.println("❌ Exchange rejected message: " + cause);
            }
        });

        rabbitTemplate.setReturnsCallback(returned -> {
            String correlationId = returned.getMessage().getMessageProperties().getCorrelationId();
            System.out.println("❌❌ Message NOT delivered to queue. Reason: "
                    + returned.getReplyText()
                    + ", Routing Key: "
                    + returned.getRoutingKey());
        });
    }
}
