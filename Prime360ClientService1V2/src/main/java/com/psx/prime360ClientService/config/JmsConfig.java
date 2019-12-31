package com.psx.prime360ClientService.config;

import java.sql.SQLException;

import javax.jms.ConnectionFactory;
import javax.jms.QueueConnectionFactory;
import javax.sql.DataSource;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.web.client.RestTemplate;

import oracle.jdbc.pool.OracleDataSource;
import oracle.jms.AQjmsFactory;


@Configuration
@PropertySource(value = { "classpath:application.properties", "classpath:database.properties" })
public class JmsConfig {

	@Autowired
	private Environment environment;

	@Autowired
	ConnectionFactory connectionFactory;

	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(environment.getProperty("jms.url"));
		return connectionFactory;
	}

	@Bean
	public PooledConnectionFactory pooledConnection() {

		PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
		pooledConnectionFactory.setConnectionFactory(connectionFactory());
		pooledConnectionFactory.setMaxConnections(Integer.parseInt(environment.getProperty("jms.maxConnections")));
		pooledConnectionFactory.setMaximumActiveSessionPerConnection(
				Integer.parseInt(environment.getProperty("jms.maximumActiveSessionPerConnection")));
		return pooledConnectionFactory;
	}
	@Bean()
	public JmsTemplate jmsTemplate() {
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(pooledConnection());
		template.setSessionAcknowledgeMode(2);
		template.setDeliveryMode(2);
		return template;
	}

	@Bean
	public RestTemplate createRestTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setConcurrency("1-1");
		return factory;
	}

	@Bean(name = "dataSource2")
	DataSource dataSource2() throws SQLException {
		OracleDataSource dataSource = new OracleDataSource();

		dataSource.setUser(environment.getProperty("spring.datasource.username"));
		dataSource.setPassword(environment.getProperty("spring.datasource.password"));
		dataSource.setURL(environment.getProperty("spring.datasource.url"));
		return dataSource;
	}

	@Bean
	public QueueConnectionFactory connectionFactoryOracle() throws Exception {
		return AQjmsFactory.getQueueConnectionFactory(dataSource2());
	}
	@Primary
	@Bean(name = "jmsTemplate2")
	public JmsTemplate jmsTemplate2() throws Exception {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(connectionFactoryOracle());
		jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
		return jmsTemplate;
	}

	@Bean
	public JmsListenerContainerFactory<?> myJMSListenerFactory(QueueConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

		factory.setMessageConverter(jacksonJmsMessageConverter());
		configurer.configure(factory, connectionFactory);
		return factory;
	}

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);

		return converter;
	}

}
