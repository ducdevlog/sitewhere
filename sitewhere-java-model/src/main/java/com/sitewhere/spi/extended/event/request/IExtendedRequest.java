package com.sitewhere.spi.extended.event.request;

import com.sitewhere.spi.common.IMetadataProvider;

import java.io.Serializable;

public interface IExtendedRequest extends IMetadataProvider, Serializable {
	String getRequestType();
	Object getData();
}
