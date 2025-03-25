package org.example.springbootrabbitmq.controller;

import org.example.springbootrabbitmq.service.RabbitMQService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQController {
    private final RabbitMQService rabbitMQService;

    public RabbitMQController(RabbitMQService rabbitMQService) {
        this.rabbitMQService = rabbitMQService;
    }

    // Create a new exchange
    @PostMapping("/create-exchange/{name}/{type}")
    public String createExchange(@PathVariable String name, @PathVariable String type) {
        rabbitMQService.createExchange(name, type);
        return "Exchange created: " + name + " of type " + type;
    }

    // Create a new queue
    @PostMapping("/create-queue/{name}")
    public String createQueue(@PathVariable String name) {
        rabbitMQService.createQueue(name);
        return "Queue created: " + name;
    }

    // Bind queue to exchange with routing key
    @PostMapping("/bind-queue/{queue}/{exchange}/{routingKey}")
    public String bindQueue(@PathVariable String queue, @PathVariable String exchange, @PathVariable String routingKey) {
        rabbitMQService.bindQueue(queue, exchange, routingKey);
        return "Queue " + queue + " bound to " + exchange + " with key " + routingKey;
    }
}
