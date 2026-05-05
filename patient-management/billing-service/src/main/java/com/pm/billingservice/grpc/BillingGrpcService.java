package com.pm.billingservice.grpc;

import com.pm.billing.grpc.BillingRequest;
import com.pm.billing.grpc.BillingResponse;
import com.pm.billing.grpc.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(BillingRequest request,
                                     StreamObserver<BillingResponse> responseObserver) {
        log.info("createBillingAccount request received {}",request.toString());
        String patientId = request.getPatientId();
        String name = request.getName();

        System.out.println("Received billing request for: " + name);
                  //Business Logic
        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("12345")
                .setStatus("Active")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}