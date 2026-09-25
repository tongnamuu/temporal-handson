package workflow;

import Activity.GreetingActivity;
import contract.GreetingActivityResult;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

import java.time.Duration;

public class GreetingWorkflowImpl implements GreetingWorkflow {
    private static final Logger log = Workflow.getLogger(GreetingWorkflowImpl.class);
    private static final long WORKER_PID = ProcessHandle.current().pid();
    private final GreetingActivity activity = Workflow.newActivityStub(
            GreetingActivity.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(10))
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build())
                    .build());

    @Override
    public GreetingActivityResult greet(GreetingInput input) {
        log.info("Greeting 처리: workerPid={}, workflowId={}",
                WORKER_PID, Workflow.getInfo().getWorkflowId());
        return activity.greet(input.name());
    }
}
