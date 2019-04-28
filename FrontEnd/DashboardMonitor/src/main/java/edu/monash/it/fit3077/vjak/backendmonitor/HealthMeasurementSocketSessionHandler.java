package edu.monash.it.fit3077.vjak.backendmonitor;

import edu.monash.it.fit3077.vjak.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.concurrent.CountDownLatch;

public class HealthMeasurementSocketSessionHandler extends StompSessionHandlerAdapter {
    private HealthMeasurementListener healthMeasurementListener;
    private CountDownLatch latch;
    private Logger logger;

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
        session.subscribe("/topic/" + Constant.clientId, this); // get ip
        this.latch.countDown();
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        logger.error("Transport error: ", exception);
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        logger.info("Received message");
        MeasurementEvent me = (MeasurementEvent) payload;
        this.healthMeasurementListener.dataReceived(me);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return MeasurementEvent.class;
    }
}
