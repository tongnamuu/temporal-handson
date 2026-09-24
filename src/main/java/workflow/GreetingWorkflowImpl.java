package workflow;

public class GreetingWorkflowImpl implements GreetingWorkflow {
    @Override
    public GreetingResult greet(GreetingInput input) {
        return new GreetingResult(
               input.name() + " result"
        );
    }
}
