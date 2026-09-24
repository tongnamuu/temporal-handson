package Activity;

public class GreetingActivityImpl implements GreetingActivity {
    @Override
    public String greet(String name) {
        return "Hello " + name;
    }
}
