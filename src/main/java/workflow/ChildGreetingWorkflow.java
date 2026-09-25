package workflow;

import contract.ChildActivityResult;
import contract.GreetingActivityResult;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface ChildGreetingWorkflow {
    @WorkflowMethod(name = "ChildGreeting")
    ChildActivityResult greet(GreetingActivityResult parentResult);
}
