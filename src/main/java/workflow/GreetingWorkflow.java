package workflow;

import contract.GreetingActivityResult;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface GreetingWorkflow {
    @WorkflowMethod(name="Greeting")
    GreetingActivityResult greet(GreetingInput input);
}
