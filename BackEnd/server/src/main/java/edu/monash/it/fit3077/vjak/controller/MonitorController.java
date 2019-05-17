package edu.monash.it.fit3077.vjak.controller;

import edu.monash.it.fit3077.vjak.model.MonitorEventModel;
import edu.monash.it.fit3077.vjak.model.PatientMonitorModel;
import edu.monash.it.fit3077.vjak.model.PatientMonitorCollectionModel;
import edu.monash.it.fit3077.vjak.model.RequestMonitorInfoModel;
import edu.monash.it.fit3077.vjak.observer.MonitorControllerObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
This class is responsible for registering/deregistering clients in order to listen to socket events that hold
patient monitor data. It also observes monitor data and sends it down to the relevant connected client.
 */
@RestController
public class MonitorController implements MonitorControllerObserver {
    private final PatientMonitorCollectionModel patientMonitorCollectionModel;
    @Autowired
    private SimpMessagingTemplate template;

    public MonitorController() {
        super();
        this.patientMonitorCollectionModel = new PatientMonitorCollectionModel();
    }

    @RequestMapping(value = "/MonitorRegister", method = RequestMethod.POST) // clients must make a POST request to /MonitorRegister.
    public void register(@RequestBody RequestMonitorInfoModel requestMonitorInfoModel) {
        System.out.println("register here");
        PatientMonitorModel pm = this.patientMonitorCollectionModel.addMonitor(requestMonitorInfoModel);
        pm.attach(this); // Make sure to observe any updated data for this particular patient monitor.
    }

    @RequestMapping(value = "/MonitorDeregister", method = RequestMethod.POST) // clients must make a POST request to /MonitorDeregister.
    public void deregister(@RequestBody RequestMonitorInfoModel requestMonitorInfoModel) {
        this.patientMonitorCollectionModel.deleteMonitor(requestMonitorInfoModel);

    }

    public void update(PatientMonitorModel monitor, String clientId) {
        // Send monitor event only to the specified client.
        this.template.convertAndSend("/topic/" + clientId, monitor.serialize()); // a client should listen into /topic/{their client id}
    }
}
