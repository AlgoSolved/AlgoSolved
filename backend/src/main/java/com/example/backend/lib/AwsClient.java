package com.example.backend.lib;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

@Component
public class AwsClient {
    @Value("${aws.accessKeyId}")
    private String accessKey;
    @Value("${aws.secretAccessKey}")
    private String secretKey;
    private final Region region = Region.AP_NORTHEAST_2;

    public String getParameterValue(String parameterName) {
        try {
            AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
            SsmClient ssmClient = SsmClient.builder()
                .credentialsProvider(() -> awsCreds)
                .region(region)
                .build();

            GetParameterRequest parameterRequest = GetParameterRequest.builder()
                .name(parameterName)
                .build();
            GetParameterResponse parameterResponse = ssmClient.getParameter(parameterRequest);
            return parameterResponse.parameter().value();
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    };
}
