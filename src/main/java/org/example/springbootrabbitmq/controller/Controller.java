package org.example.springbootrabbitmq.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootrabbitmq.message.Message;
import org.example.springbootrabbitmq.producer.RabbitMqProducer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final RabbitMqProducer rabbitMqProducer;

    @PostMapping("/publish")
    public void publish(@RequestBody Message message) {
        rabbitMqProducer.sendMessage(message);
    }

    @PostMapping("/publish/{exchange}/{routing}")
    public void publish(@RequestBody Message message, @PathVariable String exchange, @PathVariable String routing) {
        rabbitMqProducer.sendMessageWithExchange(message, exchange, routing);
    }
}
