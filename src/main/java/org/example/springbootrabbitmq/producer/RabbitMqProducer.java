package org.example.springbootrabbitmq.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbootrabbitmq.message.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMqProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public void sendMessage(Message message) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        log.info("Event produce ------> " + message);

        rabbitTemplate.convertAndSend(exchangeName, routingKey, message, correlationData);
    }

    public void sendMessageWithExchange(Message message, String exchange, String routing) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        log.info("Event produce ------> " + message);

        rabbitTemplate.convertAndSend(exchange, routing, message, correlationData);
    }
}
