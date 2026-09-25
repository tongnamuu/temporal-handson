package workflow;

import Activity.ChildGreetingActivity;
import contract.ChildActivityResult;
import contract.GreetingActivityResult;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.time.Duration;

public class ChildGreetingWorkflowImpl implements ChildGreetingWorkflow {
    private static final Logger log = Workflow.getLogger(ChildGreetingWorkflowImpl.class);
    private static final long WORKER_PID = ProcessHandle.current().pid();

    private final ChildGreetingActivity activity = Workflow.newActivityStub(
            ChildGreetingActivity.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(10))
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build())
                    .build());

    @Override
    public ChildActivityResult greet(GreetingActivityResult parentResult) {
        log.info("Child Workflow: workerPid={}, workflowId={}, parentResult={}",
                WORKER_PID, Workflow.getInfo().getWorkflowId(), parentResult.message());
        return activity.greetChild(parentResult);
    }
}
