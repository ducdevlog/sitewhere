package com.sitewhere.spi.device;

import java.io.Serializable;
import java.util.List;

public interface IListItemName extends Serializable {
    List<String> getItemNames();
}
