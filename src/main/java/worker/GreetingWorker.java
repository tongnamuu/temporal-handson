package worker;

import Activity.ChildGreetingActivityImpl;
import Activity.GreetingActivityImpl;
import config.LabConfig;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import workflow.ChildGreetingWorkflowImpl;
import workflow.GreetingWorkflowImpl;

import java.util.concurrent.CountDownLatch;

public class GreetingWorker {
    private static final Logger log = LoggerFactory.getLogger(GreetingWorker.class);

    public static void main(String[] args) throws InterruptedException {
        WorkflowServiceStubs workflowServiceStubs = WorkflowServiceStubs.newServiceStubs(
                 WorkflowServiceStubsOptions.newBuilder().setTarget(LabConfig.ADDRESS).build()
        );
        WorkflowClient workflowClient = WorkflowClient.newInstance(workflowServiceStubs,
                WorkflowClientOptions.newBuilder()
                        .setNamespace(LabConfig.NAMESPACE)
                        .build());
        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
        Worker worker = factory.newWorker(LabConfig.TASK_QUEUE);

        worker.registerWorkflowImplementationTypes(
                GreetingWorkflowImpl.class,
                ChildGreetingWorkflowImpl.class);
        worker.registerActivitiesImplementations(
                new GreetingActivityImpl(),
                new ChildGreetingActivityImpl());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            factory.shutdownNow();
            workflowServiceStubs.shutdown();
        }));

        factory.start();
        log.info("Main Worker 실행중: taskQueue={}, workerPid={}",
                LabConfig.TASK_QUEUE, ProcessHandle.current().pid());
        new CountDownLatch(1).await();
    }
}
