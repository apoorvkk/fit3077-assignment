package edu.monash.it.fit3077.vjak.controller;

import edu.monash.it.fit3077.vjak.model.MonitorEventModel;
import edu.monash.it.fit3077.vjak.model.PatientMonitor;
import edu.monash.it.fit3077.vjak.model.PatientMonitorCollection;
import edu.monash.it.fit3077.vjak.model.RequestMonitorInfo;
import edu.monash.it.fit3077.vjak.observer.MonitorControllerObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonitorController implements MonitorControllerObserver {
    private PatientMonitorCollection patientMonitorCollection;
    @Autowired
    private SimpMessagingTemplate template;

    public MonitorController() {
        super();
        this.patientMonitorCollection = new PatientMonitorCollection();
    }

    @RequestMapping(value = "/MonitorRegister", method = RequestMethod.POST)
    public void register(@RequestBody RequestMonitorInfo requestMonitorInfo) {
        PatientMonitor pm = this.patientMonitorCollection.addMonitor(requestMonitorInfo);
        pm.attach(this);
    }

    @RequestMapping(value = "/MonitorDeregister", method = RequestMethod.POST)
    public void deregister(@RequestBody RequestMonitorInfo requestMonitorInfo) {
        this.patientMonitorCollection.deleteMonitor(requestMonitorInfo);

    }

    public void update(PatientMonitor monitor, String clientId) {
        this.template.convertAndSend("/topic/" + clientId, new MonitorEventModel(monitor));
    }
}
