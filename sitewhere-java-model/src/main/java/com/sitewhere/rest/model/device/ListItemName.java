/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.rest.model.device;

import com.sitewhere.spi.device.IListItemName;

import java.util.List;

public class ListItemName implements IListItemName {
    /** For Java serialization */
    private static final long serialVersionUID = 594540906893472520L;

    private List<String> itemNames;

    @Override
    public List<String> getItemNames() {
        return itemNames;
    }

    public void setItemNames(List<String> itemNames) {
        this.itemNames = itemNames;
    }

}
