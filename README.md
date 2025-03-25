### RabbitMQ with Spring Boot
In this project the following RabbitMQ concepts are implemented:
* Publish and Consume events (Producer, Consumer)
* Dynamic Exchange, Queue addition through REST API
* Acknowledgement (Publish acknowledgement, Consume acknowledgement)

#### Steps to dynamically add exchange or queue
✅ Expose a REST API to dynamically create:
* Exchange (Direct, Topic, or Fanout)
* Queue
* Routing Key and Binding

✅ Use Spring AMQP (RabbitAdmin) to manage RabbitMQ dynamically.

✅ Verify the newly created exchange, queue, and binding in RabbitMQ Management UI.

Add the RabbitAdmin bean as configuration, which allows us to create queues and exchanges dynamically.
```
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
```
The `RabbitMQController.java` class file in the project is responsible for exposing API's for dynamically adding exchange, queue and binding queue with exchange.

#### Publish and Consume acknowledgement
When a message is sent to RabbitMQ, we want to confirm that:

✅ The message reached the exchange successfully.

✅ The message was routed to a queue successfully.

🛠 How It Works

1. Enable Publisher Confirms (confirmCallback) → Confirms message delivery to exchange.

2. Enable Return Callbacks (returnsCallback) → Confirms message was routed to a queue.

In the `application.properties` add the following properties:
```
spring.rabbitmq.publisher-confirm-type=correlated
spring.rabbitmq.publisher-returns=true
spring.rabbitmq.template.mandatory=true
```

1. `spring.rabbitmq.publisher-confirm-type=correlated`
   
    This enables publisher confirms, allowing the producer to verify that RabbitMQ has received the message at the exchange level.
2. `spring.rabbitmq.publisher-returns=true`

   This enables return callbacks, allowing the producer to verify if the message was routed to a queue.
3. `spring.rabbitmq.template.mandatory=true`

   By default, messages that can’t be routed are discarded ❌.

   This setting ensures unroutable messages are returned instead of being lost.

#### Configuration for acknowledgement
`RabbitCallbackConfig.java` class file implements the configuration for acknowledgement.

rabbitTemplate.setConfirmCallback() is for acknowledging if message reached to exchange or not.

rabbitTemplate.setReturnsCallback() is for acknowledging if message reached to queue or not.

#### Producer side
Send a `CorrelationData` for uniquely identify the message.

#### Consumer side acknowledgmemnt
1. Automatic Acknowledgement (ackMode=AUTO): RabbitMQ auto-acknowledges messages.

2. Manual Acknowledgement (ackMode=MANUAL): Consumer explicitly acknowledges after processing.

`RabbitMqConsumer.java` class implements the consumer with acknowledgement.