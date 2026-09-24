package workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface GreetingWorkflow {
    @WorkflowMethod(name="Greeting")
    GreetingResult greet(GreetingInput input);
}
