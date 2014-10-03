package com.cookbook.cq.workflow;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bvaughn
 * Date: 10/3/14
 */
@Component
@Service
public class HelloWorkflow implements WorkflowProcess {
    protected final Logger log = LoggerFactory.getLogger(WorkflowProcess.class);

    @Property(value = "A Workflow process that outputs a message.")
    static final String DESCRIPTION = Constants.SERVICE_DESCRIPTION;
    @Property(value = "Cookbook")
    static final String VENDOR = Constants.SERVICE_VENDOR;
    @Property(value = "Hello Workflow Process")
    static final String LABEL="process.label";

    public static final String MODEL = "/etc/workflow/models/CookbookWorkflow/jcr:content/model";

    @Override public void execute(WorkItem workItem, WorkflowSession workflowSession,
        MetaDataMap metaDataMap) throws WorkflowException {

        try {
            log.info("Workflow started...");

            WorkflowData workflowData = workItem.getWorkflowData();

            //Payload is always a path
            String path = workflowData.getPayload().toString();

            String args = metaDataMap.get("PROCESS_ARGS", String.class);

            log.info("Workflow payload: " + path);

            log.info("Workflow args: " + args);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        log.info("Workflow complete...");
    }


}
