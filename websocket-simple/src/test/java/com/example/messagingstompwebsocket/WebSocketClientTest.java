package com.example.messagingstompwebsocket;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author dorofeev
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class WebSocketClientTest {

    private String URL;

    @LocalServerPort
    private int port;

    private CompletableFuture<Cat> completableFuture;

    @Before
    public void setup() {
        completableFuture = new CompletableFuture<>();
        URL = "ws://localhost:" + port + "/gs-guide-websocket";
    }

    @Test
    public void sub() throws InterruptedException, ExecutionException, TimeoutException {
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession stompSession = stompClient.connect(URL, new MySessionHandler() {

        }).get(5, SECONDS);

        MappingJackson2MessageConverter m = new MappingJackson2MessageConverter();
        stompClient.setMessageConverter(m);

        stompSession.subscribe("/topic/greetings", new StompFrameHandlerForTest());

        Cat zoja = new Cat(1, "Zoja");
        stompSession.send("/topic/greetings", zoja);

        Cat resultCat = completableFuture.get(5, SECONDS);

        Assertions.assertNotNull(resultCat);
        Assertions.assertEquals(resultCat, zoja);
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    class StompFrameHandlerForTest implements StompFrameHandler {

        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            System.out.println("Headers " + stompHeaders.toString());
            return Cat.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            System.out.println((Cat) o);
            completableFuture.complete((Cat) o);
        }

    }

    class MySessionHandler extends StompSessionHandlerAdapter
    {
        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
            throw new RuntimeException("Failure in WebSocket handling", exception);
        }

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            System.out.println("after connect");
        }
    }
}

