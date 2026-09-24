package Activity;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface GreetingActivity {
    String greet(String name);
}
