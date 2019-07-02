/*
 *
 *  Developed by Anhgv by VinGroup on 7/1/19, 12:04 PM
 *  Last modified 7/1/19, 12:04 PM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.vin.iot.platform.provisioning.domain;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class DomainPendingFieldSetMapper implements FieldSetMapper<DevicePending> {

	@Override
	public DevicePending mapFieldSet(FieldSet fieldSet) throws BindException {
		final DevicePending devicePending = new DevicePending();
		devicePending.setHardwareId(fieldSet.readString("hardwareId"));
		devicePending.setDeviceType(fieldSet.readString("deviceType"));
		devicePending.setSsl(fieldSet.readBoolean("ssl"));
		return devicePending;
	}
}