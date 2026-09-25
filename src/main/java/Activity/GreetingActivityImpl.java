package Activity;

import contract.GreetingActivityResult;

public class GreetingActivityImpl implements GreetingActivity {
    @Override
    public GreetingActivityResult greet(String name) {
        return new GreetingActivityResult("Hello " + name);
    }
}
