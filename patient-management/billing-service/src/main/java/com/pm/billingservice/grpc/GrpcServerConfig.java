package com.pm.billingservice.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GrpcServerConfig {

    private final BillingGrpcService billingGrpcService;
    private Server server;

    @Value("${grpc.server.port:9001}")
    private int grpcPort;

    public GrpcServerConfig(BillingGrpcService billingGrpcService) {
        this.billingGrpcService = billingGrpcService;
    }

    @PostConstruct
    public void start() throws IOException {

        this.server = ServerBuilder.forPort(grpcPort)
                .addService(billingGrpcService)
                .addService(ProtoReflectionService.newInstance())
                .build();

        System.out.println("Starting gRPC Server on port " + grpcPort);
        server.start();
    }

    @PreDestroy
    public void stop() {
        if (server != null) {
            System.out.println("Shutting down gRPC Server...");
            server.shutdown();
        }
    }
}