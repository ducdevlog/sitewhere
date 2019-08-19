/*
 *
 *  Developed by Anhgv by VinGroup on 6/21/19, 11:54 AM
 *  Last modified 6/21/19, 11:54 AM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.sitewhere.commands.encoding.json;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AnhGV
 */
public class IRDeviceCommandValue {
    private String deviceThingUid;
    private Map<String, String> values = new HashMap<>();
    private String command;

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    public String getDeviceThingUid() {
        return deviceThingUid;
    }

    public void setDeviceThingUid(String deviceThingUid) {
        this.deviceThingUid = deviceThingUid;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
