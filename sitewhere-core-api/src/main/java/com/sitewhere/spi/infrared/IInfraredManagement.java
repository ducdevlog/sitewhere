/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.spi.infrared;

import com.sitewhere.rest.model.infrared.InfraredDeviceCodeset;
import com.sitewhere.rest.model.infrared.InfraredDeviceTypeBrand;
import com.sitewhere.rest.model.infrared.IrCodeRaw;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.search.ISearchResults;
import com.sitewhere.spi.server.lifecycle.ITenantEngineLifecycleComponent;

import java.util.List;
import java.util.Map;

public interface IInfraredManagement extends ITenantEngineLifecycleComponent {

    public List<IInfraredDeviceType> getInfraredDeviceType() throws SiteWhereException;

    public List<IInfraredDeviceTypeBrand> getInfraredDeviceTypeBrand(String type) throws SiteWhereException;

    public IInfraredDeviceTypeBrand createInfraredDeviceTypeBrand(InfraredDeviceTypeBrand infraredDeviceTypeBrand) throws SiteWhereException;

    public List<IInfraredDeviceCodeset> getInfraredDeviceCodeset(String deviceTypeBrandId) throws SiteWhereException;

    public IInfraredDeviceCodeset createInfraredDeviceCodeset(InfraredDeviceCodeset infraredDeviceCodeset) throws SiteWhereException;

    public ISearchResults<IIrCodeRaw> getIrCodeRaw(IrCodeRaw irCodeRaw, int page, int size) throws SiteWhereException;

    public List<Map<String, Object>> getIrCodeRawFilter(String irCodeRaw, int page, int size) throws SiteWhereException;

    public IIrCodeRaw createIrCodeRaw(IrCodeRaw irCodeRaw) throws SiteWhereException;

    public IrCodeRaw createIrCodeRawLearn(IrCodeRaw irCodeRaw) throws SiteWhereException;
}
