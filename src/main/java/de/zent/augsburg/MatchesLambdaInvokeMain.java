package de.zent.augsburg;

import org.json.JSONObject;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import software.amazon.awssdk.services.lambda.model.LambdaException;

public class MatchesLambdaInvokeMain {

    public static void main(String[] args) {

        String functionName = "maze-dynamo-GetMatchesFunction-sj6BGXFbFKmN";
        Region region = Region.EU_CENTRAL_1;
        LambdaClient lambdaClient = LambdaClient.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        invokeRemoteLambdaFunction(lambdaClient, functionName);
        lambdaClient.close();
    }

    public static void invokeRemoteLambdaFunction(LambdaClient lambdaClient, String functionName) {


        InvokeResponse res = null;
        try {

            // Need a SdkBytes instance for the payload.
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("name1", "value1");
            jsonObj.put("name2", "value2");
            String json = jsonObj.toString();
            SdkBytes payload = SdkBytes.fromUtf8String(json);

            // Setup an InvokeRequest
            InvokeRequest request = InvokeRequest.builder()
                    .functionName(functionName)
                    .payload(payload)
                    .build();

            res = lambdaClient.invoke(request);
            String value = res.payload().asUtf8String();
            System.out.println(value);

        } catch (LambdaException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

}
