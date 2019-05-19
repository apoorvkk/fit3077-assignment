package edu.monash.it.fit3077.vjak.backendmonitor;

import edu.monash.it.fit3077.vjak.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.concurrent.CountDownLatch;

/*
This class is responsible for listening to low level specific socket events and subscribing to the relevant client topic which
will receive events from the backend.
 */
class HealthMeasurementSocketSessionHandler extends StompSessionHandlerAdapter {
    private final HealthMeasurementListener healthMeasurementListener;
    private final CountDownLatch latch;
    private final Logger logger;

    /**
     * Initialize important variabels needed for web socket communication.
     * @param hl: health measurement listener used to send web socket data into the application codebase.
     * @param latch: used to notify caller code that the frontend has connected successfully with the backend via websockets.
     */
    HealthMeasurementSocketSessionHandler(HealthMeasurementListener hl, CountDownLatch latch) {
        super();
        this.logger = LogManager.getLogger(HealthMeasurementSocketSessionHandler.class);
        this.healthMeasurementListener = hl;
        this.latch = latch;
    }

    /**
     * Logs any websocket errors.
     */
    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Error: ", exception);
    }

    /**
     * Event handler once system has connected to backend.
     */
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.info("Connected");
        session.subscribe("/topic/" + Constant.clientId, this); // Subscribe to the necessary topic to receive events.
        this.latch.countDown();
    }

    /**
     * Logs any transport related websocket errors.
     */
    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        logger.error("Transport error: ", exception);
    }

    /**
     * This is where websocket events come first. Here, we take the raw payload and wrap it in the relevent business model.
     * Then, we send the event to the health measurement listener which will broadcast the event to all of its observers.
     * @param headers: websocket headers.
     * @param payload: the actual event data sent from the backend.
     */
    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        logger.info("Received message");

        MeasurementEventModel me;
        LinkedHashMap<String, String> data = (LinkedHashMap<String, String>) payload;
        if (data.get("type").equals(Constant.cholesterol) || data.get("type").equals(Constant.oralTemperature) || data.get("type").equals(Constant.systolicBloodPressure) || data.get("type").equals(Constant.diastolicBloodPressure)) {
            me = new QuantityMeasurmentEventModel(data);
        } else {
            me = new QualityMeasurementEventModel(data);
        }
        this.healthMeasurementListener.dataReceived(me); // notifies listener about the event.
    }

    /**
     * Used to convert raw data to a meaningful object that the application can consume.
     * @param headers: websocket headers.
     * @return LinkedHashMap class.
     */
    @Override
    public Type getPayloadType(StompHeaders headers) {
        return java.util.LinkedHashMap.class;
    }
}
