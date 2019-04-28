package health;

import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class HealthController {
    
    Map<String, Monitor> monitors = new HashMap<String, Monitor>();
    //ArrayList<String> ips;
    SimpMessagingTemplate template;
    static final Logger log = LoggerFactory.getLogger(HealthController.class);

    @MessageMapping("/register")
    public void register(SimpMessageHeaderAccessor ha, Monitor monitor) {
        String ip = (String) ha.getSessionAttributes().get("ip");
        monitors.put(ip, monitor);
        log.info("Added ip: "+ ip);
    }

    @MessageMapping("/deregister")
    public void deregister(SimpMessageHeaderAccessor ha) {
        monitors.remove((String) ha.getSessionAttributes().get("ip"));
    }
    
    @Scheduled(fixedRate = 3000)    //TEMP
    public void sendToClients() {
        if (monitors.size() > 0) {
            for (String ip : monitors.keySet()) {
                template.convertAndSend("/topic/" + ip, monitors.get(ip));
            }
        }
    }
}