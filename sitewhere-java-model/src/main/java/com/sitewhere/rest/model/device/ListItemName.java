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
