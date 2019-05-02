package edu.monash.it.fit3077.vjak.backendmonitor;

import edu.monash.it.fit3077.vjak.observer.Subject;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.CountDownLatch;

/*
This class is responsible for listening to socket events coming from the backend server that are related to patient monitors
(eg. patient 20 has a cholesterol level of 194.955 mg/dl). Once it is receives an event, it will notify all connected
observers using the Observer design pattern.
 */
public class HealthMeasurementListener extends Subject {
    private MeasurementEventModel currentEvent;

    public HealthMeasurementListener() {
        // Initialize web socket client.
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        CountDownLatch latch = new CountDownLatch(1);
        StompSessionHandler sh = new HealthMeasurementSocketSessionHandler(this, latch);
        stompClient.connect("ws://localhost:8080/polling-socket", sh);
        stompClient.setAutoStartup(true);
        stompClient.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public MeasurementEventModel getDataReceived() {
        return this.currentEvent;
    }

    void dataReceived(MeasurementEventModel payload) {
        this.currentEvent = payload;
        this.notifyObservers();
    }
}
