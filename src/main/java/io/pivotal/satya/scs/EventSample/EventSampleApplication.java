package io.pivotal.satya.scs.EventSample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.SendTo;

@SpringBootApplication
@EnableBinding(Processor.class)
public class EventSampleApplication {

	//@Autowired
	//private MyProcessor myProcessor;

	@Autowired
	private Processor processor;

	public static void main(String[] args) {
		SpringApplication.run(EventSampleApplication.class, args);
	}

	@Bean
	public MessageConverter providesTextPlainMessageConverter() {

		return new TextPlainMessageConverter();
	}

	@StreamListener(Processor.INPUT)
	@SendTo(Processor.OUTPUT)
	public LogMessage enrichLogMessage(LogMessage log) {
		System.out.println(">>>> Got >>> " + log.getMessage());
		return new LogMessage(String.format("[1]: %s", log.getMessage()));
	}


	@StreamListener(Processor.OUTPUT)
	public void enrichLogMessage1(LogMessage log) {
		System.out.println(">>>> Got >>> $$$$$$$$$$$$$$$$ " + log.getMessage());
		processor.input().send(MessageBuilder.withPayload(new LogMessage("Dummy message")).build());
	}

	//	@StreamListener(Processor.INPUT)
	//	@SendTo(Processor.OUTPUT)
	//	public LogMessage enrichLogMessage(LogMessage log) {
	//		return new LogMessage(String.format("[1]: %s", log.getMessage()));
	//	}

	//	@StreamListener(MyProcessor.INPUT)
	//	public void enrichLogMessage(int val) {
	//		if(val < 10) {
	//			myProcessor.anOutput().send(message(val));
	//		} else {
	//			myProcessor.anotherOutput().send(message(val));
	//		}
	//	}

	private static final <T> Message<T> message(T val) {
		return MessageBuilder.withPayload(val).build();
	}

	//	@StreamListener(
	//			target = MyProcessor.INPUT,
	//			condition = "payload < 10")
	//	public void routeValueToAnOutput(Integer val) {
	//		myProcessor.anOutput().send(message(val));
	//	}
	//
	//	@StreamListener(
	//			target = MyProcessor.INPUT,
	//			condition = "payload > 10"
	//	)
	//	public void routeValueToAnotherOutput(Integer val) {
	//		myProcessor.anotherOutput().send(message(val));
	//	}
	//
	//	@Bean
	//	public MessageConverter providesTextPlainMessageConverter() {
	//		return new TextPlainMessageConverter();
	//	}

}
