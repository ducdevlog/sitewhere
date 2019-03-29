/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.spi.device.command;

import java.util.List;
import java.util.UUID;

import com.sitewhere.spi.common.IAccessible;
import com.sitewhere.spi.common.IPersistentEntity;
import com.sitewhere.spi.device.ReversedMessageType;

/**
 * A parameterized command issued to a device.
 * 
 * @author Derek
 */
public interface IDeviceCommand extends IPersistentEntity, IAccessible {

    /**
     * Get unique id of parent device type.
     * 
     * @return
     */
    public UUID getDeviceTypeId();

    /**
     * Optional namespace for distinguishing commands.
     * 
     * @return
     */
    public String getNamespace();

    /**
     * Get list of parameters.
     * 
     * @return
     */
    public List<ICommandParameter> getParameters();

    /**
     * Get type of message that indicate size of message to devices.
     *
     * @return
     */
    ReversedMessageType getReversedMessageType();

    /**
     * Get route that indicate the topic of command, config, ...
     *
     * @return
     */
    String getCommandRoute();
}