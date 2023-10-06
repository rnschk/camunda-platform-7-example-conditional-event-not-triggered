package de.rnschk.workflow;

import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class WorkflowApplication {
    private static final Logger log = LoggerFactory.getLogger("de.rnschk.workflow.log");

    @Autowired
    private RuntimeService runtimeService;

    public static void main(String... args) {
        SpringApplication.run(WorkflowApplication.class, args);
    }

    @Scheduled(initialDelay = 1000L, fixedDelay = Long.MAX_VALUE)
    public void startProcessInstance() {
        var instance = runtimeService.createProcessInstanceByKey("demo").execute();
        log.info("process started: http://localhost:8080/camunda/app/cockpit/default/#/process-instance/{}", instance.getId());
    }

}
