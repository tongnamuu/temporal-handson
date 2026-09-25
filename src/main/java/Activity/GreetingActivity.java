package Activity;

import contract.GreetingActivityResult;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface GreetingActivity {
    GreetingActivityResult greet(String name);
}
