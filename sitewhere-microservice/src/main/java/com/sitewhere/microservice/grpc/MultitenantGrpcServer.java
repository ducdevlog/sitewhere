/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.microservice.grpc;

import com.sitewhere.spi.microservice.multitenant.IMultitenantMicroservice;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Base class for GRPC servers that handle requests for multiple tenants.
 * 
 * @author Derek
 */
public class MultitenantGrpcServer extends GrpcServer {

    /** Interceptor for tenant token */
    private TenantTokenServerInterceptor tenantTokenInterceptor;

    public MultitenantGrpcServer(BindableService serviceImplementation, int port) {
	super(serviceImplementation, port);
    }

    /**
     * Build server component based on configuration.
     * 
     * @return
     */
    protected Server buildServer() {
	this.tenantTokenInterceptor = new TenantTokenServerInterceptor(getMicroservice());
	NettyServerBuilder builder = NettyServerBuilder.forPort(getPort());
	builder.addService(getServiceImplementation()).intercept(getTenantTokenInterceptor())
		.intercept(getJwtInterceptor());
	builder.addService(new MultitenantManagementImpl((IMultitenantMicroservice<?, ?>) getMicroservice()));
	builder.executor(getServerExecutor());
	builder.bossEventLoopGroup(new NioEventLoopGroup(1));
	builder.workerEventLoopGroup(new NioEventLoopGroup(100));
	if (isUseTracingInterceptor()) {
		getLogger().info("============= ServerTracingInterceptor: buildServer ===============");
	    builder.intercept(getTracingInterceptor());
	}
	return builder.build();
    }

    protected TenantTokenServerInterceptor getTenantTokenInterceptor() {
	return tenantTokenInterceptor;
    }

    protected void setTenantTokenInterceptor(TenantTokenServerInterceptor tenantTokenInterceptor) {
	this.tenantTokenInterceptor = tenantTokenInterceptor;
    }
}