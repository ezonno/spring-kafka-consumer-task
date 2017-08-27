package it.impliz;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.integration.annotation.ReleaseStrategy;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.store.SimpleMessageGroup;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;

import java.util.ArrayList;
import java.util.List;

@EnableBinding(Collector.SampleCollector.class)
public class Collector {

    private int correlationId = 0;

    @ReleaseStrategy
    public boolean isReadytoRelease(List<Message> messages) {
        if (messages.size() == 1000) {
            correlationId++;
            return true;
        }

        return false;
    }

    @CorrelationStrategy
    public String correlateBy(Message message) {
        return "foo" + correlationId;
    }

    @Aggregator(inputChannel = SampleCollector.INPUT, outputChannel = SampleCollector.OUTPUT, discardChannel = "nullChannel")
    public String receive(List<Message> messages) {

        System.out.println("******************");
        System.out.println("At the collector");
        System.out.println("******************");
        System.out.print(messages);

        SimpleMessageGroup msgrp = new SimpleMessageGroup(correlationId);

        for (Message message : messages) {
            Message newMessage = new GenericMessage(message.getPayload());
            msgrp.add(newMessage);
        }

        return msgrp.toString();
    }


    public interface SampleCollector {
        String INPUT = "collectorIn";
        String OUTPUT = "collectorOut";

        @Input(INPUT)
        SubscribableChannel collectorIn();

        @Output(OUTPUT)
        MessageChannel collectorOut();
    }

}
