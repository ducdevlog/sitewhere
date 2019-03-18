package com.sitewhere.rest.model.device;

import com.sitewhere.spi.device.IListItemName;

import java.util.List;

public class ListItemName implements IListItemName {

    private List<String> itemNames;

    @Override
    public List<String> getItemNames() {
        return null;
    }

    public void setItemNames(List<String> itemNames) {
        this.itemNames = itemNames;
    }

}
