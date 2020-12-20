package com.example.rabbitmqdemo;

import io.jaegertracing.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@RestController
public class RabbitmqdemoApplication {

    static final String topicExchangeName = "spring-boot-exchange";

    static final String queueName = "spring-boot";

    @Autowired
    private RabbitTemplate rabbitTemplate;

//	@Bean
//	Queue queue() {
//		return new Queue(queueName, false);
//	}
//
//	@Bean
//	TopicExchange exchange() {
//		return new TopicExchange(topicExchangeName);
//	}
//
//	@Bean
//	Binding binding(Queue queue, TopicExchange exchange) {
//		return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
//	}

//	@Bean
//	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//											 MessageListenerAdapter listenerAdapter) {
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//		container.setConnectionFactory(connectionFactory);
//		container.setQueueNames(queueName);
//		container.setMessageListener(listenerAdapter);
//		return container;
//	}
//
//	@Bean
//	MessageListenerAdapter listenerAdapter(Receiver receiver) {
//		return new MessageListenerAdapter(receiver, "receiveMessage");
//	}

//    @Bean
//    public io.opentracing.Tracer initTracer() {
//        Configuration.SamplerConfiguration samplerConfig = new
//                Configuration.SamplerConfiguration()
//                .withType("const").withParam(1);
//        return Configuration.fromEnv("relay-service")
//                .withSampler(samplerConfig).getTracer();
//    }


    public static void main(String[] args) {
        SpringApplication.run(RabbitmqdemoApplication.class, args);
    }


    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestHeader Map<String, String> headers) {

        headers.forEach((key, value) -> {
            System.out.println(String.format("Header '%s' = %s", key, value));
        });

        rabbitTemplate.convertAndSend(RabbitmqdemoApplication.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
//		rabbitTemplate.convertAndSend(RabbitmqdemoApplication.topicExchangeName, "foo", "Hello from RabbitMQ!");

        return ResponseEntity.ok("success");
    }

    @PostMapping("/receive")
    public ResponseEntity<String> receiveMessage(@RequestBody String message, @RequestHeader Map<String, String> headers){

        headers.forEach((key, value) -> {
            System.out.println(String.format("Header '%s' = %s", key, value));
        });

        System.out.println("Receive message API :" +message);

        return ResponseEntity.ok("success");
    }

}
