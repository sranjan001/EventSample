package io.pivotal.satya.scs.EventSample;

import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageHandler;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.util.AssertionErrors.assertEquals;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EventSampleApplication.class)
@DirtiesContext
public class EventSampleApplicationTests {

	@Autowired
	private Processor pipe;

	@Autowired
	private MessageCollector messageCollector;

	@Test
	@Ignore
	public void whenSendMessage_thenResponseShouldUpdateText() {
		pipe.input()
				.send(MessageBuilder.withPayload(new LogMessage("This is my message"))
						.build());

		Object payload = messageCollector.forChannel(pipe.output()).poll().getPayload();

		System.out.println(payload.toString());


		assertEquals("check message", "[1]: This is my message", payload.toString());
	}

}
