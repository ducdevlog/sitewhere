/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.grpc.client.infrared;

import com.sitewhere.grpc.client.MultitenantApiDemux;
import com.sitewhere.grpc.client.spi.client.ICertificateApiChannel;
import com.sitewhere.grpc.client.spi.client.ICertificateApiDemux;
import com.sitewhere.grpc.client.spi.client.IInfraredApiChannel;
import com.sitewhere.grpc.client.spi.client.IInfraredApiDemux;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.microservice.IFunctionIdentifier;
import com.sitewhere.spi.microservice.MicroserviceIdentifier;

public class InfraredApiDemux extends MultitenantApiDemux<IInfraredApiChannel<?>> implements IInfraredApiDemux {
    public InfraredApiDemux(boolean cacheEnabled) {
        super(cacheEnabled);
    }

    @Override
    public IFunctionIdentifier getTargetIdentifier() {
        return MicroserviceIdentifier.Infrared;
    }

    @Override
    public IInfraredApiChannel<?> createApiChannel(String host, boolean cacheEnabled) throws SiteWhereException {
        return new InfraredApiChannel(this, host, getMicroservice().getInstanceSettings().getGrpcPort());
    }
}
