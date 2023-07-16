package de.zent.augsburg;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.eventbridge.AmazonEventBridge;
import com.amazonaws.services.eventbridge.AmazonEventBridgeClient;
import com.amazonaws.services.eventbridge.model.PutEventsRequest;
import com.amazonaws.services.eventbridge.model.PutEventsRequestEntry;
import com.amazonaws.services.eventbridge.model.PutEventsResult;

public class EventPublisherMain {

    public static void main(String[] args) {

        // jut a comment line 12:51
        // second commetn line 12:30
        // third line 13:24
        // fourth line
        // fifth line
        
        AmazonEventBridge client = AmazonEventBridgeClient.builder()
                .withRegion(Regions.EU_CENTRAL_1)
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();

        System.setProperty("aws.accessKeyId", "");
        System.setProperty("aws.secretKey","");

        String src = "de.zent.maze";
        String detailType = "Berlin Incoming Event Notification";
        String busName = "maze-bus";

        putEvents(client, src, detailType, busName);
    }

    private static void putEvents(AmazonEventBridge client, String src, String detailType, String busName) {

        PutEventsRequestEntry requestEntry = new PutEventsRequestEntry();
        requestEntry.withSource(src)
                .withDetailType(detailType)
                //.withDetail("{ \"userId\": \"1234\", \"preference\": \"music\" }")
                .withDetail("{}")
                .withEventBusName(busName);

        PutEventsRequest req = new PutEventsRequest();
        req.withEntries(requestEntry);

        PutEventsResult result = client.putEvents(req);

        System.out.println(result);
    }
}
