# Simple WebSocket in Spring Boot by STOMP

## About

#### WebSocket and STOMP Protocols

The WebSocket protocol allows you to implement bidirectional communication 
between applications. It is important to know that HTTP is used only for the 
initial handshake. After it happens, the HTTP connection is upgraded to a 
newly opened TCP/IP connection that is used by a WebSocket.
  
The WebSocket protocol is a rather low-level protocol. It defines how a stream 
of bytes is transformed into frames. A frame may contain a text or a binary 
message. Because the message itself does not provide any additional information 
on how to route or process it, It is difficult to implement more complex 
applications without writing additional code. Fortunately, the WebSocket 
specification allows using of sub-protocols that operate on a higher, 
application level. One of them, supported by the Spring Framework, is STOMP.
  
STOMP is a simple text-based messaging protocol that was initially created 
for scripting languages such as Ruby, Python, and Perl to connect to 
enterprise message brokers. Thanks to STOMP, clients and brokers developed 
in different languages can send and receive messages to and from each other. 
The WebSocket protocol is sometimes called TCP for Web. Analogically, 
STOMP is called HTTP for Web. It defines a handful of frame types that 
are mapped onto WebSockets frames, e.g., CONNECT, SUBSCRIBE, UNSUBSCRIBE, 
ACK, or SEND. On one hand, these commands are very handy to manage 
communication while, on the other, they allow us to implement solutions 
with more sophisticated features like message acknowledgment.

## Install

### Dependencies
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
```

additional libs for js:
```
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>webjars-locator-core</artifactId>
</dependency>
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>sockjs-client</artifactId>
    <version>1.0.2</version>
</dependency>
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>stomp-websocket</artifactId>
    <version>2.3.3</version>
</dependency>
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>bootstrap</artifactId>
    <version>3.3.7</version>
</dependency>
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>jquery</artifactId>
    <version>3.1.1-1</version>
</dependency>
```

## Configuration

#### 1. Enable WebSocket
```
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
```

* "/gs-guide-websocket" - main registration point WebSocket (means 'connection')
* `registry.enableSimpleBroker("/topic");` - set prefix for all topics (Ex.: '/topic/ahah', '/topic/dog')
* `registry.setApplicationDestinationPrefixes("/app");` - set prefix for send message interaction (Ex.: '/api/books')

#### 2. Adding endpoints to handle messages

```
@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
```

* `@MessageMapping("/hello")` - handle message send to route: '/api/hello' (remember prefix!)
* `@SendTo("/topic/greetings")` - send to everyone who is subscribed to this topic

## How is the interaction

### JS
1. Click to connect to WebSocket route: '/gs-guide-websocket'
2. After connect to WebSocket auto subscribe to topic: '/topic/greetings' (it means to receive all messages that were sent to this topic)
3. Click "send" (send name) to route: '/app/hello' and return greeting message