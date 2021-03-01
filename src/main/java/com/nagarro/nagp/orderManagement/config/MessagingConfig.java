package com.nagarro.nagp.orderManagement.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

	public static final String QUEUE = "orderManagementQueue";
	public static final String EXCHANGE = "orderManagementExchange";
	public static final String ROUTINGKEY = "orderManagementRoutingKey";

	@Bean
	public Queue queue() {
		return new Queue("QUEUE");
	}

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange("EXCHANGE");

	}

	@Bean
	public Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("ROUTINGKEY");
	}

	@Bean
	public MessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}

}
