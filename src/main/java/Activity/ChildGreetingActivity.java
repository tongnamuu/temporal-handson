package Activity;

import contract.ChildActivityResult;
import contract.GreetingActivityResult;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface ChildGreetingActivity {
    ChildActivityResult greetChild(GreetingActivityResult parentResult);
}
