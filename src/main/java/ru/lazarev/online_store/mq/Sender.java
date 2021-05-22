package ru.lazarev.online_store.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import ru.lazarev.online_store.dto.OrderDto;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class Sender {
    private static final String EXCHANGE_NAME = "online_store_queue";
    private static final String HOST = "localhost";
    private static final String ROUTING_KEY = "order";
    private static final String CHARSET_NAME = "UTF-8";

    public static void send(OrderDto orderDto) {
        ConnectionFactory factory = getConnectionFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        String message = null;
        try {
            message = objectMapper.writeValueAsString(orderDto);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }


        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            if (message != null) {
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes(CHARSET_NAME));
            } else {
                log.error("Incorrect data");
            }
            log.info(String.format(" [x] Sent '%s'", message));
        } catch (TimeoutException | IOException e) {
            log.error(e.getMessage());
        }
    }

    private static ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        return factory;
    }
}