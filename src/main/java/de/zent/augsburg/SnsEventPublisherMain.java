package de.zent.augsburg;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

import java.util.Date;

public class SnsEventPublisherMain {

    public static void main(String[] args) {

        String message = "Test message by Alex. Time: " + new Date().toString();
        // Name: maze-frankfurt-one
        String topicArn = "arn:aws:sns:eu-central-1:543835060909:maze-frankfurt-one";
        Region region = Region.EU_CENTRAL_1;
        SnsClient snsClient = SnsClient.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        pubTopic(snsClient, message, topicArn);
        snsClient.close();
    }

    private static void pubTopic(SnsClient snsClient, String message, String topicArn) {

        try {
            PublishRequest request = PublishRequest.builder()
                    .message(message)
                    .topicArn(topicArn)
                    .build();

            PublishResponse result = snsClient.publish(request);
            System.out.println(result.messageId() + " Message sent. Status is " + result.sdkHttpResponse().statusCode());

        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

}
