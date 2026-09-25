package client;

import config.LabConfig;
import contract.GreetingActivityResult;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import workflow.GreetingInput;

public class GreetingClient {
    private static final Logger log = LoggerFactory.getLogger(GreetingClient.class);

    public static void main(String[] args) {
        String name = args.length > 0 ? args[0] : "Temporal";

        WorkflowServiceStubs service = WorkflowServiceStubs.newServiceStubs(
                WorkflowServiceStubsOptions.newBuilder().setTarget(LabConfig.ADDRESS).build()
        );
        try {
            WorkflowClient client = WorkflowClient.newInstance(service,
                    WorkflowClientOptions.newBuilder()
                            .setNamespace(LabConfig.NAMESPACE)
                            .build());
            WorkflowStub workflow = client.newUntypedWorkflowStub("Greeting",
                    WorkflowOptions.newBuilder()
                            .setTaskQueue(LabConfig.TASK_QUEUE)
                            .build());

            String workflowId = workflow.start(new GreetingInput(name)).getWorkflowId();
            GreetingActivityResult result = workflow.getResult(GreetingActivityResult.class);
            log.info("workflowId={}, result={}", workflowId, result.message());
        } finally {
            service.shutdown();
        }
    }
}
