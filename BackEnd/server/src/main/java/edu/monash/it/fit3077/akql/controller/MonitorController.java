package edu.monash.it.fit3077.akql.controller;

import edu.monash.it.fit3077.akql.model.PatientMonitorModel;
import edu.monash.it.fit3077.akql.model.PatientMonitorCollectionModel;
import edu.monash.it.fit3077.akql.model.RequestMonitorInfoModel;
import edu.monash.it.fit3077.akql.observer.MonitorControllerObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is responsible for registering/deregistering clients in order to listen to socket events that hold
 * patient monitor data. It also observes monitor data and sends it down to the relevant connected client.
 */
@RestController
public class MonitorController implements MonitorControllerObserver {
    private final PatientMonitorCollectionModel patientMonitorCollectionModel;
    @Autowired
    private SimpMessagingTemplate template;

    /**
     * Initialize the monitor controller.
     */
    public MonitorController() {
        super();
        this.patientMonitorCollectionModel = new PatientMonitorCollectionModel();
    }

    /**
     * Register a patient's health measurement for monitoring.
     * @param requestMonitorInfoModel: information on the patient and the health measurement to be monitored.
     */
    @RequestMapping(value = "/MonitorRegister", method = RequestMethod.POST) // clients must make a POST request to /MonitorRegister.
    public void register(@RequestBody RequestMonitorInfoModel requestMonitorInfoModel) {
        PatientMonitorModel pm = this.patientMonitorCollectionModel.addMonitor(requestMonitorInfoModel);
        pm.attach(this); // Make sure to observe any updated data for this particular patient monitor.
    }

    /**
     * Deregister a patient's health measurement for monitoring.
     * @param requestMonitorInfoModel: information on the patient and the health measurement to be unmonitored.
     */
    @RequestMapping(value = "/MonitorDeregister", method = RequestMethod.POST) // clients must make a POST request to /MonitorDeregister.
    public void deregister(@RequestBody RequestMonitorInfoModel requestMonitorInfoModel) {
        this.patientMonitorCollectionModel.deleteMonitor(requestMonitorInfoModel);

    }

    /**
     * Sends update signal to the front-end (the views) to update using new health measurement data.
     * @param monitor: the health measurement monitor that is going to have its data sent.
     * @param clientId: the id of the client which this health measurement data is going to be sent to.
     */
    public void update(PatientMonitorModel monitor, String clientId) {
        // Send monitor event only to the specified client.
        this.template.convertAndSend("/topic/" + clientId, monitor.serialize()); // a client should listen into /topic/{their client id}
    }
}
