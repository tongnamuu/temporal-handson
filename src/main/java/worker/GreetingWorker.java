package worker;

import Activity.GreetingActivityImpl;
import config.LabConfig;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import workflow.GreetingWorkflowImpl;

import java.util.concurrent.CountDownLatch;

public class GreetingWorker {
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

        worker.registerWorkflowImplementationTypes(GreetingWorkflowImpl.class);
        worker.registerActivitiesImplementations(new GreetingActivityImpl());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            factory.shutdownNow();
            workflowServiceStubs.shutdown();
        }));

        factory.start();
        System.out.println("Worker 실행중 : " + LabConfig.TASK_QUEUE);
        new CountDownLatch(1).await();
    }
}
