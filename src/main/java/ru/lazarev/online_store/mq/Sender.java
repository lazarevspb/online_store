package ru.lazarev.online_store.mq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import ru.lazarev.online_store.dto.OrderDto;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class Sender {
    private static final String EXCHANGE_NAME = "online_store_serial";
    private static final String HOST = "localhost";
    private static final String ROUTING_KEY = "order";
    private static final String CHARSET_NAME = "UTF-8";

    public static void send(OrderDto orderDto) {
        ConnectionFactory factory = getConnectionFactory();


        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT, false, true, null);
            channel.basicPublish(EXCHANGE_NAME, "", null, SerializationUtils.serialize(orderDto));
            System.out.println(" [x] Sent" + orderDto);
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