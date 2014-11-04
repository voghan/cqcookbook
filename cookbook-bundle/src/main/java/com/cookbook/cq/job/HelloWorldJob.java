package com.cookbook.cq.job;

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
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;

@Service
@Component
@Properties({
    @Property(label="Quartz Cron Expression",description="Quartz Scheduler specific cron expression. Do not put unix cron expression",name="scheduler.expression", value="0 0 2 * * ?"),
    @Property(
        label="Allow concurrent executions",
        description="Allow concurrent executions of this Scheduled Service",
        name="scheduler.concurrent",
        boolValue=false,
        propertyPrivate=true
    ),
    @Property(label="Job Enabled?",description="Turn on or off job",
        name="text4babies.job.enabled",
        boolValue=false
    )
})
public class HelloWorldJob implements Runnable {
    protected final Logger log = LoggerFactory.getLogger(HelloWorldJob.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private WorkflowService workflowService;

    @Reference
    private SlingSettingsService slingSettingsService;
    @Reference
    private ConfigurationAdmin configurationAdmin;

    @Override public void run() {

        try {

            Configuration envConfig = configurationAdmin.getConfiguration("com.cookbook.cq.common");
            String payload = (String) envConfig.getProperties().get("cookbook.wf.payload");

            if (slingSettingsService.getRunModes().contains("author") ) {

                startWorkflow(HelloWorkflow.MODEL, payload);
            }

        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
    }

    private void startWorkflow(String model, String payload) {

        try {
            ResourceResolver resourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(
                null);

            Session session = resourceResolver.adaptTo(Session.class);

            WorkflowSession workflowSession = workflowService.getWorkflowSession(session);

            WorkflowModel workflowModel = workflowSession.getModel(model);

            WorkflowData workflowData = workflowSession.newWorkflowData("JCR_PATH", payload);

            workflowSession.startWorkflow(workflowModel, workflowData);

        } catch (LoginException e) {
            log.error(e.getMessage(), e);
        } catch (WorkflowException e) {
            log.error(e.getMessage(), e);
        }
    }
}
