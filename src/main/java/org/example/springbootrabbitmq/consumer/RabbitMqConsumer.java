package org.example.springbootrabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.example.springbootrabbitmq.message.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class RabbitMqConsumer {

    @RabbitListener(queues = "${rabbitmq.queue.name}", ackMode = "MANUAL")
    public void receive(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
      try {
          log.info("receive message: {}", message);

          channel.basicAck(deliveryTag, false);
          System.out.println("✅ Acknowledged: " + message);
      } catch (Exception e) {
          System.out.println("❌ Failed. Requeuing: " + message);
          channel.basicNack(deliveryTag, false, true);
      }
    }
}
