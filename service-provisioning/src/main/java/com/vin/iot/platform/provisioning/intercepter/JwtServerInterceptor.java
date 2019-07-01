package com.vin.iot.platform.provisioning.intercepter;

import com.vin.iot.platform.provisioning.grpc.MultitenantServiceGrpcImpl;
import io.grpc.*;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

//@GRpcGlobalInterceptor
//@Order(1)
public class JwtServerInterceptor implements ServerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultitenantServiceGrpcImpl.class);

    public static final Context.Key<String> CONTEXT_JWT_KEY = Context.key("jwt");

    public static final Metadata.Key<String> HEADER_JWT_KEY = Metadata.Key.of("jwt", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        if (headers.containsKey(HEADER_JWT_KEY)) {
            String jwt = headers.get(HEADER_JWT_KEY);
            LOGGER.trace("Server received jwt key: " + jwt);
            Context ctx = Context.current().withValue(CONTEXT_JWT_KEY, jwt);
            return Contexts.interceptCall(ctx, call, headers, next);
        } else {
            call.close(Status.UNAUTHENTICATED.withDescription("JWT not passed in metadata."), headers);
            return new ServerCall.Listener<ReqT>() {
            };
        }
    }
}
