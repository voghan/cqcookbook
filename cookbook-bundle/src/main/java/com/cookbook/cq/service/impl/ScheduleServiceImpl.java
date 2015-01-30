package com.cookbook.cq.service.impl;

import com.cookbook.cq.service.ScheduleService;
import com.cookbook.cq.workflow.HelloWorkflow;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowService;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.model.WorkflowModel;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;


@Service
@Component
@Properties({
    @Property(label="Quartz Cron Expression",description="Quartz Scheduler specific cron expression. Do not put unix cron expression",name="scheduler.expression", value="0 0/5 * * * ?"),
    @Property(
        label="Allow concurrent executions",
        description="Allow concurrent executions of this Scheduled Service",
        name="scheduler.concurrent",
        boolValue=false,
        propertyPrivate=true
    ),
    @Property(label="Job Enabled?",description="Turn on or off job",
        name="schedule.service.job.enabled",
        boolValue=false
    )
})
public class ScheduleServiceImpl implements ScheduleService, Runnable {
    /** Default log. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private WorkflowService workflowService;

    public void run() {

        log.info("*********************** I'm a sample job that runs every five minutes");
        startWorkflow(HelloWorkflow.MODEL, "/content/usergenerated/cookbook");
    }

    public void startWorkflow(String model, String payload) {
        log.info("startWorkflow... ");
        log.info("... model " + model);
        log.info("... payload " + payload);

        try {
            ResourceResolver resourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(
                null);

            Session session = resourceResolver.adaptTo(Session.class);

            WorkflowSession workflowSession = workflowService.getWorkflowSession(session);

            WorkflowModel workflowModel = workflowSession.getModel(model);

            WorkflowData workflowData = workflowSession.newWorkflowData("JCR_PATH", payload);

            workflowSession.startWorkflow(workflowModel, workflowData);

        } catch (LoginException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        } catch (WorkflowException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }

        log.info("startWorkflow... ending");

    }
}
