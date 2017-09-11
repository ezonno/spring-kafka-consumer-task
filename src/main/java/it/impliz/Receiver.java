package it.impliz;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.SubscribableChannel;

import java.util.List;

@EnableBinding(Receiver.Sink.class)
public class Receiver {


    // Sink application definition
    @StreamListener(Sink.INPUT)
    public void receive(List<Message> fooMessages) {
        System.out.println("******************");
        System.out.println("At the Receiver");
        System.out.println("******************");
        System.out.println("Received messages " + fooMessages);
    }

    public interface Sink {
        String INPUT = "receiverIn";

        @Input(INPUT)
        SubscribableChannel receiverIn();
    }
}
