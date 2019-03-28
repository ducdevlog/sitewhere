/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.ohrule.spi.microservice;

import com.sitewhere.spi.microservice.MicroserviceIdentifier;
import com.sitewhere.spi.microservice.multitenant.IMultitenantMicroservice;
import org.springframework.stereotype.Component;

/**
 * Microservice that provides openhab's rule management functionality.
 *
 * @author Duc
 */
@Component
public interface IOpenHabRuleMicroservice
		extends IMultitenantMicroservice<MicroserviceIdentifier, IOpenHabRuleTenantEngine> {

}