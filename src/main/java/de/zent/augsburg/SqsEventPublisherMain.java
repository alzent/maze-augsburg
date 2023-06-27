package de.zent.augsburg;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

import java.util.Date;

public class SqsEventPublisherMain {

    private static final Region REGION = Region.EU_CENTRAL_1;

    public static void main(String[] args) {
        String queueName = "maze-stuttgart-one";
        sendMsg(queueName);
    }

    private static void sendMsg(String queueName){

        String message = "Simple queue message by Alex. time: " + new Date().toString();
        SqsClient sqsClient = SqsClient.builder()
                .region(REGION)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        sendMsgIntern(sqsClient, queueName, message);
        sqsClient.close();
    }

    public static void sendMsgIntern(SqsClient sqsClient, String queueName, String message) {

        try {
            CreateQueueRequest request = CreateQueueRequest.builder()
                    .queueName(queueName)
                    .build();
            sqsClient.createQueue(request);

            GetQueueUrlRequest getQueueRequest = GetQueueUrlRequest.builder()
                    .queueName(queueName)
                    .build();

            String queueUrl = sqsClient.getQueueUrl(getQueueRequest).queueUrl();
            SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(message)
                    .delaySeconds(5)
                    .build();

            sqsClient.sendMessage(sendMsgRequest);

        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

}
