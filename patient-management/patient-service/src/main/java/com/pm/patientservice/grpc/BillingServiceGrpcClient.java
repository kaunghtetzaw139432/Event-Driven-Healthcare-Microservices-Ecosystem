package com.pm.patientservice.grpc;

import com.pm.billing.grpc.BillingRequest;
import com.pm.billing.grpc.BillingResponse;
import com.pm.billing.grpc.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BillingServiceGrpcClient {

    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;
    private final ManagedChannel channel;

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort
    ) {
        log.info("Connecting to Billing Service GRPC service at {}:{}", serverAddress, serverPort);


        this.channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort)
                .usePlaintext()
                .build();

        this.blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }
       public BillingResponse createBillingAccount(String patient_id,String email,String name)
       {
           BillingRequest request=BillingRequest.newBuilder()
                   .setPatientId(patient_id)
                   .setEmail(email)
                   .setName(name)
                   .build();
           BillingResponse response=blockingStub.createBillingAccount(request);
           log.info("Received response from billing service via GRPC : {}",response);
           return  response;
       }
    @PreDestroy
    public void shutdown() {
        if (channel != null) {
            channel.shutdown();
        }
    }
}