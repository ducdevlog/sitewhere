package com.vin.iot.platform.openhab.event.intercepter;

import io.grpc.*;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

//@GRpcGlobalInterceptor
//@Order(10)
public  class TenantTokenServerInterceptor implements ServerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenantTokenServerInterceptor.class);

    /** Key for accessing requested tenant id */
    public static final Context.Key<String> CONTEXT_TENANT_ID_KEY = Context.key("tenant");

    public static final Metadata.Key<String> HEADER_TENANT_ID_KEY = Metadata.Key.of("tenant", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        if (headers.containsKey(HEADER_TENANT_ID_KEY)) {
            String tenantId = headers.get(HEADER_TENANT_ID_KEY);
            LOGGER.trace("Server received tenant id key: " + tenantId);
            Context ctx = Context.current().withValue(CONTEXT_TENANT_ID_KEY, tenantId);
            return Contexts.interceptCall(ctx, call, headers, next);
        } else {
            call.close(Status.UNAUTHENTICATED.withDescription("Tenant token not passed in metadata."), headers);
            return new ServerCall.Listener<ReqT>() {
            };
        }
    }
}