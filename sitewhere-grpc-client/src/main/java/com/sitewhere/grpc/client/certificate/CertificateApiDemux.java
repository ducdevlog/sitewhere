/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.grpc.client.certificate;

import com.sitewhere.grpc.client.MultitenantApiDemux;
import com.sitewhere.grpc.client.mqtt.MqttAclApiChannel;
import com.sitewhere.grpc.client.spi.client.ICertificateApiChannel;
import com.sitewhere.grpc.client.spi.client.ICertificateApiDemux;
import com.sitewhere.grpc.client.spi.client.IMqttAclApiChannel;
import com.sitewhere.grpc.client.spi.client.IMqttAclApiDemux;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.microservice.IFunctionIdentifier;
import com.sitewhere.spi.microservice.MicroserviceIdentifier;

public class CertificateApiDemux extends MultitenantApiDemux<ICertificateApiChannel<?>>
        implements ICertificateApiDemux {
    public CertificateApiDemux(boolean cacheEnabled) {
        super(cacheEnabled);
    }

    @Override
    public IFunctionIdentifier getTargetIdentifier() {
        return MicroserviceIdentifier.Certificate;
    }

    @Override
    public ICertificateApiChannel<?> createApiChannel(String host, boolean cacheEnabled) throws SiteWhereException {
        return new CertificateApiChannel(this, host, getMicroservice().getInstanceSettings().getGrpcPort());
    }
}
