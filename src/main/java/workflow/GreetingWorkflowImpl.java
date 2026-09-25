package workflow;

import Activity.GreetingActivity;
import contract.ChildActivityResult;
import contract.GreetingActivityResult;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.ChildWorkflowOptions;
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
        String workflowId = Workflow.getInfo().getWorkflowId();
        log.info("Greeting 처리: workerPid={}, workflowId={}", WORKER_PID, workflowId);
        GreetingActivityResult result = activity.greet(input.name());

        String childWorkflowId = workflowId + "-child";
        ChildGreetingWorkflow child = Workflow.newChildWorkflowStub(
                ChildGreetingWorkflow.class,
                ChildWorkflowOptions.newBuilder()
                        .setWorkflowId(childWorkflowId)
                        .build());
        log.info("Child 호출: workerPid={}, workflowId={}, childWorkflowId={}",
                WORKER_PID, workflowId, childWorkflowId);
        ChildActivityResult childResult = child.greet(result);
        log.info("Child 완료: workerPid={}, workflowId={}, childWorkflowId={}, childResult={}",
                WORKER_PID, workflowId, childWorkflowId, childResult.message());
        return new GreetingActivityResult(childResult.message());
    }
}
