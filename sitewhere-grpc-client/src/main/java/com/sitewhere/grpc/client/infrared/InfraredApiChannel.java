/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.sitewhere.grpc.client.infrared;

import com.sitewhere.grpc.client.GrpcUtils;
import com.sitewhere.grpc.client.MultitenantApiChannel;
import com.sitewhere.grpc.client.spi.IApiDemux;
import com.sitewhere.grpc.client.spi.client.IInfraredApiChannel;
import com.sitewhere.grpc.service.*;
import com.sitewhere.rest.model.infrared.InfraredDeviceCodeset;
import com.sitewhere.rest.model.infrared.InfraredDeviceTypeBrand;
import com.sitewhere.rest.model.infrared.IrCodeRaw;
import com.sitewhere.rest.model.search.SearchResults;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.infrared.IInfraredDeviceCodeset;
import com.sitewhere.spi.infrared.IInfraredDeviceType;
import com.sitewhere.spi.infrared.IInfraredDeviceTypeBrand;
import com.sitewhere.spi.infrared.IIrCodeRaw;
import com.sitewhere.spi.search.ISearchResults;
import com.sitewhere.spi.tracing.ITracerProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class InfraredApiChannel extends MultitenantApiChannel<InfraredGrpcChannel>
	implements IInfraredApiChannel<InfraredGrpcChannel> {

    public InfraredApiChannel(IApiDemux<?> demux, String hostname, int port) {
        super(demux, hostname, port);
    }

    @Override
    public InfraredGrpcChannel createGrpcChannel(ITracerProvider tracerProvider, String host, int port) {
        return new InfraredGrpcChannel(tracerProvider, host, port);
    }

    @Override
    public List<IInfraredDeviceType> getInfraredDeviceType() throws SiteWhereException {
        try {
            GrpcUtils.handleClientMethodEntry(this, InfraredGrpc.getGetInfraredDeviceTypeMethod());
            GGetInfraredDeviceTypeRequest.Builder grequest = GGetInfraredDeviceTypeRequest.newBuilder();
            GGetInfraredDeviceTypeResponse gresponse = getGrpcChannel().getBlockingStub().getInfraredDeviceType(grequest.build());
            List<IInfraredDeviceType> infraredDeviceTypes = new ArrayList<>();
            if (gresponse.getInfraredDeviceTypeCount() > 0) {
                gresponse.getInfraredDeviceTypeList().stream().map(InfraredModelConverter::asApiInfraredDeviceType).forEach(infraredDeviceTypes::add);
            }
            GrpcUtils.logClientMethodResponse(InfraredGrpc.getGetInfraredDeviceTypeMethod(), infraredDeviceTypes);
            return infraredDeviceTypes;
        } catch (Throwable t) {
            throw GrpcUtils.handleClientMethodException(InfraredGrpc.getGetInfraredDeviceTypeMethod(), t);
        }
    }

    @Override
    public List<IInfraredDeviceTypeBrand> getInfraredDeviceTypeBrand(String type) throws SiteWhereException {
        try {
            GrpcUtils.handleClientMethodEntry(this, InfraredGrpc.getGetInfraredDeviceTypeMethod());
            GGetInfraredDeviceTypeBrandRequest.Builder grequest = GGetInfraredDeviceTypeBrandRequest.newBuilder();
            grequest.setType(type);
            GGetInfraredDeviceTypeBrandResponse gresponse = getGrpcChannel().getBlockingStub().getInfraredDeviceTypeBrand(grequest.build());
            List<IInfraredDeviceTypeBrand> infraredDeviceTypeBrands = new ArrayList<>();
            if (gresponse.getInfraredDeviceTypeBrandCount() > 0) {
                gresponse.getInfraredDeviceTypeBrandList().stream().map(InfraredModelConverter::asApiInfraredDeviceTypeBrand).forEach(infraredDeviceTypeBrands::add);
            }
            GrpcUtils.logClientMethodResponse(InfraredGrpc.getGetInfraredDeviceTypeMethod(), infraredDeviceTypeBrands);
            return infraredDeviceTypeBrands;
        } catch (Throwable t) {
            throw GrpcUtils.handleClientMethodException(InfraredGrpc.getGetInfraredDeviceTypeMethod(), t);
        }
    }

    @Override
    public IInfraredDeviceTypeBrand createInfraredDeviceTypeBrand(InfraredDeviceTypeBrand infraredDeviceTypeBrand) throws SiteWhereException {
        try {
            GrpcUtils.handleClientMethodEntry(this, InfraredGrpc.getCreateInfraredDeviceTypeBrandMethod());
            GCreateInfraredDeviceTypeBrandRequest.Builder grequest = GCreateInfraredDeviceTypeBrandRequest.newBuilder();
            grequest.setInfraredDeviceTypeBrand(InfraredModelConverter.asGrpcInfraredDeviceTypeBrand(infraredDeviceTypeBrand));
            AtomicReference<GCreateInfraredDeviceTypeBrandResponse> gresponse = new AtomicReference<>(getGrpcChannel().getBlockingStub().createInfraredDeviceTypeBrand(grequest.build()));
            return InfraredModelConverter.asApiInfraredDeviceTypeBrand(gresponse.get().getInfraredDeviceTypeBrand());
        } catch (Throwable t) {
            throw GrpcUtils.handleClientMethodException(InfraredGrpc.getCreateInfraredDeviceTypeBrandMethod(), t);
        }
    }

    @Override
    public List<IInfraredDeviceCodeset> getInfraredDeviceCodeset(String deviceTypeBrandId) throws SiteWhereException {
        try {
            GrpcUtils.handleClientMethodEntry(this, InfraredGrpc.getGetInfraredDeviceCodesetMethod());
            GGetInfraredDeviceCodesetRequest.Builder grequest = GGetInfraredDeviceCodesetRequest.newBuilder();
            grequest.setDeviceTypeBrandId(deviceTypeBrandId);
            GGetInfraredDeviceCodesetResponse gresponse = getGrpcChannel().getBlockingStub().getInfraredDeviceCodeset(grequest.build());
            List<IInfraredDeviceCodeset> infraredDeviceCodesets = new ArrayList<>();
            if (gresponse.getInfraredDeviceCodesetCount() > 0) {
                gresponse.getInfraredDeviceCodesetList().stream().map(InfraredModelConverter::asApiInfraredDeviceCodeset).forEach(infraredDeviceCodesets::add);
            }
            GrpcUtils.logClientMethodResponse(InfraredGrpc.getGetInfraredDeviceCodesetMethod(), infraredDeviceCodesets);
            return infraredDeviceCodesets;
        } catch (Throwable t) {
            throw GrpcUtils.handleClientMethodException(InfraredGrpc.getGetInfraredDeviceCodesetMethod(), t);
        }
    }

    @Override
    public IInfraredDeviceCodeset createInfraredDeviceCodeset(InfraredDeviceCodeset infraredDeviceCodeset) throws SiteWhereException {
        try {
            GrpcUtils.handleClientMethodEntry(this, InfraredGrpc.getCreateInfraredDeviceCodesetMethod());
            GCreateInfraredDeviceCodesetRequest.Builder grequest = GCreateInfraredDeviceCodesetRequest.newBuilder();
            grequest.setInfraredDeviceCodeset(InfraredModelConverter.asGrpcInfraredDeviceCodeset(infraredDeviceCodeset));
            GCreateInfraredDeviceCodesetResponse gresponse = getGrpcChannel().getBlockingStub().createInfraredDeviceCodeset(grequest.build());
            return InfraredModelConverter.asApiInfraredDeviceCodeset(gresponse.getInfraredDeviceCodeset());
        } catch (Throwable t) {
            throw GrpcUtils.handleClientMethodException(InfraredGrpc.getCreateInfraredDeviceCodesetMethod(), t);
        }
    }

    @Override
    public ISearchResults<IIrCodeRaw> getIrCodeRaw(IrCodeRaw irCodeRaw, int page, int size) throws SiteWhereException {
        try {
            GrpcUtils.handleClientMethodEntry(this, InfraredGrpc.getGetIrCodeRawMethod());
            GIrCodeRawRequest.Builder grequest = GIrCodeRawRequest.newBuilder();
            grequest.setIrCodeRaw(InfraredModelConverter.asGrpcIrCodeRaw(irCodeRaw));
            grequest.setPage(page);
            grequest.setSize(size);
            System.out.println("============ :" + irCodeRaw.getCodesetName());
            System.out.println("============ :" + grequest.getIrCodeRaw().getCodesetName());
            GIrCodeRawResponse gresponse = getGrpcChannel().getBlockingStub().getIrCodeRaw(grequest.build());
            List<IIrCodeRaw> iIrCodeRaws = new ArrayList<>();
            if (gresponse.getIrCodeRawCount() > 0) {
                gresponse.getIrCodeRawList().stream().map(InfraredModelConverter::asApiGIrCodeRaw).forEach(iIrCodeRaws::add);
            }
            ISearchResults<IIrCodeRaw> rawISearchResults = new SearchResults<IIrCodeRaw>(iIrCodeRaws, gresponse.getResults());
            GrpcUtils.logClientMethodResponse(InfraredGrpc.getGetIrCodeRawMethod(), iIrCodeRaws);
            return rawISearchResults;
        } catch (Throwable t) {
            throw GrpcUtils.handleClientMethodException(InfraredGrpc.getGetIrCodeRawMethod(), t);
        }
    }

    @Override
    public IIrCodeRaw createIrCodeRaw(IrCodeRaw irCodeRaw) throws SiteWhereException {
        try {
            GrpcUtils.handleClientMethodEntry(this, InfraredGrpc.getCreateInfraredDeviceCodesetMethod());
            GIrCodeRawCreateRequest.Builder grequest = GIrCodeRawCreateRequest.newBuilder();
            grequest.setIrCodeRaw(InfraredModelConverter.asGrpcIrCodeRaw(irCodeRaw));
            GIrCodeRawCreateResponse gresponse = getGrpcChannel().getBlockingStub().createIrCodeRaw(grequest.build());
            return InfraredModelConverter.asApiGIrCodeRaw(gresponse.getIrCodeRaw());
        } catch (Throwable t) {
            throw GrpcUtils.handleClientMethodException(InfraredGrpc.getCreateInfraredDeviceCodesetMethod(), t);
        }
    }
}
