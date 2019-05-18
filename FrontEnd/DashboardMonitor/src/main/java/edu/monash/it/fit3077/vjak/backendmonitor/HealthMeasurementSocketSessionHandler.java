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

    public HealthMeasurementSocketSessionHandler(HealthMeasurementListener hl, CountDownLatch latch) {
        super();
        this.logger = LogManager.getLogger(HealthMeasurementSocketSessionHandler.class);
        this.healthMeasurementListener = hl;
        this.latch = latch;
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Error: ", exception);
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.info("Connected");
        session.subscribe("/topic/" + Constant.clientId, this); // Subscribe to the necessary topic to receive events.
        this.latch.countDown();
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        logger.error("Transport error: ", exception);
    }

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

    @Override
    public Type getPayloadType(StompHeaders headers) {
        // Used to convert raw data to a meaningful object that the application can consume.
        return java.util.LinkedHashMap.class;
    }
}
