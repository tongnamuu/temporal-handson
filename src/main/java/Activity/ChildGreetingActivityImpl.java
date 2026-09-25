package Activity;

import contract.ChildActivityResult;
import contract.GreetingActivityResult;
import io.temporal.activity.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChildGreetingActivityImpl implements ChildGreetingActivity {
    private static final Logger log = LoggerFactory.getLogger(ChildGreetingActivityImpl.class);

    @Override
    public ChildActivityResult greetChild(GreetingActivityResult parentResult) {
        log.info("Child Activity: workerPid={}, workflowId={}, parentResult={}",
                ProcessHandle.current().pid(),
                Activity.getExecutionContext().getInfo().getWorkflowId(),
                parentResult.message());
        return new ChildActivityResult("Child: " + parentResult.message());
    }
}
