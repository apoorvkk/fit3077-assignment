package health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Scheduled;

@Controller
public class GreetingsController {

    String name = "NULL";
    static final Logger log = LoggerFactory.getLogger(GreetingsController.class);

    @Autowired
    SimpMessagingTemplate template;

    @MessageMapping("/hello")
    public void setName(HelloMessage message) {
        name = message.getName();
        log.info("Name set to: " + name);
    }

    @Scheduled(fixedRate = 2000)
    public void validateSendBack() {
        if (!name.equals("NULL")) {
            log.info("Sending to topic...");
            this.template.convertAndSend("/topic/greetings",new Greetings("Hello, " + HtmlUtils.htmlEscape(name) + "!"));
            //greeting();
        }
        

    }

    //@SendTo("/topic/greetings")
    public Greetings greeting() {
        //Thread.sleep(1000); // simulated delay
        return new Greetings("Hello, " + HtmlUtils.htmlEscape(name) + "!");
    }

}