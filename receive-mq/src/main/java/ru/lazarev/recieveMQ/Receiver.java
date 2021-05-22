package ru.lazarev.recieveMQ;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import ru.lazarev.online_store.dto.OrderDto;

@Slf4j
public class Receiver {
    private static final String EXCHANGE_NAME = "online_store_serial";
    private static final String HOST = "localhost";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT, false, true, null);
        String queueName = channel.queueDeclare().getQueue();
        log.info("My queue name: " + queueName);
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        log.info("Receiver launched");
        DeliverCallback deliverCallback = Receiver::someOrderProcessing;
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

    private static void someOrderProcessing(String consumerTag, Delivery delivery) {
        OrderDto orderDto = SerializationUtils.deserialize(delivery.getBody());
        log.info(" [x] Received '" + orderDto);
    }
}
