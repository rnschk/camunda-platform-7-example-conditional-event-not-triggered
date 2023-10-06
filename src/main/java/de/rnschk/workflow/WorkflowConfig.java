package de.rnschk.workflow;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.camunda.bpm.engine.variable.Variables.createVariables;

@Configuration
public class WorkflowConfig {
    private static final Logger log = LoggerFactory.getLogger("de.rnschk.workflow.log");

    @Bean
    @ExternalTaskSubscription("bpmn-error-1")
    public ExternalTaskHandler bpmnErrorExternalTaskHandler1() {
        return (task, service) -> {
            log.info("execute task 1");
            service.handleBpmnError(task, "error code", "error message",
              createVariables().putValue("foo", "Hallo Task 1"));
        };
    }

    @Bean
    @ExternalTaskSubscription("bpmn-error-2")
    public ExternalTaskHandler bpmnErrorExternalTaskHandler2() {
        return (task, service) -> {
            log.info("execute task 2");
            service.handleBpmnError(task, "error code", "error message",
              createVariables().putValue("foo", "Hallo Task 2"));
        };
    }

    @Bean
    public JavaDelegate logger() {
        return (execution) -> {
            var bar = execution.getVariable("foo");
            log.info("execute logger, message='{}'", bar);
        };
    }

}
