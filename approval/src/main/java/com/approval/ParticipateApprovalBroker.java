package com.approval;

import com.approval.solace.MessageSender;
import com.approval.solace.SolaceBrokerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.TextMessage;

/**
 * @author Ocean Liang
 * @date 4/25/2019
 */

@Component
@Slf4j
public class ParticipateApprovalBroker {
    private final String LISTENED_QUEUE = "ARS/ACTIVITY/EVT";
    private static final String DEFAULT_CONCURRENCY = "10";
    private static final String QUEUE_LISTENER_FACTORY = "queueListenerFactory";
    private static final String OUTPUT_TOPIC = "ARS/APPROVAL";

    @Resource
    private MessageSender messageSender;

//    @Retryable(value = {SolaceBrokerException.class}, backoff = @Backoff(random = true, multiplier = 0))
    @JmsListener(destination = LISTENED_QUEUE, containerFactory = QUEUE_LISTENER_FACTORY, concurrency = DEFAULT_CONCURRENCY)
    public void processMessage(TextMessage originalMessage) throws Exception {
        System.out.println("receive from queue");
        System.out.println(originalMessage.getText());
        messageSender.sendMessageToTopic(OUTPUT_TOPIC,"send to activity");
    }
}
