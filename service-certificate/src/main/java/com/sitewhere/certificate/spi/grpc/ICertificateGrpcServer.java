/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.certificate.spi.grpc;

import com.sitewhere.spi.server.lifecycle.ITenantEngineLifecycleComponent;

/**
 * Binds to a port and listens for certificate GRPC requests.
 *
 * @author Derek
 */
public interface ICertificateGrpcServer extends ITenantEngineLifecycleComponent {
}