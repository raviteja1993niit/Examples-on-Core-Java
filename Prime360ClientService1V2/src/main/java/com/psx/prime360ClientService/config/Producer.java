package com.psx.prime360ClientService.config;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;


@Component
public class Producer {
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);

	@Autowired
	@Qualifier(value ="jmsTemplate2")
	JmsTemplate jmsTemplate;

	public void sendMessage(final String queueName, final String message) {

		logger.info("Sending Message " + queueName);
		jmsTemplate.send(queueName, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = null;
				textMessage = session.createTextMessage(message);

				return textMessage;
			}
		});
	}


}
