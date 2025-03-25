package org.example.springbootrabbitmq.service;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {
    private final RabbitAdmin rabbitAdmin;

    public RabbitMQService(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    /**
     * Dynamically create an exchange (Direct, Topic, Fanout).
     */
    public void createExchange(String exchangeName, String type) {
        Exchange exchange;
        switch (type.toLowerCase()) {
            case "direct":
                exchange = new DirectExchange(exchangeName);
                break;
            case "topic":
                exchange = new TopicExchange(exchangeName);
                break;
            case "fanout":
                exchange = new FanoutExchange(exchangeName);
                break;
            default:
                throw new IllegalArgumentException("Invalid exchange type: Use direct, topic, or fanout.");
        }
        rabbitAdmin.declareExchange(exchange);
    }

    /**
     * Dynamically create a queue.
     */
    public void createQueue(String queueName) {
        Queue queue = new Queue(queueName, true); // durable queue
        rabbitAdmin.declareQueue(queue);
    }

    /**
     * Bind a queue to an exchange with a routing key. Must have existing Exchange and Queue
     */
    public void bindQueue(String queueName, String exchangeName, String routingKey) {
        Binding binding = BindingBuilder
                .bind(new Queue(queueName))
                .to(new DirectExchange(exchangeName))
                .with(routingKey);
        rabbitAdmin.declareBinding(binding);
    }
}
