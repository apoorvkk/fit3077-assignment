package edu.monash.it.fit3077.vjak.backendmonitor;

import edu.monash.it.fit3077.vjak.observer.Subject;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.CountDownLatch;

public class HealthMeasurementListener extends Subject {
    private MeasurementEvent currentEvent;

    public HealthMeasurementListener() {
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        CountDownLatch latch = new CountDownLatch(1);
        StompSessionHandler sh = new HealthMeasurementSocketSessionHandler(this, latch);
        stompClient.connect("ws://localhost:8080/", sh); // Insert proper url
        stompClient.setAutoStartup(true);
        stompClient.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public MeasurementEvent getDataReceived() {
        return this.currentEvent;
    }

    public void dataReceived(MeasurementEvent payload) {
        this.currentEvent = payload;
        this.notifyObservers();
    }
}
