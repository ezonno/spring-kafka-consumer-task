package it.impliz;


import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.messaging.Message;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;

import java.util.List;

//@EnableBinding(Receiver.Sink.class)
public class Receiver implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    // Sink application definition
    @StreamListener(Sink.INPUT)
    public void receive(String fooMessage) {
        System.out.println("******************");
        System.out.println("At the Receiver");
        System.out.println("******************");


        System.out.println("Received messages " + fooMessage);
//
        SpringApplication.exit(applicationContext);
    }

    public interface Sink {
        String INPUT = "receiverIn";

        @Input(INPUT)
        SubscribableChannel receiverIn();
    }
}
