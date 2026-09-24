package workflow;

import io.temporal.workflow.Workflow;
import org.slf4j.Logger;

public class GreetingWorkflowImpl implements GreetingWorkflow {
    private static final Logger log = Workflow.getLogger(GreetingWorkflowImpl.class);
    private static final long WORKER_PID = ProcessHandle.current().pid();

    @Override
    public GreetingResult greet(GreetingInput input) {
        log.info("Greeting 처리: workerPid={}, workflowId={}",
                WORKER_PID, Workflow.getInfo().getWorkflowId());
        return new GreetingResult(
               input.name() + " result"
        );
    }
}
