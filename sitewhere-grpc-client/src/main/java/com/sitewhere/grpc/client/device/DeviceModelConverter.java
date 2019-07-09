/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.grpc.client.device;

import java.util.*;
import java.util.stream.Collectors;

import com.sitewhere.grpc.client.common.converter.CommonModelConverter;
import com.sitewhere.grpc.model.CommonModel;
import com.sitewhere.grpc.model.CommonModel.GDeviceAlarmState;
import com.sitewhere.grpc.model.CommonModel.GDeviceContainerPolicy;
import com.sitewhere.grpc.model.CommonModel.GOptionalBoolean;
import com.sitewhere.grpc.model.CommonModel.GOptionalDouble;
import com.sitewhere.grpc.model.CommonModel.GOptionalString;
import com.sitewhere.grpc.model.CommonModel.GParameterType;
import com.sitewhere.grpc.model.DeviceModel.GArea;
import com.sitewhere.grpc.model.DeviceModel.GAreaCreateRequest;
import com.sitewhere.grpc.model.DeviceModel.GAreaSearchCriteria;
import com.sitewhere.grpc.model.DeviceModel.GAreaSearchResults;
import com.sitewhere.grpc.model.DeviceModel.GAreaType;
import com.sitewhere.grpc.model.DeviceModel.GAreaTypeCreateRequest;
import com.sitewhere.grpc.model.DeviceModel.GAreaTypeSearchCriteria;
import com.sitewhere.grpc.model.DeviceModel.GAreaTypeSearchResults;
import com.sitewhere.grpc.model.DeviceModel.GCommandParameter;
import com.sitewhere.grpc.model.DeviceModel.GCustomer;
import com.sitewhere.grpc.model.DeviceModel.GCustomerCreateRequest;
import com.sitewhere.grpc.model.DeviceModel.GCustomerSearchCriteria;
import com.sitewhere.grpc.model.DeviceModel.GCustomerSearchResults;
import com.sitewhere.grpc.model.DeviceModel.GCustomerType;
import com.sitewhere.grpc.model.DeviceModel.GCustomerTypeCreateRequest;
import com.sitewhere.grpc.model.DeviceModel.GCustomerTypeSearchCriteria;
import com.sitewhere.grpc.model.DeviceModel.GCustomerTypeSearchResults;
import com.sitewhere.grpc.model.DeviceModel.GDevice;
import com.sitewhere.grpc.model.DeviceModel.GDeviceAlarm;
import com.sitewhere.grpc.model.DeviceModel.GDeviceAlarmCreateRequest;
import com.sitewhere.grpc.model.DeviceModel.GDeviceAlarmSearchCriteria;
import com.sitewhere.grpc.model.DeviceModel.GDeviceAlarmSearchResults;
import com.sitewhere.grpc.model.DeviceModel.GDeviceAssignment;
import com.sitewhere.grpc.model.DeviceModel.GDeviceAssignmentCreateRequest;
import com.sitewhere.grpc.model.DeviceModel.GDeviceAssignmentSearchCriteria;
import com.sitewhere.grpc.model.DeviceModel.GDeviceAssignmentSearchResults;
import com.sitewhere.grpc.model.DeviceModel.GDeviceCommand;
import com.sitewhere.grpc.model.DeviceModel.GDeviceCommandCreateRequest;
import com.sitewhere.grpc.model.DeviceModel.GDeviceCommandSearchCriteria;
import com.sitewhere.grpc.model.DeviceModel.GDeviceCommandSearchResults;
import com.sitewhere.grpc.model.DeviceModel.GDeviceCreateRequest;
import com.sitewhere.grpc.model.DeviceModel.GDeviceElementMapping;
import com.sitewhere.grpc.model.DeviceModel.GDeviceElementSchema;
import com.sitewhere.grpc.model.DeviceModel.GDeviceGroup;
import com.sitewhere.grpc.model.DeviceModel.GDeviceGroupCreateRequest;
import com.sitewhere.grpc.model.DeviceModel.GDeviceGroupElement;
import com.sitewhere.grpc.model.DeviceModel.GDeviceGroupElementCreateRequest;
import com.sitewhere.grpc.model.DeviceModel.GDeviceGroupElementsSearchCriteria;
import com.sitewhere.grpc.model.DeviceModel.GDeviceGroupElementsSearchResults;
import com.sitewhere.grpc.model.DeviceModel.GDeviceGroupSearchCriteria;
import com.sitewhere.grpc.model.DeviceModel.GDeviceGroupSearchResults;
import com.sitewhere.grpc.model.DeviceModel.GDeviceGroupsWithRoleSearchCriteria;
import com.sitewhere.grpc.model.DeviceModel.GDeviceRegistationPayload;
import com.sitewhere.grpc.model.DeviceModel.GDeviceRegistrationRequest;
import com.sitewhere.grpc.model.DeviceModel.GDeviceSearchCriteria;
import com.sitewhere.grpc.model.DeviceModel.GDeviceSearchResults;
import com.sitewhere.grpc.model.DeviceModel.GDeviceSlot;
import com.sitewhere.grpc.model.DeviceModel.GListItemName;
import com.sitewhere.grpc.model.DeviceModel.GDeviceStatus;
import com.sitewhere.grpc.model.DeviceModel.GDeviceStatusCreateRequest;
import com.sitewhere.grpc.model.DeviceModel.GDeviceStatusSearchCriteria;
import com.sitewhere.grpc.model.DeviceModel.GDeviceStatusSearchResults;
import com.sitewhere.grpc.model.DeviceModel.GDeviceType;
import com.sitewhere.grpc.model.DeviceModel.GDeviceTypeCreateRequest;
import com.sitewhere.grpc.model.DeviceModel.GDeviceTypeReference;
import com.sitewhere.grpc.model.DeviceModel.GDeviceTypeSearchCriteria;
import com.sitewhere.grpc.model.DeviceModel.GDeviceTypeSearchResults;
import com.sitewhere.grpc.model.DeviceModel.GDeviceUnit;
import com.sitewhere.grpc.model.DeviceModel.GZone;
import com.sitewhere.grpc.model.DeviceModel.GZoneCreateRequest;
import com.sitewhere.grpc.model.DeviceModel.GZoneSearchCriteria;
import com.sitewhere.grpc.model.DeviceModel.GZoneSearchResults;
import com.sitewhere.grpc.model.DeviceModel.GDeviceTypeReversedMessageType;
import com.sitewhere.rest.model.area.Area;
import com.sitewhere.rest.model.area.AreaType;
import com.sitewhere.rest.model.area.Zone;
import com.sitewhere.rest.model.area.request.AreaCreateRequest;
import com.sitewhere.rest.model.area.request.AreaTypeCreateRequest;
import com.sitewhere.rest.model.area.request.ZoneCreateRequest;
import com.sitewhere.rest.model.customer.Customer;
import com.sitewhere.rest.model.customer.CustomerType;
import com.sitewhere.rest.model.customer.request.CustomerCreateRequest;
import com.sitewhere.rest.model.customer.request.CustomerTypeCreateRequest;
import com.sitewhere.rest.model.device.*;
import com.sitewhere.rest.model.device.command.CommandParameter;
import com.sitewhere.rest.model.device.command.DeviceCommand;
import com.sitewhere.rest.model.device.element.DeviceElementSchema;
import com.sitewhere.rest.model.device.element.DeviceSlot;
import com.sitewhere.rest.model.device.element.DeviceUnit;
import com.sitewhere.rest.model.device.event.kafka.DeviceRegistrationPayload;
import com.sitewhere.rest.model.device.event.request.DeviceRegistrationRequest;
import com.sitewhere.rest.model.device.group.DeviceGroup;
import com.sitewhere.rest.model.device.group.DeviceGroupElement;
import com.sitewhere.rest.model.device.request.DeviceAlarmCreateRequest;
import com.sitewhere.rest.model.device.request.DeviceAssignmentCreateRequest;
import com.sitewhere.rest.model.device.request.DeviceCommandCreateRequest;
import com.sitewhere.rest.model.device.request.DeviceCreateRequest;
import com.sitewhere.rest.model.device.request.DeviceGroupCreateRequest;
import com.sitewhere.rest.model.device.request.DeviceGroupElementCreateRequest;
import com.sitewhere.rest.model.device.request.DeviceStatusCreateRequest;
import com.sitewhere.rest.model.device.request.DeviceTypeCreateRequest;
import com.sitewhere.rest.model.search.SearchResults;
import com.sitewhere.rest.model.search.area.AreaSearchCriteria;
import com.sitewhere.rest.model.search.customer.CustomerSearchCriteria;
import com.sitewhere.rest.model.search.device.*;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.area.IArea;
import com.sitewhere.spi.area.IAreaType;
import com.sitewhere.spi.area.IZone;
import com.sitewhere.spi.area.request.IAreaCreateRequest;
import com.sitewhere.spi.area.request.IAreaTypeCreateRequest;
import com.sitewhere.spi.area.request.IZoneCreateRequest;
import com.sitewhere.spi.customer.ICustomer;
import com.sitewhere.spi.customer.ICustomerType;
import com.sitewhere.spi.customer.request.ICustomerCreateRequest;
import com.sitewhere.spi.customer.request.ICustomerTypeCreateRequest;
import com.sitewhere.spi.device.*;
import com.sitewhere.spi.device.command.ICommandParameter;
import com.sitewhere.spi.device.command.IDeviceCommand;
import com.sitewhere.spi.device.command.ParameterType;
import com.sitewhere.spi.device.element.IDeviceElementSchema;
import com.sitewhere.spi.device.element.IDeviceSlot;
import com.sitewhere.spi.device.element.IDeviceUnit;
import com.sitewhere.spi.device.event.kafka.IDeviceRegistrationPayload;
import com.sitewhere.spi.device.event.request.IDeviceRegistrationRequest;
import com.sitewhere.spi.device.group.IDeviceGroup;
import com.sitewhere.spi.device.group.IDeviceGroupElement;
import com.sitewhere.spi.device.request.IDeviceAlarmCreateRequest;
import com.sitewhere.spi.device.request.IDeviceAssignmentCreateRequest;
import com.sitewhere.spi.device.request.IDeviceCommandCreateRequest;
import com.sitewhere.spi.device.request.IDeviceCreateRequest;
import com.sitewhere.spi.device.request.IDeviceGroupCreateRequest;
import com.sitewhere.spi.device.request.IDeviceGroupElementCreateRequest;
import com.sitewhere.spi.device.request.IDeviceStatusCreateRequest;
import com.sitewhere.spi.device.request.IDeviceTypeCreateRequest;
import com.sitewhere.spi.error.ErrorCode;
import com.sitewhere.spi.search.ISearchCriteria;
import com.sitewhere.spi.search.ISearchResults;
import com.sitewhere.spi.search.area.IAreaSearchCriteria;
import com.sitewhere.spi.search.customer.ICustomerSearchCriteria;
import com.sitewhere.spi.search.device.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Convert device entities between SiteWhere API model and GRPC model.
 * 
 * @author Derek
 */
public class DeviceModelConverter {

    /**
     * Convert device container policy from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceContainerPolicy asApiDeviceContainerPolicy(GDeviceContainerPolicy grpc)
	    throws SiteWhereException {
	switch (grpc) {
	case CONTAINER_COMPOSITE:
	    return DeviceContainerPolicy.Composite;
	case CONTAINER_STANDALONE:
	    return DeviceContainerPolicy.Standalone;
	case UNRECOGNIZED:
	    throw new SiteWhereException(ErrorCode.InvalidDeviceContainerPolicy, "Unknown device container policy: " + grpc.name());
	}
	return null;
    }

	public static ReversedMessageType asApiDeviceReversedMessageType(GDeviceTypeReversedMessageType grpc)
			throws SiteWhereException {
		switch (grpc) {
			case FULL:
				return ReversedMessageType.FULL;
			case MINIMAL:
				return ReversedMessageType.MINIMAL;
			default:
				throw new SiteWhereException(ErrorCode.InvalidDeviceReversedMessage, "Unknown device reversed message type: " + grpc);
		}
	}

    /**
     * Convert device container policy from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceContainerPolicy asGrpcDeviceContainerPolicy(DeviceContainerPolicy api)
	    throws SiteWhereException {
	switch (api) {
	case Composite:
	    return GDeviceContainerPolicy.CONTAINER_COMPOSITE;
	case Standalone:
	    return GDeviceContainerPolicy.CONTAINER_STANDALONE;
	}
	throw new SiteWhereException(ErrorCode.InvalidDeviceContainerPolicy, "Unknown device container policy: " + api.name());
    }

	public static GDeviceTypeReversedMessageType asGrpcDeviceTypeReversedMessageType(ReversedMessageType api)
			throws SiteWhereException {
		switch (api) {
			case FULL:
				return GDeviceTypeReversedMessageType.FULL;
			case MINIMAL:
				return GDeviceTypeReversedMessageType.MINIMAL;
		}
		throw new SiteWhereException(ErrorCode.InvalidDeviceContainerPolicy, "Unknown device container policy: " + api.name());
	}

    /**
     * Convert device slot from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceSlot asApiDeviceSlot(GDeviceSlot grpc) throws SiteWhereException {
	DeviceSlot api = new DeviceSlot();
	api.setName(grpc.getName());
	api.setPath(grpc.getPath());
	return api;
    }

    /**
     * Convert device slot from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceSlot asGrpcDeviceSlot(IDeviceSlot api) throws SiteWhereException {
	GDeviceSlot.Builder grpc = GDeviceSlot.newBuilder();
	grpc.setName(api.getName());
	grpc.setPath(api.getPath());
	return grpc.build();
    }

    /**
     * Convert list of device slots from GRPC to API.
     * 
     * @param grpcs
     * @return
     * @throws SiteWhereException
     */
    public static List<DeviceSlot> asApiDeviceSlots(List<GDeviceSlot> grpcs) throws SiteWhereException {
	List<DeviceSlot> api = new ArrayList<DeviceSlot>();
	for (GDeviceSlot gslot : grpcs) {
	    api.add(DeviceModelConverter.asApiDeviceSlot(gslot));
	}
	return api;
    }

    /**
     * Convert list of device slots from API to GRPC.
     * 
     * @param apis
     * @return
     * @throws SiteWhereException
     */
    public static List<GDeviceSlot> asGrpcDeviceSlots(List<IDeviceSlot> apis) throws SiteWhereException {
	List<GDeviceSlot> grpcs = new ArrayList<GDeviceSlot>();
	for (IDeviceSlot api : apis) {
	    grpcs.add(DeviceModelConverter.asGrpcDeviceSlot(api));
	}
	return grpcs;
    }

    /**
     * Convert device unit from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceUnit asApiDeviceUnit(GDeviceUnit grpc) throws SiteWhereException {
	DeviceUnit api = new DeviceUnit();
	api.setName(grpc.getName());
	api.setPath(grpc.getPath());
	api.setDeviceSlots(DeviceModelConverter.asApiDeviceSlots(grpc.getSlotsList()));
	api.setDeviceUnits(DeviceModelConverter.asApiDeviceUnits(grpc.getUnitsList()));
	return api;
    }

    /**
     * Convert device unit from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceUnit asGrpcDeviceUnit(IDeviceUnit api) throws SiteWhereException {
	GDeviceUnit.Builder grpc = GDeviceUnit.newBuilder();
	grpc.setName(api.getName());
	grpc.setPath(api.getPath());
	grpc.addAllSlots(DeviceModelConverter.asGrpcDeviceSlots(api.getDeviceSlots()));
	grpc.addAllUnits(DeviceModelConverter.asGrpcDeviceUnits(api.getDeviceUnits()));
	return grpc.build();
    }

    /**
     * Convert list of device units from API to GRPC.
     * 
     * @param apis
     * @return
     * @throws SiteWhereException
     */
    public static List<GDeviceUnit> asGrpcDeviceUnits(List<IDeviceUnit> apis) throws SiteWhereException {
	List<GDeviceUnit> grpcs = new ArrayList<GDeviceUnit>();
	for (IDeviceUnit api : apis) {
	    grpcs.add(DeviceModelConverter.asGrpcDeviceUnit(api));
	}
	return grpcs;
    }

    /**
     * Convert list of device units from GRPC to API.
     * 
     * @param grpcs
     * @return
     * @throws SiteWhereException
     */
    public static List<DeviceUnit> asApiDeviceUnits(List<GDeviceUnit> grpcs) throws SiteWhereException {
	List<DeviceUnit> api = new ArrayList<DeviceUnit>();
	for (GDeviceUnit gunit : grpcs) {
	    api.add(DeviceModelConverter.asApiDeviceUnit(gunit));
	}
	return api;
    }

    /**
     * Convert device element schema from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceElementSchema asApiDeviceElementSchema(GDeviceElementSchema grpc) throws SiteWhereException {
	DeviceElementSchema api = new DeviceElementSchema();
	api.setDeviceSlots(DeviceModelConverter.asApiDeviceSlots(grpc.getSlotsList()));
	api.setDeviceUnits(DeviceModelConverter.asApiDeviceUnits(grpc.getUnitsList()));
	return api;
    }

    /**
     * Convert device element schema from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceElementSchema asGrpcDeviceElementSchema(IDeviceElementSchema api) throws SiteWhereException {
	GDeviceElementSchema.Builder grpc = GDeviceElementSchema.newBuilder();
	grpc.addAllSlots(DeviceModelConverter.asGrpcDeviceSlots(api.getDeviceSlots()));
	grpc.addAllUnits(DeviceModelConverter.asGrpcDeviceUnits(api.getDeviceUnits()));
	return grpc.build();
    }

    /**
     * Convert device type search criteria from API to GRPC.
     * 
     * @param criteria
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceTypeSearchCriteria asGrpcDeviceTypeSearchCriteria(IDeviceTypeSearchCriteria criteria)
	    throws SiteWhereException {
	GDeviceTypeSearchCriteria.Builder gcriteria = GDeviceTypeSearchCriteria.newBuilder();
	if (criteria != null) {
		if (StringUtils.isNotEmpty(criteria.getParentDeviceTypeToken())) {
			gcriteria.setParentDeviceTypeToken(criteria.getParentDeviceTypeToken());
		}
	    gcriteria.setPaging(CommonModelConverter.asGrpcPaging(criteria));
	}
	return gcriteria.build();
    }

	public static DeviceTypeSearchCriteria asApiDeviceTypeSearchCriteria(GDeviceTypeSearchCriteria grpc)
			throws SiteWhereException {
		DeviceTypeSearchCriteria criteria = new DeviceTypeSearchCriteria(grpc.getPaging().getPageNumber(),
				grpc.getPaging().getPageSize());
		if (StringUtils.isNotEmpty(criteria.getParentDeviceTypeToken())) {
			criteria.setParentDeviceTypeToken(grpc.getParentDeviceTypeToken());
		}
		return criteria;
	}

    /**
     * Convert device type search results from GRPC to API.
     * 
     * @param response
     * @return
     * @throws SiteWhereException
     */
    public static ISearchResults<IDeviceType> asApiDeviceTypeSearchResults(GDeviceTypeSearchResults response)
	    throws SiteWhereException {
	List<IDeviceType> results = new ArrayList<IDeviceType>();
	for (GDeviceType grpc : response.getDeviceTypesList()) {
	    results.add(DeviceModelConverter.asApiDeviceType(grpc));
	}
	return new SearchResults<IDeviceType>(results, response.getCount());
    }

    /**
     * Convert device type create request from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static IDeviceTypeCreateRequest asApiDeviceTypeCreateRequest(GDeviceTypeCreateRequest grpc)
	    throws SiteWhereException {
	DeviceTypeCreateRequest api = new DeviceTypeCreateRequest();
	api.setToken(grpc.hasToken() ? grpc.getToken().getValue() : null);
	api.setName(grpc.hasName() ? grpc.getName().getValue() : null);
	api.setDescription(grpc.hasDescription() ? grpc.getDescription().getValue() : null);
	api.setContainerPolicy(DeviceModelConverter.asApiDeviceContainerPolicy(grpc.getContainerPolicy()));
	api.setDeviceElementSchema(DeviceModelConverter.asApiDeviceElementSchema(grpc.getDeviceElementSchema()));
	api.setMetadata(grpc.getMetadataMap());
		api.setReversedMessageType(DeviceModelConverter.asApiDeviceReversedMessageType(grpc.getReversedMessageType()));
		api.setParentDeviceTypeToken(StringUtils.isNotEmpty(grpc.getParentDeviceTypeToken()) ? grpc.getParentDeviceTypeToken() : null);
	CommonModelConverter.setBrandingInformation(api, grpc.getBranding());
	return api;
    }

    /**
     * Convert device type create request from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceTypeCreateRequest asGrpcDeviceTypeCreateRequest(IDeviceTypeCreateRequest api)
	    throws SiteWhereException {
	GDeviceTypeCreateRequest.Builder grpc = GDeviceTypeCreateRequest.newBuilder();
	if (api.getToken() != null) {
	    grpc.setToken(GOptionalString.newBuilder().setValue(api.getToken()));
	}
	if (api.getName() != null) {
	    grpc.setName(GOptionalString.newBuilder().setValue(api.getName()));
	}
	if (api.getDescription() != null) {
	    grpc.setDescription(GOptionalString.newBuilder().setValue(api.getDescription()));
	}
	grpc.setContainerPolicy(DeviceModelConverter.asGrpcDeviceContainerPolicy(api.getContainerPolicy()));
	if (api.getDeviceElementSchema() != null) {
	    grpc.setDeviceElementSchema(DeviceModelConverter.asGrpcDeviceElementSchema(api.getDeviceElementSchema()));
	}
	grpc.putAllMetadata(api.getMetadata());
	if	(api.getReversedMessageType() != null) {
		grpc.setReversedMessageType(DeviceModelConverter.asGrpcDeviceTypeReversedMessageType(api.getReversedMessageType()));
	}

	if (StringUtils.isNotEmpty(api.getParentDeviceTypeToken())) {
		grpc.setParentDeviceTypeToken(api.getParentDeviceTypeToken());
	}
	grpc.setBranding(CommonModelConverter.asGrpcBrandingInformation(api));
	return grpc.build();
    }

    /**
     * Convert device type from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static IDeviceType asApiDeviceType(GDeviceType grpc) throws SiteWhereException {
	DeviceType api = new DeviceType();
	api.setName(grpc.getName());
	api.setDescription(grpc.getDescription());
	api.setContainerPolicy(DeviceModelConverter.asApiDeviceContainerPolicy(grpc.getContainerPolicy()));
	api.setDeviceElementSchema(DeviceModelConverter.asApiDeviceElementSchema(grpc.getDeviceElementSchema()));
	CommonModelConverter.setEntityInformation(api, grpc.getEntityInformation());
	CommonModelConverter.setBrandingInformation(api, grpc.getBranding());
		api.setReversedMessageType(DeviceModelConverter.asApiDeviceReversedMessageType(grpc.getReversedMessageType()));
		api.setParentDeviceTypeToken(StringUtils.isNotEmpty(grpc.getParentDeviceTypeToken()) ? grpc.getParentDeviceTypeToken() : null);
	return api;
    }

    /**
     * Convert device type from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceType asGrpcDeviceType(IDeviceType api) throws SiteWhereException {
	GDeviceType.Builder grpc = GDeviceType.newBuilder();
	grpc.setName(api.getName());
	grpc.setDescription(api.getDescription());
	grpc.setContainerPolicy(DeviceModelConverter.asGrpcDeviceContainerPolicy(api.getContainerPolicy()));
	if (api.getDeviceElementSchema() != null) {
	    grpc.setDeviceElementSchema(DeviceModelConverter.asGrpcDeviceElementSchema(api.getDeviceElementSchema()));
	}
	grpc.setEntityInformation(CommonModelConverter.asGrpcEntityInformation(api));
	grpc.setBranding(CommonModelConverter.asGrpcBrandingInformation(api));
		if	(api.getReversedMessageType() != null) {
			grpc.setReversedMessageType(DeviceModelConverter.asGrpcDeviceTypeReversedMessageType(api.getReversedMessageType()));
		}
		if (StringUtils.isNotEmpty(api.getParentDeviceTypeToken())) {
			grpc.setParentDeviceTypeToken(api.getParentDeviceTypeToken());
		}
	return grpc.build();
    }

    /**
     * Convert parameter type from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static ParameterType asApiParameterType(GParameterType grpc) throws SiteWhereException {
	switch (grpc) {
	case PARAMETER_BOOL:
	    return ParameterType.Bool;
	case PARAMETER_BYTES:
	    return ParameterType.Bytes;
	case PARAMETER_DOUBLE:
	    return ParameterType.Double;
	case PARAMETER_FIXED32:
	    return ParameterType.Fixed32;
	case PARAMETER_FIXED64:
	    return ParameterType.Fixed64;
	case PARAMETER_FLOAT:
	    return ParameterType.Float;
	case PARAMETER_INT32:
	    return ParameterType.Int32;
	case PARAMETER_INT64:
	    return ParameterType.Int64;
	case PARAMETER_SFIXED32:
	    return ParameterType.SFixed32;
	case PARAMETER_SFIXED64:
	    return ParameterType.SFixed64;
	case PARAMETER_SINT32:
	    return ParameterType.SInt32;
	case PARAMETER_SINT64:
	    return ParameterType.SInt64;
	case PARAMETER_STRING:
	    return ParameterType.String;
	case PARAMETER_UINT32:
	    return ParameterType.UInt32;
	case PARAMETER_UINT64:
	    return ParameterType.UInt64;
	case UNRECOGNIZED:
	    throw new SiteWhereException(ErrorCode.Error, "Unknown parameter type: " + grpc.name());
	}
	return null;
    }

    /**
     * Convert parameter type from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GParameterType asGrpcParameterType(ParameterType api) throws SiteWhereException {
	switch (api) {
	case Bool:
	    return GParameterType.PARAMETER_BOOL;
	case Bytes:
	    return GParameterType.PARAMETER_BYTES;
	case Double:
	    return GParameterType.PARAMETER_DOUBLE;
	case Fixed32:
	    return GParameterType.PARAMETER_FIXED32;
	case Fixed64:
	    return GParameterType.PARAMETER_FIXED64;
	case Float:
	    return GParameterType.PARAMETER_FLOAT;
	case Int32:
	    return GParameterType.PARAMETER_INT32;
	case Int64:
	    return GParameterType.PARAMETER_INT64;
	case SFixed32:
	    return GParameterType.PARAMETER_SFIXED32;
	case SFixed64:
	    return GParameterType.PARAMETER_SFIXED64;
	case SInt32:
	    return GParameterType.PARAMETER_SINT32;
	case SInt64:
	    return GParameterType.PARAMETER_SINT64;
	case String:
	    return GParameterType.PARAMETER_STRING;
	case UInt32:
	    return GParameterType.PARAMETER_UINT32;
	case UInt64:
	    return GParameterType.PARAMETER_UINT64;
	}
	throw new SiteWhereException(ErrorCode.Error, "Unknown parameter type: " + api.name());
    }

    /**
     * Convert command parameter from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static CommandParameter asApiCommandParameter(GCommandParameter grpc) throws SiteWhereException {
	CommandParameter api = new CommandParameter();
	api.setName(grpc.getName());
	api.setRequired(grpc.getRequired());
	api.setType(DeviceModelConverter.asApiParameterType(grpc.getType()));
	return api;
    }

    /**
     * Convert list of command parameters from GRPC to API.
     * 
     * @param grpcs
     * @return
     * @throws SiteWhereException
     */
    public static List<CommandParameter> asApiCommandParameters(List<GCommandParameter> grpcs)
	    throws SiteWhereException {
	List<CommandParameter> api = new ArrayList<CommandParameter>();
	for (GCommandParameter grpc : grpcs) {
	    api.add(DeviceModelConverter.asApiCommandParameter(grpc));
	}
	return api;
    }

    /**
     * Convert command parameter from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GCommandParameter asGrpcCommandParameter(ICommandParameter api) throws SiteWhereException {
	GCommandParameter.Builder grpc = GCommandParameter.newBuilder();
	grpc.setName(api.getName());
	grpc.setRequired(api.isRequired());
	grpc.setType(DeviceModelConverter.asGrpcParameterType(api.getType()));
	return grpc.build();
    }

    /**
     * Convert command parameter from GRPC to API.
     * 
     * @param apis
     * @return
     * @throws SiteWhereException
     */
    public static List<GCommandParameter> asGrpcCommandParameters(List<ICommandParameter> apis)
	    throws SiteWhereException {
	List<GCommandParameter> grpcs = new ArrayList<GCommandParameter>();
	for (ICommandParameter api : apis) {
	    grpcs.add(DeviceModelConverter.asGrpcCommandParameter(api));
	}
	return grpcs;
    }

    /**
     * Convert device command search criteria from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceCommandSearchCriteria asApiDeviceCommandSearchCriteria(GDeviceCommandSearchCriteria grpc)
	    throws SiteWhereException {
	DeviceCommandSearchCriteria api = new DeviceCommandSearchCriteria(grpc.getPaging().getPageNumber(),
		grpc.getPaging().getPageSize());
	api.setDeviceTypeId(grpc.hasDeviceTypeId() ? CommonModelConverter.asApiUuid(grpc.getDeviceTypeId()) : null);
	return api;
    }

    /**
     * Convert device command search criteria from API to GRPC.
     * 
     * @param criteria
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceCommandSearchCriteria asGrpcDeviceCommandSearchCriteria(IDeviceCommandSearchCriteria api)
	    throws SiteWhereException {
	GDeviceCommandSearchCriteria.Builder gcriteria = GDeviceCommandSearchCriteria.newBuilder();
	if (api.getDeviceTypeId() != null) {
	    gcriteria.setDeviceTypeId(CommonModelConverter.asGrpcUuid(api.getDeviceTypeId()));
	}
	gcriteria.setPaging(CommonModelConverter.asGrpcPaging(api));
	return gcriteria.build();
    }

    /**
     * Convert device command search results from GRPC to API.
     * 
     * @param response
     * @return
     * @throws SiteWhereException
     */
    public static ISearchResults<IDeviceCommand> asApiDeviceCommandSearchResults(GDeviceCommandSearchResults response)
	    throws SiteWhereException {
	List<IDeviceCommand> results = new ArrayList<IDeviceCommand>();
	for (GDeviceCommand grpc : response.getDeviceCommandsList()) {
	    results.add(DeviceModelConverter.asApiDeviceCommand(grpc));
	}
	return new SearchResults<IDeviceCommand>(results, response.getCount());
    }

    /**
     * Convert device command create request from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceCommandCreateRequest asApiDeviceCommandCreateRequest(GDeviceCommandCreateRequest grpc)
	    throws SiteWhereException {
	DeviceCommandCreateRequest api = new DeviceCommandCreateRequest();
	api.setToken(grpc.hasToken() ? grpc.getToken().getValue() : null);
	api.setDeviceTypeToken(grpc.hasDeviceTypeToken() ? grpc.getDeviceTypeToken().getValue() : null);
	api.setName(grpc.hasName() ? grpc.getName().getValue() : null);
	api.setDescription(grpc.hasDescription() ? grpc.getDescription().getValue() : null);
	api.setNamespace(grpc.hasNamespace() ? grpc.getNamespace().getValue() : null);
	api.setParameters(DeviceModelConverter.asApiCommandParameters(grpc.getParametersList()));
	api.setMetadata(grpc.getMetadataMap());
		api.setCommandRoute(grpc.hasCommandRoute() ? grpc.getCommandRoute().getValue() : null);
	return api;
    }

    /**
     * Convert device command create request from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceCommandCreateRequest asGrpcDeviceCommandCreateRequest(IDeviceCommandCreateRequest api)
	    throws SiteWhereException {
	GDeviceCommandCreateRequest.Builder grpc = GDeviceCommandCreateRequest.newBuilder();
	if (api.getToken() != null) {
	    grpc.setToken(GOptionalString.newBuilder().setValue(api.getToken()));
	}
	if (api.getDeviceTypeToken() != null) {
	    grpc.setDeviceTypeToken(GOptionalString.newBuilder().setValue(api.getDeviceTypeToken()));
	}
	if (api.getName() != null) {
	    grpc.setName(GOptionalString.newBuilder().setValue(api.getName()));
	}
	if (api.getDescription() != null) {
	    grpc.setDescription(GOptionalString.newBuilder().setValue(api.getDescription()));
	}
	if (api.getNamespace() != null) {
	    grpc.setNamespace(GOptionalString.newBuilder().setValue(api.getNamespace()));
	}
	grpc.addAllParameters(DeviceModelConverter.asGrpcCommandParameters(api.getParameters()));
	if (api.getMetadata() != null) {
	    grpc.putAllMetadata(api.getMetadata());
	}
	if (api.getCommandRoute() != null) {
		grpc.setCommandRoute(GOptionalString.newBuilder().setValue(api.getCommandRoute()));
	}
	return grpc.build();
    }

    /**
     * Convert device command from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceCommand asApiDeviceCommand(GDeviceCommand grpc) throws SiteWhereException {
	DeviceCommand api = new DeviceCommand();
	api.setDeviceTypeId(CommonModelConverter.asApiUuid(grpc.getDeviceTypeId()));
	api.setName(grpc.getName());
	api.setDescription(grpc.getDescription());
	api.setNamespace(grpc.getNamespace());
	api.setParameters(DeviceModelConverter.asApiCommandParameters(grpc.getParametersList()));
		api.setCommandRoute(grpc.hasCommandRoute() ? grpc.getCommandRoute().getValue() : null);
	CommonModelConverter.setEntityInformation(api, grpc.getEntityInformation());
	return api;
    }

    /**
     * Convert device command from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceCommand asGrpcDeviceCommand(IDeviceCommand api) throws SiteWhereException {
	GDeviceCommand.Builder grpc = GDeviceCommand.newBuilder();
	grpc.setDeviceTypeId(CommonModelConverter.asGrpcUuid(api.getDeviceTypeId()));
	grpc.setName(api.getName());
	grpc.setDescription(api.getDescription());
	grpc.setNamespace(api.getNamespace());
	grpc.addAllParameters(DeviceModelConverter.asGrpcCommandParameters(api.getParameters()));
	grpc.setEntityInformation(CommonModelConverter.asGrpcEntityInformation(api));
		if (api.getCommandRoute() != null) {
			grpc.setCommandRoute(GOptionalString.newBuilder().setValue(api.getCommandRoute()));
		}
	return grpc.build();
    }

    /**
     * Convert device status search criteria from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceStatusSearchCriteria asApiDeviceStatusSearchCriteria(GDeviceStatusSearchCriteria grpc)
	    throws SiteWhereException {
	DeviceStatusSearchCriteria api = new DeviceStatusSearchCriteria(grpc.getPaging().getPageNumber(),
		grpc.getPaging().getPageSize());
	api.setDeviceTypeId(grpc.hasDeviceTypeId() ? CommonModelConverter.asApiUuid(grpc.getDeviceTypeId()) : null);
	api.setCode(grpc.hasCode() ? grpc.getCode().getValue() : null);
	return api;
    }

    /**
     * Convert device status search criteria from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceStatusSearchCriteria asGrpcDeviceStatusSearchCriteria(IDeviceStatusSearchCriteria api)
	    throws SiteWhereException {
	GDeviceStatusSearchCriteria.Builder gcriteria = GDeviceStatusSearchCriteria.newBuilder();
	if (api.getDeviceTypeId() != null) {
	    gcriteria.setDeviceTypeId(CommonModelConverter.asGrpcUuid(api.getDeviceTypeId()));
	}
	if (api.getCode() != null) {
	    gcriteria.setCode(GOptionalString.newBuilder().setValue(api.getCode()).build());
	}
	gcriteria.setPaging(CommonModelConverter.asGrpcPaging(api));
	return gcriteria.build();
    }

    /**
     * Convert device status search results from GRPC to API.
     * 
     * @param response
     * @return
     * @throws SiteWhereException
     */
    public static ISearchResults<IDeviceStatus> asApiDeviceStatusSearchResults(GDeviceStatusSearchResults response)
	    throws SiteWhereException {
	List<IDeviceStatus> results = new ArrayList<IDeviceStatus>();
	for (GDeviceStatus grpc : response.getDeviceStatusesList()) {
	    results.add(DeviceModelConverter.asApiDeviceStatus(grpc));
	}
	return new SearchResults<IDeviceStatus>(results, response.getCount());
    }

    /**
     * Convert device status create request from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceStatusCreateRequest asApiDeviceStatusCreateRequest(GDeviceStatusCreateRequest grpc)
	    throws SiteWhereException {
	DeviceStatusCreateRequest api = new DeviceStatusCreateRequest();
	api.setToken(grpc.hasToken() ? grpc.getToken().getValue() : null);
	api.setDeviceTypeToken(grpc.hasDeviceTypeToken() ? grpc.getDeviceTypeToken().getValue() : null);
	api.setCode(grpc.hasCode() ? grpc.getCode().getValue() : null);
	api.setName(grpc.hasName() ? grpc.getName().getValue() : null);
	api.setBackgroundColor(grpc.hasBackgroundColor() ? grpc.getBackgroundColor().getValue() : null);
	api.setForegroundColor(grpc.hasForegroundColor() ? grpc.getForegroundColor().getValue() : null);
	api.setBorderColor(grpc.hasBorderColor() ? grpc.getBorderColor().getValue() : null);
	api.setIcon(grpc.hasIcon() ? grpc.getIcon().getValue() : null);
	api.setMetadata(grpc.getMetadataMap());
	return api;
    }

    /**
     * Convert device status create request from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceStatusCreateRequest asGrpcDeviceStatusCreateRequest(IDeviceStatusCreateRequest api)
	    throws SiteWhereException {
	GDeviceStatusCreateRequest.Builder grpc = GDeviceStatusCreateRequest.newBuilder();
	if (api.getToken() != null) {
	    grpc.setToken(GOptionalString.newBuilder().setValue(api.getToken()));
	}
	if (api.getDeviceTypeToken() != null) {
	    grpc.setDeviceTypeToken(GOptionalString.newBuilder().setValue(api.getDeviceTypeToken()));
	}
	if (api.getCode() != null) {
	    grpc.setCode(GOptionalString.newBuilder().setValue(api.getCode()));
	}
	if (api.getName() != null) {
	    grpc.setName(GOptionalString.newBuilder().setValue(api.getName()));
	}
	if (api.getBackgroundColor() != null) {
	    grpc.setBackgroundColor(GOptionalString.newBuilder().setValue(api.getBackgroundColor()));
	}
	if (api.getForegroundColor() != null) {
	    grpc.setForegroundColor(GOptionalString.newBuilder().setValue(api.getForegroundColor()));
	}
	if (api.getBorderColor() != null) {
	    grpc.setBorderColor(GOptionalString.newBuilder().setValue(api.getBorderColor()));
	}
	if (api.getIcon() != null) {
	    grpc.setIcon(GOptionalString.newBuilder().setValue(api.getIcon()));
	}
	if (api.getMetadata() != null) {
	    grpc.putAllMetadata(api.getMetadata());
	}
	return grpc.build();
    }

    /**
     * Convert device status from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceStatus asApiDeviceStatus(GDeviceStatus grpc) throws SiteWhereException {
	DeviceStatus api = new DeviceStatus();
	api.setDeviceTypeId(CommonModelConverter.asApiUuid(grpc.getDeviceTypeId()));
	api.setCode(grpc.getCode());
	api.setName(grpc.getName());
	api.setDeviceTypeId(CommonModelConverter.asApiUuid(grpc.getDeviceTypeId()));
	api.setBackgroundColor(grpc.getBackgroundColor());
	api.setForegroundColor(grpc.getForegroundColor());
	api.setBorderColor(grpc.getBorderColor());
	api.setIcon(grpc.getIcon());
	CommonModelConverter.setEntityInformation(api, grpc.getEntityInformation());
	return api;
    }

    /**
     * Convert device status from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceStatus asGrpcDeviceStatus(IDeviceStatus api) throws SiteWhereException {
	GDeviceStatus.Builder grpc = GDeviceStatus.newBuilder();
	grpc.setDeviceTypeId(CommonModelConverter.asGrpcUuid(api.getDeviceTypeId()));
	grpc.setCode(api.getCode());
	grpc.setName(api.getName());
	grpc.setDeviceTypeId(CommonModelConverter.asGrpcUuid(api.getDeviceTypeId()));
	grpc.setBackgroundColor(api.getBackgroundColor());
	grpc.setForegroundColor(api.getForegroundColor());
	grpc.setBorderColor(api.getBorderColor());
	grpc.setIcon(api.getIcon());
	grpc.setEntityInformation(CommonModelConverter.asGrpcEntityInformation(api));
	return grpc.build();
    }

    /**
     * Convert device element mapping from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceElementMapping asApiDeviceElementMapping(GDeviceElementMapping grpc) throws SiteWhereException {
	DeviceElementMapping api = new DeviceElementMapping();
	api.setDeviceToken(grpc.getDeviceToken());
	api.setDeviceElementSchemaPath(grpc.getSchemaPath());
	return api;
    }

    /**
     * Convert list of device element mappgings from GRPC to API.
     * 
     * @param grpcs
     * @return
     * @throws SiteWhereException
     */
    public static List<DeviceElementMapping> asApiDeviceElementMappings(List<GDeviceElementMapping> grpcs)
	    throws SiteWhereException {
	List<DeviceElementMapping> api = new ArrayList<DeviceElementMapping>();
	for (GDeviceElementMapping grpc : grpcs) {
	    api.add(DeviceModelConverter.asApiDeviceElementMapping(grpc));
	}
	return api;
    }

    /**
     * Convert device element mapping from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceElementMapping asGrpcDeviceElementMapping(IDeviceElementMapping api)
	    throws SiteWhereException {
	GDeviceElementMapping.Builder grpc = GDeviceElementMapping.newBuilder();
	grpc.setDeviceToken(api.getDeviceToken());
	grpc.setSchemaPath(api.getDeviceElementSchemaPath());
	return grpc.build();
    }

    /**
     * Convert list of device element mappings from API to GRPC.
     * 
     * @param apis
     * @return
     * @throws SiteWhereException
     */
    public static List<GDeviceElementMapping> asGrpcDeviceElementMappings(List<? extends IDeviceElementMapping> apis)
	    throws SiteWhereException {
	List<GDeviceElementMapping> grpcs = new ArrayList<GDeviceElementMapping>();
	if (apis != null) {
	    for (IDeviceElementMapping api : apis) {
		grpcs.add(DeviceModelConverter.asGrpcDeviceElementMapping(api));
	    }
	}
	return grpcs;
    }

    /**
     * Convert device search results from GRPC to API.
     * 
     * @param response
     * @return
     * @throws SiteWhereException
     */
    public static ISearchResults<IDevice> asApiDeviceSearchResults(GDeviceSearchResults response)
	    throws SiteWhereException {
	List<IDevice> results = new ArrayList<IDevice>();
	for (GDevice grpc : response.getDevicesList()) {
	    results.add(DeviceModelConverter.asApiDevice(grpc));
	}
	return new SearchResults<IDevice>(results, response.getCount());
    }

    /**
     * Convert device registration request from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceRegistrationRequest asApiDeviceRegistrationRequest(GDeviceRegistrationRequest grpc)
	    throws SiteWhereException {
	DeviceRegistrationRequest api = new DeviceRegistrationRequest();
	api.setToken(grpc.hasToken() ? grpc.getToken().getValue() : null);
	api.setParentDeviceToken(grpc.hasParentDeviceToken() ? grpc.getParentDeviceToken().getValue() : null);
	api.setDeviceTypeToken(grpc.hasDeviceTypeToken() ? grpc.getDeviceTypeToken().getValue() : null);
	api.setStatus(grpc.hasStatus() ? grpc.getStatus().getValue() : null);
	api.setComments(grpc.hasComments() ? grpc.getComments().getValue() : null);
	api.setDeviceElementMappings(
		DeviceModelConverter.asApiDeviceElementMappings(grpc.getDeviceElementMappingsList()));
		api.setGatewayId(grpc.hasGatewayId() ? grpc.getGatewayId().getValue() : null);
		api.setHardwareId(grpc.hasHardwareId() ? grpc.getHardwareId().getValue() : null);
		Map<String, List<String>> listItemNameMap = new HashMap<>();
		for (String channel : grpc.getItemChannelLinkMap().keySet()) {
			GListItemName list = grpc.getItemChannelLinkMap().get(channel);
			List<String> listItemName = new ArrayList<>();
			listItemName.addAll(list.getItemNamesList());
			listItemNameMap.put(channel, listItemName);
		}
		api.setItemChannelLink(listItemNameMap);
		api.setConfigurationGateway(grpc.getConfigurationGatewayMap());
		api.setDelete(grpc.hasDelete() ? grpc.getDelete().getValue() : false);
	api.setMetadata(grpc.getMetadataMap());
	api.setCustomerToken(grpc.hasCustomerToken() ? grpc.getCustomerToken().getValue() : null);
	api.setAreaToken(grpc.hasAreaToken() ? grpc.getAreaToken().getValue() : null);
	return api;
    }

    /**
     * Convert device registration request from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceRegistrationRequest asGrpcDeviceRegistrationRequest(IDeviceRegistrationRequest api)
	    throws SiteWhereException {
	GDeviceRegistrationRequest.Builder grpc = GDeviceRegistrationRequest.newBuilder();
	if (api.getToken() != null) {
	    grpc.setToken(GOptionalString.newBuilder().setValue(api.getToken()));
	}
	if (api.getParentDeviceToken() != null) {
	    grpc.setParentDeviceToken(GOptionalString.newBuilder().setValue(api.getParentDeviceToken()));
	}
	if (api.getDeviceTypeToken() != null) {
	    grpc.setDeviceTypeToken(GOptionalString.newBuilder().setValue(api.getDeviceTypeToken()));
	}
	if (api.getStatus() != null) {
	    grpc.setStatus(GOptionalString.newBuilder().setValue(api.getStatus()));
	}
	if (api.getComments() != null) {
	    grpc.setComments(GOptionalString.newBuilder().setValue(api.getComments()));
	}
	grpc.addAllDeviceElementMappings(
		DeviceModelConverter.asGrpcDeviceElementMappings(api.getDeviceElementMappings()));

		if (api.getGatewayId() != null) {
			grpc.setGatewayId(GOptionalString.newBuilder().setValue(api.getGatewayId()));
		}

		if (api.getHardwareId() != null) {
			grpc.setHardwareId(GOptionalString.newBuilder().setValue(api.getHardwareId()));
		}

		if (api.getItemChannelLink() != null) {
			for (String channel : api.getItemChannelLink().keySet()) {
				GListItemName.Builder listItemName = GListItemName.newBuilder();
				for (String itemName : api.getItemChannelLink().get(channel)) {
					listItemName.addItemNames(itemName);
				}
				grpc.putItemChannelLink(channel, listItemName.build());
			}
		}

		if (api.getConfigurationGateway() != null) {
			for (String key : api.getConfigurationGateway().keySet()) {
				grpc.putConfigurationGateway(key, api.getConfigurationGateway().get(key));
			}
		}

		grpc.setDelete(GOptionalBoolean.newBuilder().setValue(api.isDelete()));

	if (api.getMetadata() != null) {
	    grpc.putAllMetadata(api.getMetadata());
	}
	if (api.getCustomerToken() != null) {
	    grpc.setCustomerToken(GOptionalString.newBuilder().setValue(api.getCustomerToken()));
	}
	if (api.getAreaToken() != null) {
	    grpc.setAreaToken(GOptionalString.newBuilder().setValue(api.getAreaToken()));
	}
	return grpc.build();
    }

    /**
     * Convert device create request from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceCreateRequest asApiDeviceCreateRequest(GDeviceCreateRequest grpc) throws SiteWhereException {
	DeviceCreateRequest api = new DeviceCreateRequest();
	api.setToken(grpc.hasToken() ? grpc.getToken().getValue() : null);
	api.setParentDeviceToken(grpc.hasParentDeviceToken() ? grpc.getParentDeviceToken().getValue() : null);
	api.setDeviceTypeToken(grpc.hasDeviceTypeToken() ? grpc.getDeviceTypeToken().getValue() : null);
	api.setStatus(grpc.hasStatus() ? grpc.getStatus().getValue() : null);
	api.setComments(grpc.hasComments() ? grpc.getComments().getValue() : null);
	api.setDeviceElementMappings(
		DeviceModelConverter.asApiDeviceElementMappings(grpc.getDeviceElementMappingsList()));
		api.setGatewayId(grpc.hasGatewayId() ? grpc.getGatewayId().getValue() : null);
		api.setHardwareId(grpc.hasHardwareId() ? grpc.getHardwareId().getValue() : null);
		Map<String, List<String>> listItemNameMap = new HashMap<>();
		for (String channel : grpc.getItemChannelLinkMap().keySet()) {
			GListItemName list = grpc.getItemChannelLinkMap().get(channel);
			List<String> listItemName = new ArrayList<>();
			listItemName.addAll(list.getItemNamesList());
			listItemNameMap.put(channel, listItemName);
		}
		api.setItemChannelLink(listItemNameMap);
		api.setConfigurationGateway(grpc.getConfigurationGatewayMap());
		api.setDelete(grpc.hasDelete() ? grpc.getDelete().getValue() : false);
	api.setMetadata(grpc.getMetadataMap());
	return api;
    }

    /**
     * Convert device create request from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceCreateRequest asGrpcDeviceCreateRequest(IDeviceCreateRequest api) throws SiteWhereException {
	GDeviceCreateRequest.Builder grpc = GDeviceCreateRequest.newBuilder();
	if (api.getToken() != null) {
	    grpc.setToken(GOptionalString.newBuilder().setValue(api.getToken()));
	}
	if (api.getParentDeviceToken() != null) {
	    grpc.setParentDeviceToken(GOptionalString.newBuilder().setValue(api.getParentDeviceToken()));
	}
	if (api.getDeviceTypeToken() != null) {
	    grpc.setDeviceTypeToken(GOptionalString.newBuilder().setValue(api.getDeviceTypeToken()));
	}
	if (api.getStatus() != null) {
	    grpc.setStatus(GOptionalString.newBuilder().setValue(api.getStatus()));
	}
	if (api.getComments() != null) {
	    grpc.setComments(GOptionalString.newBuilder().setValue(api.getComments()));
	}

		if (api.getGatewayId() != null) {
			grpc.setGatewayId(GOptionalString.newBuilder().setValue(api.getGatewayId()));
		}
		if (api.getHardwareId() != null) {
			grpc.setHardwareId(GOptionalString.newBuilder().setValue(api.getHardwareId()));
		}

		if (api.getItemChannelLink() != null) {
			for (String channel : api.getItemChannelLink().keySet()) {
				GListItemName.Builder listItemName = GListItemName.newBuilder();
				for (String itemName : api.getItemChannelLink().get(channel)) {
					listItemName.addItemNames(itemName);
				}
				grpc.putItemChannelLink(channel, listItemName.build());
			}
		}
		if (api.getConfigurationGateway() != null) {
			for (String key : api.getConfigurationGateway().keySet()) {
				grpc.putConfigurationGateway(key, api.getConfigurationGateway().get(key));
			}
		}
		grpc.setDelete(GOptionalBoolean.newBuilder().setValue(api.isDelete()));
	grpc.addAllDeviceElementMappings(
		DeviceModelConverter.asGrpcDeviceElementMappings(api.getDeviceElementMappings()));
	if (api.getMetadata() != null) {
	    grpc.putAllMetadata(api.getMetadata());
	}
	return grpc.build();
    }

    /**
     * Convert device from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static Device asApiDevice(GDevice grpc) throws SiteWhereException {
	Device api = new Device();
	api.setDeviceTypeId(CommonModelConverter.asApiUuid(grpc.getDeviceTypeId()));
	api.setStatus(grpc.hasStatus() ? grpc.getStatus().getValue() : null);
	api.setDeviceAssignmentId(
		grpc.hasDeviceAssignmentId() ? CommonModelConverter.asApiUuid(grpc.getDeviceAssignmentId()) : null);
	api.setParentDeviceId(
		grpc.hasParentDeviceId() ? CommonModelConverter.asApiUuid(grpc.getParentDeviceId()) : null);
	api.setComments(grpc.hasComments() ? grpc.getComments().getValue() : null);
	api.setDeviceElementMappings(
		DeviceModelConverter.asApiDeviceElementMappings(grpc.getDeviceElementMappingsList()));
		api.setGatewayId(grpc.hasGatewayId() ? grpc.getGatewayId().getValue() : null);
		api.setHardwareId(grpc.hasHardwareId() ? grpc.getHardwareId().getValue() : null);
		Map<String, List<String>> listItemNameMap = new HashMap<>();
		for (String channel : grpc.getItemChannelLinkMap().keySet()) {
			GListItemName list = grpc.getItemChannelLinkMap().get(channel);
			List<String> listItemName = new ArrayList<>();
			listItemName.addAll(list.getItemNamesList());
			listItemNameMap.put(channel, listItemName);
		}
		api.setItemChannelLink(listItemNameMap);
		api.setConfigurationGateway(grpc.getConfigurationGatewayMap());
		api.setDelete(grpc.hasDelete() ? grpc.getDelete().getValue() : false);
		api.setCounter(grpc.getCounter());
	CommonModelConverter.setEntityInformation(api, grpc.getEntityInformation());
	return api;
    }

    /**
     * Convert device from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDevice asGrpcDevice(IDevice api) throws SiteWhereException {
	GDevice.Builder grpc = GDevice.newBuilder();
	if (api.getParentDeviceId() != null) {
	    grpc.setParentDeviceId(CommonModelConverter.asGrpcUuid(api.getParentDeviceId()));
	}
	grpc.setDeviceTypeId(CommonModelConverter.asGrpcUuid(api.getDeviceTypeId()));
	if (api.getStatus() != null) {
	    grpc.setStatus(GOptionalString.newBuilder().setValue(api.getStatus()).build());
	}
	if (api.getDeviceAssignmentId() != null) {
	    grpc.setDeviceAssignmentId(CommonModelConverter.asGrpcUuid(api.getDeviceAssignmentId()));
	}
	if (api.getComments() != null) {
	    grpc.setComments(GOptionalString.newBuilder().setValue(api.getComments()).build());
	}

		if (api.getGatewayId() != null) {
			grpc.setGatewayId(GOptionalString.newBuilder().setValue(api.getGatewayId()));
		}

		if (api.getHardwareId() != null) {
			grpc.setHardwareId(GOptionalString.newBuilder().setValue(api.getHardwareId()));
		}

		if (api.getItemChannelLink() != null) {
			for (String channel : api.getItemChannelLink().keySet()) {
				GListItemName.Builder listItemName = GListItemName.newBuilder();
				for (String itemName : api.getItemChannelLink().get(channel)) {
					listItemName.addItemNames(itemName);
				}
				grpc.putItemChannelLink(channel, listItemName.build());
			}
		}
		if (api.getConfigurationGateway() != null) {
			for (String key : api.getConfigurationGateway().keySet()) {
				grpc.putConfigurationGateway(key, api.getConfigurationGateway().get(key));
			}
		}
		grpc.setDelete(GOptionalBoolean.newBuilder().setValue(api.isDelete()));
		grpc.setCounter(api.getCounter());
	grpc.addAllDeviceElementMappings(
		DeviceModelConverter.asGrpcDeviceElementMappings(api.getDeviceElementMappings()));
	grpc.setEntityInformation(CommonModelConverter.asGrpcEntityInformation(api));
	return grpc.build();
    }

    /**
     * Convert device search criteria from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceSearchCriteria asApiDeviceSearchCriteria(GDeviceSearchCriteria grpc) throws SiteWhereException {
	int pageNumber = grpc.hasPaging() ? grpc.getPaging().getPageNumber() : 1;
	int pageSize = grpc.hasPaging() ? grpc.getPaging().getPageSize() : 0;
	Date createdAfter = CommonModelConverter.asApiDate(grpc.getCreatedAfter());
	Date createdBefore = CommonModelConverter.asApiDate(grpc.getCreatedBefore());
	DeviceSearchCriteria api = new DeviceSearchCriteria(pageNumber, pageSize, createdAfter, createdBefore);
	api.setExcludeAssigned(grpc.hasExcludeAssigned() ? grpc.getExcludeAssigned().getValue() : false);
	api.setDeviceTypeToken(grpc.hasDeviceType() ? grpc.getDeviceType().getToken() : null);
	if (grpc.getDeviceTokensCount() > 0) {
		List<String> deviceTokens = grpc.getDeviceTokensOrBuilderList().stream().map(CommonModel.GOptionalStringOrBuilder::getValue).collect(Collectors.toList());
		api.setDeviceTokens(deviceTokens);
	}
 	return api;
    }

    /**
     * Convert device search criteria from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceSearchCriteria asGrpcDeviceSearchCriteria(IDeviceSearchCriteria api)
	    throws SiteWhereException {
	GDeviceSearchCriteria.Builder grpc = GDeviceSearchCriteria.newBuilder();
	grpc.setPaging(CommonModelConverter.asGrpcPaging(api));
	if (api.getDeviceTypeToken() != null) {
	    grpc.setDeviceType(GDeviceTypeReference.newBuilder().setToken(api.getDeviceTypeToken()).build());
	}
	if	(CollectionUtils.isNotEmpty(api.getDeviceTokens())) {
		api.getDeviceTokens().stream().map(deviceToken -> GOptionalString.newBuilder().setValue(deviceToken)).forEach(grpc::addDeviceTokens);
	}
	grpc.setCreatedAfter(CommonModelConverter.asGrpcDate(api.getStartDate()));
	grpc.setCreatedBefore(CommonModelConverter.asGrpcDate(api.getEndDate()));
	if (api.isExcludeAssigned()) {
	    grpc.setExcludeAssigned(GOptionalBoolean.newBuilder().setValue(true));
	}
	return grpc.build();
    }

    /**
     * Convert a device group create request from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceGroupCreateRequest asApiDeviceGroupCreateRequest(GDeviceGroupCreateRequest grpc)
	    throws SiteWhereException {
	DeviceGroupCreateRequest api = new DeviceGroupCreateRequest();
	api.setToken(grpc.hasToken() ? grpc.getToken().getValue() : null);
	api.setName(grpc.hasName() ? grpc.getName().getValue() : null);
	api.setDescription(grpc.hasDescription() ? grpc.getDescription().getValue() : null);
	api.setRoles(grpc.getRolesList());
	api.setMetadata(grpc.getMetadataMap());
	CommonModelConverter.setBrandingInformation(api, grpc.getBranding());
	return api;
    }

    /**
     * Convert a device group group request from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceGroupCreateRequest asGrpcDeviceGroupCreateRequest(IDeviceGroupCreateRequest api)
	    throws SiteWhereException {
	GDeviceGroupCreateRequest.Builder grpc = GDeviceGroupCreateRequest.newBuilder();
	if (api.getToken() != null) {
	    grpc.setToken(GOptionalString.newBuilder().setValue(api.getToken()));
	}
	if (api.getName() != null) {
	    grpc.setName(GOptionalString.newBuilder().setValue(api.getName()));
	}
	if (api.getDescription() != null) {
	    grpc.setDescription(GOptionalString.newBuilder().setValue(api.getDescription()));
	}
	grpc.addAllRoles(api.getRoles());
	if (api.getMetadata() != null) {
	    grpc.putAllMetadata(api.getMetadata());
	}
	grpc.setBranding(CommonModelConverter.asGrpcBrandingInformation(api));
	return grpc.build();
    }

    /**
     * Convert device group search criteria from API to GRPC.
     * 
     * @param code
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceGroupSearchCriteria asApiDeviceGroupSearchCriteria(ISearchCriteria criteria)
	    throws SiteWhereException {
	GDeviceGroupSearchCriteria.Builder gcriteria = GDeviceGroupSearchCriteria.newBuilder();
	gcriteria.setPaging(CommonModelConverter.asGrpcPaging(criteria));
	return gcriteria.build();
    }

    /**
     * Convert device groups with role search criteria from API to GRPC.
     * 
     * @param role
     * @param criteria
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceGroupsWithRoleSearchCriteria asApiDeviceGroupsWithRoleSearchCriteria(String role,
	    ISearchCriteria criteria) throws SiteWhereException {
	GDeviceGroupsWithRoleSearchCriteria.Builder gcriteria = GDeviceGroupsWithRoleSearchCriteria.newBuilder();
	gcriteria.setRole(role);
	gcriteria.setPaging(CommonModelConverter.asGrpcPaging(criteria));
	return gcriteria.build();
    }

    /**
     * Convert device group search results from GRPC to API.
     * 
     * @param response
     * @return
     * @throws SiteWhereException
     */
    public static ISearchResults<IDeviceGroup> asApiDeviceGroupSearchResults(GDeviceGroupSearchResults response)
	    throws SiteWhereException {
	List<IDeviceGroup> results = new ArrayList<IDeviceGroup>();
	for (GDeviceGroup grpc : response.getDeviceGroupsList()) {
	    results.add(DeviceModelConverter.asApiDeviceGroup(grpc));
	}
	return new SearchResults<IDeviceGroup>(results, response.getCount());
    }

    /**
     * Convert a device group from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceGroup asApiDeviceGroup(GDeviceGroup grpc) throws SiteWhereException {
	DeviceGroup api = new DeviceGroup();
	api.setName(grpc.getName());
	api.setDescription(grpc.getDescription());
	api.setRoles(grpc.getRolesList());
	CommonModelConverter.setEntityInformation(api, grpc.getEntityInformation());
	CommonModelConverter.setBrandingInformation(api, grpc.getBranding());
	return api;
    }

    /**
     * Convert a device group from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceGroup asGrpcDeviceGroup(IDeviceGroup api) throws SiteWhereException {
	GDeviceGroup.Builder grpc = GDeviceGroup.newBuilder();
	grpc.setName(api.getName());
	grpc.setDescription(api.getDescription());
	grpc.addAllRoles(api.getRoles());
	grpc.setEntityInformation(CommonModelConverter.asGrpcEntityInformation(api));
	grpc.setBranding(CommonModelConverter.asGrpcBrandingInformation(api));
	return grpc.build();
    }

    /**
     * Convert a device group element create request from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceGroupElementCreateRequest asApiDeviceGroupElementCreateRequest(
	    GDeviceGroupElementCreateRequest grpc) throws SiteWhereException {
	DeviceGroupElementCreateRequest api = new DeviceGroupElementCreateRequest();
	api.setDeviceToken(grpc.hasDeviceToken() ? grpc.getDeviceToken().getValue() : null);
	api.setNestedGroupToken(grpc.hasNestedGroupToken() ? grpc.getNestedGroupToken().getValue() : null);
	api.setRoles(grpc.getRolesList());
	return api;
    }

    /**
     * Convert a list of device group element create requests from GRPC to API.
     * 
     * @param grpcs
     * @return
     * @throws SiteWhereException
     */
    public static List<IDeviceGroupElementCreateRequest> asApiDeviceGroupElementCreateRequests(
	    List<GDeviceGroupElementCreateRequest> grpcs) throws SiteWhereException {
	List<IDeviceGroupElementCreateRequest> api = new ArrayList<IDeviceGroupElementCreateRequest>();
	for (GDeviceGroupElementCreateRequest grpc : grpcs) {
	    api.add(DeviceModelConverter.asApiDeviceGroupElementCreateRequest(grpc));
	}
	return api;
    }

    /**
     * Convert a device group element create request from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceGroupElementCreateRequest asGrpcDeviceGroupElementCreateRequest(
	    IDeviceGroupElementCreateRequest api) throws SiteWhereException {
	GDeviceGroupElementCreateRequest.Builder grpc = GDeviceGroupElementCreateRequest.newBuilder();
	if (api.getDeviceToken() != null) {
	    grpc.setDeviceToken(GOptionalString.newBuilder().setValue(api.getDeviceToken()));
	}
	if (api.getNestedGroupToken() != null) {
	    grpc.setNestedGroupToken(GOptionalString.newBuilder().setValue(api.getNestedGroupToken()));
	}
	grpc.addAllRoles(api.getRoles());
	return grpc.build();
    }

    /**
     * Convert a list of device group element create requests from API to GRPC.
     * 
     * @param apis
     * @return
     * @throws SiteWhereException
     */
    public static List<GDeviceGroupElementCreateRequest> asGrpcDeviceGroupElementCreateRequests(
	    List<IDeviceGroupElementCreateRequest> apis) throws SiteWhereException {
	List<GDeviceGroupElementCreateRequest> grpcs = new ArrayList<GDeviceGroupElementCreateRequest>();
	for (IDeviceGroupElementCreateRequest api : apis) {
	    grpcs.add(DeviceModelConverter.asGrpcDeviceGroupElementCreateRequest(api));
	}
	return grpcs;
    }

    /**
     * Convert device group element search criteria from API to GRPC.
     * 
     * @param code
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceGroupElementsSearchCriteria asApiDeviceGroupElementSearchCriteria(ISearchCriteria criteria)
	    throws SiteWhereException {
	GDeviceGroupElementsSearchCriteria.Builder gcriteria = GDeviceGroupElementsSearchCriteria.newBuilder();
	gcriteria.setPaging(CommonModelConverter.asGrpcPaging(criteria));
	return gcriteria.build();
    }

    /**
     * Convert device group element search results from GRPC to API.
     * 
     * @param response
     * @return
     * @throws SiteWhereException
     */
    public static ISearchResults<IDeviceGroupElement> asApiDeviceGroupElementsSearchResults(
	    GDeviceGroupElementsSearchResults response) throws SiteWhereException {
	List<IDeviceGroupElement> results = new ArrayList<IDeviceGroupElement>();
	for (GDeviceGroupElement grpc : response.getElementsList()) {
	    results.add(DeviceModelConverter.asApiDeviceGroupElement(grpc));
	}
	return new SearchResults<IDeviceGroupElement>(results, response.getCount());
    }

    /**
     * Convert a device group element from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceGroupElement asApiDeviceGroupElement(GDeviceGroupElement grpc) throws SiteWhereException {
	DeviceGroupElement api = new DeviceGroupElement();
	api.setId(CommonModelConverter.asApiUuid(grpc.getId()));
	api.setGroupId(CommonModelConverter.asApiUuid(grpc.getGroupId()));
	api.setDeviceId(grpc.hasDeviceId() ? CommonModelConverter.asApiUuid(grpc.getDeviceId()) : null);
	api.setNestedGroupId(grpc.hasNestedGroupId() ? CommonModelConverter.asApiUuid(grpc.getNestedGroupId()) : null);
	api.setRoles(grpc.getRolesList());
	return api;
    }

    /**
     * Convert a device group element list from GRPC to API.
     * 
     * @param grpcs
     * @return
     * @throws SiteWhereException
     */
    public static List<IDeviceGroupElement> asApiDeviceGroupElements(List<GDeviceGroupElement> grpcs)
	    throws SiteWhereException {
	List<IDeviceGroupElement> api = new ArrayList<IDeviceGroupElement>();
	for (GDeviceGroupElement grpc : grpcs) {
	    api.add(DeviceModelConverter.asApiDeviceGroupElement(grpc));
	}
	return api;
    }

    /**
     * Convert a device group element from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceGroupElement asGrpcDeviceGroupElement(IDeviceGroupElement api) throws SiteWhereException {
	GDeviceGroupElement.Builder grpc = GDeviceGroupElement.newBuilder();
	grpc.setId(CommonModelConverter.asGrpcUuid(api.getId()));
	grpc.setGroupId(CommonModelConverter.asGrpcUuid(api.getGroupId()));
	if (api.getDeviceId() != null) {
	    grpc.setDeviceId(CommonModelConverter.asGrpcUuid(api.getDeviceId()));
	}
	if (api.getNestedGroupId() != null) {
	    grpc.setNestedGroupId(CommonModelConverter.asGrpcUuid(api.getNestedGroupId()));
	}
	grpc.addAllRoles(api.getRoles());
	return grpc.build();
    }

    /**
     * Convert device assignment search criteria from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceAssignmentSearchCriteria asApiDeviceAssignmentSearchCriteria(
	    GDeviceAssignmentSearchCriteria grpc) throws SiteWhereException {
	DeviceAssignmentSearchCriteria api = new DeviceAssignmentSearchCriteria(grpc.getPaging().getPageNumber(),
		grpc.getPaging().getPageSize());
	api.setStatus(CommonModelConverter.asApiDeviceAssignmentStatus(grpc.getStatus()));
	api.setDeviceId(grpc.hasDeviceId() ? CommonModelConverter.asApiUuid(grpc.getDeviceId()) : null);
	api.setDeviceTypeIds(CommonModelConverter.asApiUuids(grpc.getDeviceTypeIdsList()));
	api.setCustomerIds(CommonModelConverter.asApiUuids(grpc.getCustomerIdsList()));
	api.setAreaIds(CommonModelConverter.asApiUuids(grpc.getAreaIdsList()));
	api.setAssetIds(CommonModelConverter.asApiUuids(grpc.getAssetIdsList()));
	return api;
    }

    /**
     * Convert device assignment search criteria from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceAssignmentSearchCriteria asGrpcDeviceAssignmentSearchCriteria(
	    IDeviceAssignmentSearchCriteria api) throws SiteWhereException {
	GDeviceAssignmentSearchCriteria.Builder grpc = GDeviceAssignmentSearchCriteria.newBuilder();
	grpc.setStatus(CommonModelConverter.asGrpcDeviceAssignmentStatus(api.getStatus()));
	if (api.getDeviceId() != null) {
	    grpc.setDeviceId(CommonModelConverter.asGrpcUuid(api.getDeviceId()));
	}
	if (api.getDeviceTypeIds() != null) {
	    grpc.addAllDeviceTypeIds(CommonModelConverter.asGrpcUuids(api.getDeviceTypeIds()));
	}
	if (api.getCustomerIds() != null) {
	    grpc.addAllCustomerIds(CommonModelConverter.asGrpcUuids(api.getCustomerIds()));
	}
	if (api.getAreaIds() != null) {
	    grpc.addAllAreaIds(CommonModelConverter.asGrpcUuids(api.getAreaIds()));
	}
	if (api.getAssetIds() != null) {
	    grpc.addAllAssetIds(CommonModelConverter.asGrpcUuids(api.getAssetIds()));
	}
	grpc.setPaging(CommonModelConverter.asGrpcPaging(api));
	return grpc.build();
    }

    /**
     * Convert device assignment search results from GRPC to API.
     * 
     * @param response
     * @return
     * @throws SiteWhereException
     */
    public static ISearchResults<IDeviceAssignment> asApiDeviceAssignmentSearchResults(
	    GDeviceAssignmentSearchResults response) throws SiteWhereException {
	List<IDeviceAssignment> results = new ArrayList<IDeviceAssignment>();
	for (GDeviceAssignment grpc : response.getAssignmentsList()) {
	    results.add(DeviceModelConverter.asApiDeviceAssignment(grpc));
	}
	return new SearchResults<IDeviceAssignment>(results, response.getCount());
    }

    /**
     * Convert a device assignment create request from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceAssignmentCreateRequest asApiDeviceAssignmentCreateRequest(GDeviceAssignmentCreateRequest grpc)
	    throws SiteWhereException {
	DeviceAssignmentCreateRequest api = new DeviceAssignmentCreateRequest();
	api.setToken(grpc.hasToken() ? grpc.getToken().getValue() : null);
	api.setDeviceToken(grpc.hasDeviceToken() ? grpc.getDeviceToken().getValue() : null);
	api.setCustomerToken(grpc.hasCustomerToken() ? grpc.getCustomerToken().getValue() : null);
	api.setAreaToken(grpc.hasAreaToken() ? grpc.getAreaToken().getValue() : null);
	api.setAssetToken(grpc.hasAssetToken() ? grpc.getAssetToken().getValue() : null);
	api.setStatus(CommonModelConverter.asApiDeviceAssignmentStatus(grpc.getStatus()));
	api.setMetadata(grpc.getMetadataMap());
	return api;
    }

    /**
     * Convert a device assignment create request from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceAssignmentCreateRequest asGrpcDeviceAssignmentCreateRequest(IDeviceAssignmentCreateRequest api)
	    throws SiteWhereException {
	GDeviceAssignmentCreateRequest.Builder grpc = GDeviceAssignmentCreateRequest.newBuilder();
	if (api.getToken() != null) {
	    grpc.setToken(GOptionalString.newBuilder().setValue(api.getToken()));
	}
	if (api.getDeviceToken() != null) {
	    grpc.setDeviceToken(GOptionalString.newBuilder().setValue(api.getDeviceToken()));
	}
	if (api.getCustomerToken() != null) {
	    grpc.setCustomerToken(GOptionalString.newBuilder().setValue(api.getCustomerToken()));
	}
	if (api.getAreaToken() != null) {
	    grpc.setAreaToken(GOptionalString.newBuilder().setValue(api.getAreaToken()));
	}
	if (api.getAssetToken() != null) {
	    grpc.setAssetToken(GOptionalString.newBuilder().setValue(api.getAssetToken()));
	}
	if (api.getStatus() != null) {
	    grpc.setStatus(CommonModelConverter.asGrpcDeviceAssignmentStatus(api.getStatus()));
	}
	if (api.getMetadata() != null) {
	    grpc.putAllMetadata(api.getMetadata());
	}
	return grpc.build();
    }

    /**
     * Convert a device assignment from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceAssignment asApiDeviceAssignment(GDeviceAssignment grpc) throws SiteWhereException {
	DeviceAssignment api = new DeviceAssignment();
	api.setStatus(CommonModelConverter.asApiDeviceAssignmentStatus(grpc.getStatus()));
	api.setDeviceId(grpc.hasDeviceId() ? CommonModelConverter.asApiUuid(grpc.getDeviceId()) : null);
	api.setDeviceTypeId(grpc.hasDeviceTypeId() ? CommonModelConverter.asApiUuid(grpc.getDeviceTypeId()) : null);
	api.setCustomerId(grpc.hasCustomerId() ? CommonModelConverter.asApiUuid(grpc.getCustomerId()) : null);
	api.setAreaId(grpc.hasAreaId() ? CommonModelConverter.asApiUuid(grpc.getAreaId()) : null);
	api.setAssetId(grpc.hasAssetId() ? CommonModelConverter.asApiUuid(grpc.getAssetId()) : null);
	api.setActiveDate(CommonModelConverter.asApiDate(grpc.getActiveDate()));
	api.setReleasedDate(CommonModelConverter.asApiDate(grpc.getReleasedDate()));
	CommonModelConverter.setEntityInformation(api, grpc.getEntityInformation());
	return api;
    }

    /**
     * Convert a device assignment from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceAssignment asGrpcDeviceAssignment(IDeviceAssignment api) throws SiteWhereException {
	GDeviceAssignment.Builder grpc = GDeviceAssignment.newBuilder();
	grpc.setStatus(CommonModelConverter.asGrpcDeviceAssignmentStatus(api.getStatus()));
	if (api.getDeviceId() != null) {
		grpc.setDeviceId(CommonModelConverter.asGrpcUuid(api.getDeviceId()));
		grpc.setDeviceTypeId(CommonModelConverter.asGrpcUuid(api.getDeviceTypeId()));
	}
	if (api.getAreaId() != null) {
	    grpc.setAreaId(CommonModelConverter.asGrpcUuid(api.getAreaId()));
	}
	if (api.getAssetId() != null) {
	    grpc.setAssetId(CommonModelConverter.asGrpcUuid(api.getAssetId()));
	}
	if (api.getCustomerId() != null) {
		grpc.setCustomerId(CommonModelConverter.asGrpcUuid(api.getCustomerId()));
	}
	grpc.setActiveDate(CommonModelConverter.asGrpcDate(api.getActiveDate()));
	grpc.setReleasedDate(CommonModelConverter.asGrpcDate(api.getReleasedDate()));
	grpc.setEntityInformation(CommonModelConverter.asGrpcEntityInformation(api));
	return grpc.build();
    }

    /**
     * Convert device alarm state from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceAlarmState asApiDeviceAlarmState(GDeviceAlarmState grpc) throws SiteWhereException {
	if (grpc == null) {
	    return null;
	}
	switch (grpc) {
	case ALARM_STATE_TRIGGERED:
	    return DeviceAlarmState.Triggered;
	case ALARM_STATE_ACKNOWLEDGED:
	    return DeviceAlarmState.Acknowledged;
	case ALARM_STATE_RESOLVED:
	    return DeviceAlarmState.Resolved;
	case UNRECOGNIZED:
	    throw new SiteWhereException(ErrorCode.InvalidDataCategory, "Unknown device alarm state: " + grpc.name());
	}
	return null;
    }

    /**
     * Convert device alarm state from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceAlarmState asGrpcDeviceAlarmState(DeviceAlarmState api) throws SiteWhereException {
	if (api == null) {
	    return null;
	}
	switch (api) {
	case Triggered:
	    return GDeviceAlarmState.ALARM_STATE_TRIGGERED;
	case Acknowledged:
	    return GDeviceAlarmState.ALARM_STATE_ACKNOWLEDGED;
	case Resolved:
	    return GDeviceAlarmState.ALARM_STATE_RESOLVED;
	}
	throw new SiteWhereException(ErrorCode.InvalidDataCategory, "Unknown device alarm state: " + api.name());
    }

    /**
     * Convert device alarm create request from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceAlarmCreateRequest asApiDeviceAlarmCreateRequest(GDeviceAlarmCreateRequest grpc)
	    throws SiteWhereException {
	DeviceAlarmCreateRequest api = new DeviceAlarmCreateRequest();
	api.setDeviceAssignmentToken(
		grpc.hasDeviceAssignmentToken() ? grpc.getDeviceAssignmentToken().getValue() : null);
	api.setAlarmMessage(grpc.hasAlarmMessage() ? grpc.getAlarmMessage().getValue() : null);
	api.setTriggeringEventId(CommonModelConverter.asApiUuid(grpc.getTriggeringEventId()));
	api.setState(DeviceModelConverter.asApiDeviceAlarmState(grpc.getState()));
	api.setTriggeredDate(CommonModelConverter.asApiDate(grpc.getTriggeredDate()));
	api.setAcknowledgedDate(CommonModelConverter.asApiDate(grpc.getAcknowledgedDate()));
	api.setResolvedDate(CommonModelConverter.asApiDate(grpc.getResolvedDate()));
	api.setMetadata(grpc.getMetadataMap());
	return api;
    }

    /**
     * Convert device alarm create request from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceAlarmCreateRequest asGrpcDeviceAlarmCreateRequest(IDeviceAlarmCreateRequest api)
	    throws SiteWhereException {
	GDeviceAlarmCreateRequest.Builder grpc = GDeviceAlarmCreateRequest.newBuilder();
	if (api.getDeviceAssignmentToken() != null) {
	    grpc.setDeviceAssignmentToken(GOptionalString.newBuilder().setValue(api.getDeviceAssignmentToken()));
	}
	if (api.getAlarmMessage() != null) {
	    grpc.setAlarmMessage(GOptionalString.newBuilder().setValue(api.getAlarmMessage()));
	}
	if (api.getTriggeringEventId() != null) {
	    grpc.setTriggeringEventId(CommonModelConverter.asGrpcUuid(api.getTriggeringEventId()));
	}
	if (api.getState() != null) {
	    grpc.setState(DeviceModelConverter.asGrpcDeviceAlarmState(api.getState()));
	}
	if (api.getTriggeredDate() != null) {
	    grpc.setTriggeredDate(CommonModelConverter.asGrpcDate(api.getTriggeredDate()));
	}
	if (api.getAcknowledgedDate() != null) {
	    grpc.setAcknowledgedDate(CommonModelConverter.asGrpcDate(api.getAcknowledgedDate()));
	}
	if (api.getResolvedDate() != null) {
	    grpc.setResolvedDate(CommonModelConverter.asGrpcDate(api.getResolvedDate()));
	}
	if (api.getMetadata() != null) {
	    grpc.putAllMetadata(api.getMetadata());
	}
	return grpc.build();
    }

    /**
     * Convert device alarm search criteria from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceAlarmSearchCriteria asApiDeviceAlarmSearchCriteria(GDeviceAlarmSearchCriteria grpc)
	    throws SiteWhereException {
	DeviceAlarmSearchCriteria api = new DeviceAlarmSearchCriteria();
	api.setDeviceId(CommonModelConverter.asApiUuid(grpc.getDeviceId()));
	api.setDeviceAssignmentId(CommonModelConverter.asApiUuid(grpc.getDeviceAssignmentId()));
	api.setCustomerId(CommonModelConverter.asApiUuid(grpc.getCustomerId()));
	api.setAreaId(CommonModelConverter.asApiUuid(grpc.getAreaId()));
	api.setAssetId(CommonModelConverter.asApiUuid(grpc.getAssetId()));
	api.setTriggeringEventId(CommonModelConverter.asApiUuid(grpc.getTriggeringEventId()));
	api.setState(DeviceModelConverter.asApiDeviceAlarmState(grpc.getState()));
	api.setPageNumber(grpc.getPageNumber());
	api.setPageSize(grpc.getPageSize());
	return api;
    }

    /**
     * Convert device alarm search criteria from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceAlarmSearchCriteria asGrpcDeviceAlarmSearchCriteria(IDeviceAlarmSearchCriteria api)
	    throws SiteWhereException {
	GDeviceAlarmSearchCriteria.Builder grpc = GDeviceAlarmSearchCriteria.newBuilder();
	if (api.getDeviceId() != null) {
	    grpc.setDeviceId(CommonModelConverter.asGrpcUuid(api.getDeviceId()));
	}
	if (api.getDeviceAssignmentId() != null) {
	    grpc.setDeviceAssignmentId(CommonModelConverter.asGrpcUuid(api.getDeviceAssignmentId()));
	}
	if (api.getCustomerId() != null) {
	    grpc.setCustomerId(CommonModelConverter.asGrpcUuid(api.getCustomerId()));
	}
	if (api.getAreaId() != null) {
	    grpc.setAreaId(CommonModelConverter.asGrpcUuid(api.getAreaId()));
	}
	if (api.getAssetId() != null) {
	    grpc.setAssetId(CommonModelConverter.asGrpcUuid(api.getAssetId()));
	}
	if (api.getTriggeringEventId() != null) {
	    grpc.setTriggeringEventId(CommonModelConverter.asGrpcUuid(api.getTriggeringEventId()));
	}
	if (api.getState() != null) {
	    grpc.setState(DeviceModelConverter.asGrpcDeviceAlarmState(api.getState()));
	}
	grpc.setPageNumber(api.getPageNumber());
	grpc.setPageSize(api.getPageSize());
	return grpc.build();
    }

    /**
     * Convert device alarm search results from GRPC to API.
     * 
     * @param response
     * @return
     * @throws SiteWhereException
     */
    public static ISearchResults<IDeviceAlarm> asApiDeviceAlarmSearchResults(GDeviceAlarmSearchResults response)
	    throws SiteWhereException {
	List<IDeviceAlarm> results = new ArrayList<IDeviceAlarm>();
	for (GDeviceAlarm grpc : response.getAlarmsList()) {
	    results.add(DeviceModelConverter.asApiDeviceAlarm(grpc));
	}
	return new SearchResults<IDeviceAlarm>(results, response.getCount());
    }

    /**
     * Convert device alarm from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceAlarm asApiDeviceAlarm(GDeviceAlarm grpc) throws SiteWhereException {
	DeviceAlarm api = new DeviceAlarm();
	api.setDeviceId(grpc.hasDeviceId() ? CommonModelConverter.asApiUuid(grpc.getDeviceId()) : null);
	api.setDeviceAssignmentId(
		grpc.hasDeviceAssignmentId() ? CommonModelConverter.asApiUuid(grpc.getDeviceAssignmentId()) : null);
	api.setCustomerId(grpc.hasCustomerId() ? CommonModelConverter.asApiUuid(grpc.getCustomerId()) : null);
	api.setAreaId(grpc.hasAreaId() ? CommonModelConverter.asApiUuid(grpc.getAreaId()) : null);
	api.setAssetId(grpc.hasAssetId() ? CommonModelConverter.asApiUuid(grpc.getAssetId()) : null);
	api.setAlarmMessage(grpc.hasAlarmMessage() ? grpc.getAlarmMessage().getValue() : null);
	api.setTriggeringEventId(CommonModelConverter.asApiUuid(grpc.getTriggeringEventId()));
	api.setState(DeviceModelConverter.asApiDeviceAlarmState(grpc.getState()));
	api.setTriggeredDate(CommonModelConverter.asApiDate(grpc.getTriggeredDate()));
	api.setAcknowledgedDate(CommonModelConverter.asApiDate(grpc.getAcknowledgedDate()));
	api.setResolvedDate(CommonModelConverter.asApiDate(grpc.getResolvedDate()));
	api.setMetadata(grpc.getMetadataMap());
	return api;
    }

    /**
     * Convert device alarm from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceAlarm asGrpcDeviceAlarm(IDeviceAlarm api) throws SiteWhereException {
	GDeviceAlarm.Builder grpc = GDeviceAlarm.newBuilder();
	if (api.getDeviceId() != null) {
	    grpc.setDeviceId(CommonModelConverter.asGrpcUuid(api.getDeviceId()));
	}
	if (api.getDeviceAssignmentId() != null) {
	    grpc.setDeviceAssignmentId(CommonModelConverter.asGrpcUuid(api.getDeviceAssignmentId()));
	}
	if (api.getCustomerId() != null) {
	    grpc.setCustomerId(CommonModelConverter.asGrpcUuid(api.getCustomerId()));
	}
	if (api.getAreaId() != null) {
	    grpc.setAreaId(CommonModelConverter.asGrpcUuid(api.getAreaId()));
	}
	if (api.getAssetId() != null) {
	    grpc.setAssetId(CommonModelConverter.asGrpcUuid(api.getAssetId()));
	}
	if (api.getAlarmMessage() != null) {
	    grpc.setAlarmMessage(GOptionalString.newBuilder().setValue(api.getAlarmMessage()));
	}
	if (api.getTriggeringEventId() != null) {
	    grpc.setTriggeringEventId(CommonModelConverter.asGrpcUuid(api.getTriggeringEventId()));
	}
	if (api.getState() != null) {
	    grpc.setState(DeviceModelConverter.asGrpcDeviceAlarmState(api.getState()));
	}
	if (api.getTriggeredDate() != null) {
	    grpc.setTriggeredDate(CommonModelConverter.asGrpcDate(api.getTriggeredDate()));
	}
	if (api.getAcknowledgedDate() != null) {
	    grpc.setAcknowledgedDate(CommonModelConverter.asGrpcDate(api.getAcknowledgedDate()));
	}
	if (api.getResolvedDate() != null) {
	    grpc.setResolvedDate(CommonModelConverter.asGrpcDate(api.getResolvedDate()));
	}
	if (api.getMetadata() != null) {
	    grpc.putAllMetadata(api.getMetadata());
	}
	return grpc.build();
    }

    /**
     * Convert customer type create request from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static CustomerTypeCreateRequest asApiCustomerTypeCreateRequest(GCustomerTypeCreateRequest grpc)
	    throws SiteWhereException {
	CustomerTypeCreateRequest api = new CustomerTypeCreateRequest();
	api.setToken(grpc.hasToken() ? grpc.getToken().getValue() : null);
	api.setName(grpc.hasName() ? grpc.getName().getValue() : null);
	api.setDescription(grpc.hasDescription() ? grpc.getDescription().getValue() : null);
	api.setContainedCustomerTypeTokens(grpc.getContainedCustomerTypeTokensList());
	api.setMetadata(grpc.getMetadataMap());
	CommonModelConverter.setBrandingInformation(api, grpc.getBranding());
	return api;
    }

    /**
     * Convert customer type create request from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GCustomerTypeCreateRequest asGrpcCustomerTypeCreateRequest(ICustomerTypeCreateRequest api)
	    throws SiteWhereException {
	GCustomerTypeCreateRequest.Builder grpc = GCustomerTypeCreateRequest.newBuilder();
	if (api.getToken() != null) {
	    grpc.setToken(GOptionalString.newBuilder().setValue(api.getToken()));
	}
	if (api.getName() != null) {
	    grpc.setName(GOptionalString.newBuilder().setValue(api.getName()));
	}
	if (api.getDescription() != null) {
	    grpc.setDescription(GOptionalString.newBuilder().setValue(api.getDescription()));
	}
	if (api.getContainedCustomerTypeTokens() != null) {
	    grpc.addAllContainedCustomerTypeTokens(api.getContainedCustomerTypeTokens());
	}
	if (api.getMetadata() != null) {
	    grpc.putAllMetadata(api.getMetadata());
	}
	grpc.setBranding(CommonModelConverter.asGrpcBrandingInformation(api));
	return grpc.build();
    }

    /**
     * Convert customer type search criteria from API to GRPC.
     * 
     * @param code
     * @return
     * @throws SiteWhereException
     */
    public static GCustomerTypeSearchCriteria asApiCustomerTypeSearchCriteria(ISearchCriteria criteria)
	    throws SiteWhereException {
	GCustomerTypeSearchCriteria.Builder gcriteria = GCustomerTypeSearchCriteria.newBuilder();
	gcriteria.setPaging(CommonModelConverter.asGrpcPaging(criteria));
	return gcriteria.build();
    }

    /**
     * Convert area type search results from GRPC to API.
     * 
     * @param response
     * @return
     * @throws SiteWhereException
     */
    public static ISearchResults<ICustomerType> asApiCustomerTypeSearchResults(GCustomerTypeSearchResults response)
	    throws SiteWhereException {
	List<ICustomerType> results = new ArrayList<ICustomerType>();
	for (GCustomerType grpc : response.getCustomerTypesList()) {
	    results.add(DeviceModelConverter.asApiCustomerType(grpc));
	}
	return new SearchResults<ICustomerType>(results, response.getCount());
    }

    /**
     * Convert customer type from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static CustomerType asApiCustomerType(GCustomerType grpc) throws SiteWhereException {
	CustomerType api = new CustomerType();
	api.setName(grpc.getName());
	api.setDescription(grpc.getDescription());
	api.setContainedCustomerTypeIds(CommonModelConverter.asApiUuids(grpc.getContainedCustomerTypeIdsList()));
	CommonModelConverter.setEntityInformation(api, grpc.getEntityInformation());
	CommonModelConverter.setBrandingInformation(api, grpc.getBranding());
	return api;
    }

    /**
     * Convert customer type from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GCustomerType asGrpcCustomerType(ICustomerType api) throws SiteWhereException {
	GCustomerType.Builder grpc = GCustomerType.newBuilder();
	grpc.setName(api.getName());
	grpc.setDescription(api.getDescription());
	if (api.getContainedCustomerTypeIds() != null) {
	    grpc.addAllContainedCustomerTypeIds(CommonModelConverter.asGrpcUuids(api.getContainedCustomerTypeIds()));
	}
	grpc.setEntityInformation(CommonModelConverter.asGrpcEntityInformation(api));
	grpc.setBranding(CommonModelConverter.asGrpcBrandingInformation(api));
	return grpc.build();
    }

    /**
     * Convert customer create request from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static CustomerCreateRequest asApiCustomerCreateRequest(GCustomerCreateRequest grpc)
	    throws SiteWhereException {
	CustomerCreateRequest api = new CustomerCreateRequest();
	api.setToken(grpc.hasToken() ? grpc.getToken().getValue() : null);
	api.setCustomerTypeToken(grpc.hasCustomerTypeToken() ? grpc.getCustomerTypeToken().getValue() : null);
	api.setParentCustomerToken(grpc.hasParentCustomerToken() ? grpc.getParentCustomerToken().getValue() : null);
	api.setName(grpc.hasName() ? grpc.getName().getValue() : null);
	api.setDescription(grpc.hasDescription() ? grpc.getDescription().getValue() : null);
	api.setMetadata(grpc.getMetadataMap());
	CommonModelConverter.setBrandingInformation(api, grpc.getBranding());
	return api;
    }

    /**
     * Convert customer create request from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GCustomerCreateRequest asGrpcCustomerCreateRequest(ICustomerCreateRequest api)
	    throws SiteWhereException {
	GCustomerCreateRequest.Builder grpc = GCustomerCreateRequest.newBuilder();
	if (api.getToken() != null) {
	    grpc.setToken(GOptionalString.newBuilder().setValue(api.getToken()));
	}
	if (api.getCustomerTypeToken() != null) {
	    grpc.setCustomerTypeToken(GOptionalString.newBuilder().setValue(api.getCustomerTypeToken()));
	}
	if (api.getParentCustomerToken() != null) {
	    grpc.setParentCustomerToken(GOptionalString.newBuilder().setValue(api.getParentCustomerToken()));
	}
	if (api.getName() != null) {
	    grpc.setName(GOptionalString.newBuilder().setValue(api.getName()));
	}
	if (api.getDescription() != null) {
	    grpc.setDescription(GOptionalString.newBuilder().setValue(api.getDescription()));
	}
	if (api.getMetadata() != null) {
	    grpc.putAllMetadata(api.getMetadata());
	}
	grpc.setBranding(CommonModelConverter.asGrpcBrandingInformation(api));
	return grpc.build();
    }

    /**
     * Convert customer search criteria from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static CustomerSearchCriteria asApiCustomerSearchCriteria(GCustomerSearchCriteria grpc)
	    throws SiteWhereException {
	CustomerSearchCriteria api = new CustomerSearchCriteria(grpc.getPaging().getPageNumber(),
		grpc.getPaging().getPageSize());
	api.setRootOnly(grpc.hasRootOnly() ? grpc.getRootOnly().getValue() : null);
	api.setParentCustomerId(
		grpc.hasParentCustomerId() ? CommonModelConverter.asApiUuid(grpc.getParentCustomerId()) : null);
	api.setCustomerTypeId(
		grpc.hasCustomerTypeId() ? CommonModelConverter.asApiUuid(grpc.getCustomerTypeId()) : null);
	return api;
    }

    /**
     * Convert customer search criteria from API to GRPC.
     * 
     * @param code
     * @return
     * @throws SiteWhereException
     */
    public static GCustomerSearchCriteria asGrpcCustomerSearchCriteria(ICustomerSearchCriteria api)
	    throws SiteWhereException {
	GCustomerSearchCriteria.Builder grpc = GCustomerSearchCriteria.newBuilder();
	if (api.getRootOnly() != null) {
	    grpc.setRootOnly(GOptionalBoolean.newBuilder().setValue(api.getRootOnly()));
	}
	if (api.getParentCustomerId() != null) {
	    grpc.setParentCustomerId(CommonModelConverter.asGrpcUuid(api.getParentCustomerId()));
	}
	if (api.getCustomerTypeId() != null) {
	    grpc.setCustomerTypeId(CommonModelConverter.asGrpcUuid(api.getCustomerTypeId()));
	}
	grpc.setPaging(CommonModelConverter.asGrpcPaging(api));
	return grpc.build();
    }

    /**
     * Convert customer search results from GRPC to API.
     * 
     * @param response
     * @return
     * @throws SiteWhereException
     */
    public static ISearchResults<ICustomer> asApiCustomerSearchResults(GCustomerSearchResults response)
	    throws SiteWhereException {
	List<ICustomer> results = new ArrayList<ICustomer>();
	for (GCustomer grpc : response.getCustomersList()) {
	    results.add(DeviceModelConverter.asApiCustomer(grpc));
	}
	return new SearchResults<ICustomer>(results, response.getCount());
    }

    /**
     * Convert customer from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static Customer asApiCustomer(GCustomer grpc) throws SiteWhereException {
	Customer api = new Customer();
	api.setCustomerTypeId(CommonModelConverter.asApiUuid(grpc.getCustomerTypeId()));
	api.setParentCustomerId(
		grpc.hasParentCustomerId() ? CommonModelConverter.asApiUuid(grpc.getParentCustomerId()) : null);
	api.setName(grpc.getName());
	api.setDescription(grpc.getDescription());
	CommonModelConverter.setEntityInformation(api, grpc.getEntityInformation());
	CommonModelConverter.setBrandingInformation(api, grpc.getBranding());
	return api;
    }

    /**
     * Convert list of customers from GRPC to API.
     * 
     * @param grpcs
     * @return
     * @throws SiteWhereException
     */
    public static List<ICustomer> asApiCustomers(Collection<GCustomer> grpcs) throws SiteWhereException {
	List<ICustomer> apis = new ArrayList<>();
	for (GCustomer grpc : grpcs) {
	    apis.add(DeviceModelConverter.asApiCustomer(grpc));
	}
	return apis;
    }

    /**
     * Convert customer from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GCustomer asGrpcCustomer(ICustomer api) throws SiteWhereException {
	GCustomer.Builder grpc = GCustomer.newBuilder();
	grpc.setCustomerTypeId(CommonModelConverter.asGrpcUuid(api.getCustomerTypeId()));
	if (api.getParentCustomerId() != null) {
	    grpc.setParentCustomerId(CommonModelConverter.asGrpcUuid(api.getParentCustomerId()));
	}
	grpc.setName(api.getName());
	grpc.setDescription(api.getDescription());
	grpc.setEntityInformation(CommonModelConverter.asGrpcEntityInformation(api));
	grpc.setBranding(CommonModelConverter.asGrpcBrandingInformation(api));
	return grpc.build();
    }

    /**
     * Convert list of customers from API to GRPC.
     * 
     * @param apis
     * @return
     * @throws SiteWhereException
     */
    public static List<GCustomer> asGrpcCustomers(List<ICustomer> apis) throws SiteWhereException {
	List<GCustomer> grpcs = new ArrayList<>();
	for (ICustomer api : apis) {
	    grpcs.add(DeviceModelConverter.asGrpcCustomer(api));
	}
	return grpcs;
    }

    /**
     * Convert area type create request from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static AreaTypeCreateRequest asApiAreaTypeCreateRequest(GAreaTypeCreateRequest grpc)
	    throws SiteWhereException {
	AreaTypeCreateRequest api = new AreaTypeCreateRequest();
	api.setToken(grpc.hasToken() ? grpc.getToken().getValue() : null);
	api.setName(grpc.getName());
	api.setDescription(grpc.getDescription());
	api.setContainedAreaTypeTokens(grpc.getContainedAreaTypeTokensList());
	api.setMetadata(grpc.getMetadataMap());
	CommonModelConverter.setBrandingInformation(api, grpc.getBranding());
	return api;
    }

    /**
     * Convert area type create request from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GAreaTypeCreateRequest asGrpcAreaTypeCreateRequest(IAreaTypeCreateRequest api)
	    throws SiteWhereException {
	GAreaTypeCreateRequest.Builder grpc = GAreaTypeCreateRequest.newBuilder();
	if (api.getToken() != null) {
	    grpc.setToken(GOptionalString.newBuilder().setValue(api.getToken()));
	}
	grpc.setName(api.getName());
	grpc.setDescription(api.getDescription());
	if (api.getContainedAreaTypeTokens() != null) {
	    grpc.addAllContainedAreaTypeTokens(api.getContainedAreaTypeTokens());
	}
	if (api.getMetadata() != null) {
	    grpc.putAllMetadata(api.getMetadata());
	}
	grpc.setBranding(CommonModelConverter.asGrpcBrandingInformation(api));
	return grpc.build();
    }

    /**
     * Convert area type search criteria from API to GRPC.
     * 
     * @param code
     * @return
     * @throws SiteWhereException
     */
    public static GAreaTypeSearchCriteria asApiAreaTypeSearchCriteria(ISearchCriteria criteria)
	    throws SiteWhereException {
	GAreaTypeSearchCriteria.Builder gcriteria = GAreaTypeSearchCriteria.newBuilder();
	gcriteria.setPaging(CommonModelConverter.asGrpcPaging(criteria));
	return gcriteria.build();
    }

    /**
     * Convert area type search results from GRPC to API.
     * 
     * @param response
     * @return
     * @throws SiteWhereException
     */
    public static ISearchResults<IAreaType> asApiAreaTypeSearchResults(GAreaTypeSearchResults response)
	    throws SiteWhereException {
	List<IAreaType> results = new ArrayList<IAreaType>();
	for (GAreaType grpc : response.getAreaTypesList()) {
	    results.add(DeviceModelConverter.asApiAreaType(grpc));
	}
	return new SearchResults<IAreaType>(results, response.getCount());
    }

    /**
     * Convert area type from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static AreaType asApiAreaType(GAreaType grpc) throws SiteWhereException {
	AreaType api = new AreaType();
	api.setName(grpc.getName());
	api.setDescription(grpc.getDescription());
	api.setContainedAreaTypeIds(CommonModelConverter.asApiUuids(grpc.getContainedAreaTypeIdsList()));
	CommonModelConverter.setEntityInformation(api, grpc.getEntityInformation());
	CommonModelConverter.setBrandingInformation(api, grpc.getBranding());
	return api;
    }

    /**
     * Convert area type from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GAreaType asGrpcAreaType(IAreaType api) throws SiteWhereException {
	GAreaType.Builder grpc = GAreaType.newBuilder();
	grpc.setName(api.getName());
	grpc.setDescription(api.getDescription());
	if (api.getContainedAreaTypeIds() != null) {
	    grpc.addAllContainedAreaTypeIds(CommonModelConverter.asGrpcUuids(api.getContainedAreaTypeIds()));
	}
	grpc.setEntityInformation(CommonModelConverter.asGrpcEntityInformation(api));
	grpc.setBranding(CommonModelConverter.asGrpcBrandingInformation(api));
	return grpc.build();
    }

    /**
     * Convert area create request from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static AreaCreateRequest asApiAreaCreateRequest(GAreaCreateRequest grpc) throws SiteWhereException {
	AreaCreateRequest api = new AreaCreateRequest();
	api.setToken(grpc.hasToken() ? grpc.getToken().getValue() : null);
	api.setAreaTypeToken(grpc.hasAreaTypeToken() ? grpc.getAreaTypeToken().getValue() : null);
	api.setParentAreaToken(grpc.hasParentAreaToken() ? grpc.getParentAreaToken().getValue() : null);
	api.setName(grpc.hasName() ? grpc.getName().getValue() : null);
	api.setDescription(grpc.hasDescription() ? grpc.getDescription().getValue() : null);
	api.setBounds(CommonModelConverter.asApiLocations(grpc.getBoundsList()));
	api.setMetadata(grpc.getMetadataMap());
	if (grpc.getGatewayId() != null) {
		api.setGatewayId(grpc.getGatewayId());
	}
	CommonModelConverter.setBrandingInformation(api, grpc.getBranding());
	api.setGatewayId(grpc.getGatewayId());
	return api;
    }

    /**
     * Convert area create request from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GAreaCreateRequest asGrpcAreaCreateRequest(IAreaCreateRequest api) throws SiteWhereException {
	GAreaCreateRequest.Builder grpc = GAreaCreateRequest.newBuilder();
	if (api.getToken() != null) {
	    grpc.setToken(GOptionalString.newBuilder().setValue(api.getToken()));
	}
	if (api.getAreaTypeToken() != null) {
	    grpc.setAreaTypeToken(GOptionalString.newBuilder().setValue(api.getAreaTypeToken()));
	}
	if (api.getParentAreaToken() != null) {
	    grpc.setParentAreaToken(GOptionalString.newBuilder().setValue(api.getParentAreaToken()));
	}
	if (api.getName() != null) {
	    grpc.setName(GOptionalString.newBuilder().setValue(api.getName()));
	}
	if (api.getDescription() != null) {
	    grpc.setDescription(GOptionalString.newBuilder().setValue(api.getDescription()));
	}
	if (api.getBounds() != null) {
	    grpc.addAllBounds(CommonModelConverter.asGrpcLocations(api.getBounds()));
	}
	if (api.getMetadata() != null) {
	    grpc.putAllMetadata(api.getMetadata());
	}
	if (api.getGatewayId() != null) {
		grpc.setGatewayId(api.getGatewayId());
	}
	grpc.setBranding(CommonModelConverter.asGrpcBrandingInformation(api));
	return grpc.build();
    }

    /**
     * Convert area search criteria from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static AreaSearchCriteria asApiAreaSearchCriteria(GAreaSearchCriteria grpc) throws SiteWhereException {
	AreaSearchCriteria api = new AreaSearchCriteria(grpc.getPaging().getPageNumber(),
		grpc.getPaging().getPageSize());
	api.setRootOnly(grpc.hasRootOnly() ? grpc.getRootOnly().getValue() : null);
	api.setParentAreaId(grpc.hasParentAreaId() ? CommonModelConverter.asApiUuid(grpc.getParentAreaId()) : null);
	api.setAreaTypeId(grpc.hasAreaTypeId() ? CommonModelConverter.asApiUuid(grpc.getAreaTypeId()) : null);
	api.setAreaTokens(grpc.getAreaTokensCount() > 0 ? grpc.getAreaTokensList() : null);
	return api;
    }

    /**
     * Convert area search criteria from API to GRPC.
     * 
     * @param code
     * @return
     * @throws SiteWhereException
     */
    public static GAreaSearchCriteria asGrpcAreaSearchCriteria(IAreaSearchCriteria api) throws SiteWhereException {
	GAreaSearchCriteria.Builder grpc = GAreaSearchCriteria.newBuilder();
	if (api.getRootOnly() != null) {
	    grpc.setRootOnly(GOptionalBoolean.newBuilder().setValue(api.getRootOnly()));
	}
	if (api.getParentAreaId() != null) {
	    grpc.setParentAreaId(CommonModelConverter.asGrpcUuid(api.getParentAreaId()));
	}
	if (api.getAreaTypeId() != null) {
	    grpc.setAreaTypeId(CommonModelConverter.asGrpcUuid(api.getAreaTypeId()));
	}
	grpc.setPaging(CommonModelConverter.asGrpcPaging(api));
	if (CollectionUtils.isNotEmpty(api.getAreaTokens())) {
		grpc.addAllAreaTokens(api.getAreaTokens());
	}
	return grpc.build();
    }

    /**
     * Convert area search results from GRPC to API.
     * 
     * @param response
     * @return
     * @throws SiteWhereException
     */
    public static ISearchResults<IArea> asApiAreaSearchResults(GAreaSearchResults response) throws SiteWhereException {
	List<IArea> results = new ArrayList<IArea>();
	for (GArea grpc : response.getAreasList()) {
	    results.add(DeviceModelConverter.asApiArea(grpc));
	}
	return new SearchResults<IArea>(results, response.getCount());
    }

    /**
     * Convert area from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static Area asApiArea(GArea grpc) throws SiteWhereException {
	Area api = new Area();
	api.setAreaTypeId(CommonModelConverter.asApiUuid(grpc.getAreaTypeId()));
	api.setParentAreaId(grpc.hasParentAreaId() ? CommonModelConverter.asApiUuid(grpc.getParentAreaId()) : null);
	api.setName(grpc.getName());
	api.setDescription(grpc.getDescription());
	api.setBounds(CommonModelConverter.asApiLocations(grpc.getBoundsList()));
	CommonModelConverter.setEntityInformation(api, grpc.getEntityInformation());
	CommonModelConverter.setBrandingInformation(api, grpc.getBranding());
	api.setGatewayId(grpc.getGatewayId());
	return api;
    }

    /**
     * Convert list of areas from GRPC to API.
     * 
     * @param grpcs
     * @return
     * @throws SiteWhereException
     */
    public static List<IArea> asApiAreas(Collection<GArea> grpcs) throws SiteWhereException {
	List<IArea> apis = new ArrayList<>();
	for (GArea grpc : grpcs) {
	    apis.add(DeviceModelConverter.asApiArea(grpc));
	}
	return apis;
    }

    /**
     * Convert area from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GArea asGrpcArea(IArea api) throws SiteWhereException {
	GArea.Builder grpc = GArea.newBuilder();
	grpc.setAreaTypeId(CommonModelConverter.asGrpcUuid(api.getAreaTypeId()));
	if (api.getParentAreaId() != null) {
	    grpc.setParentAreaId(CommonModelConverter.asGrpcUuid(api.getParentAreaId()));
	}
	grpc.setName(api.getName());
	if (StringUtils.isNotEmpty(api.getDescription())) grpc.setDescription(api.getDescription());
	grpc.addAllBounds(CommonModelConverter.asGrpcLocations(api.getBounds()));
	grpc.setEntityInformation(CommonModelConverter.asGrpcEntityInformation(api));
	grpc.setBranding(CommonModelConverter.asGrpcBrandingInformation(api));
	if (api.getGatewayId() != null) {
		grpc.setGatewayId(api.getGatewayId());
	}
	return grpc.build();
    }

    /**
     * Convert list of areas from API to GRPC.
     * 
     * @param apis
     * @return
     * @throws SiteWhereException
     */
    public static List<GArea> asGrpcAreas(List<IArea> apis) throws SiteWhereException {
	List<GArea> grpcs = new ArrayList<>();
	for (IArea api : apis) {
	    grpcs.add(DeviceModelConverter.asGrpcArea(api));
	}
	return grpcs;
    }

    /**
     * Convert zone create request from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static ZoneCreateRequest asApiZoneCreateRequest(GZoneCreateRequest grpc) throws SiteWhereException {
	ZoneCreateRequest api = new ZoneCreateRequest();
	api.setToken(grpc.hasToken() ? grpc.getToken().getValue() : null);
	api.setAreaToken(grpc.hasAreaToken() ? grpc.getAreaToken().getValue() : null);
	api.setName(grpc.hasName() ? grpc.getName().getValue() : null);
	api.setBounds(CommonModelConverter.asApiLocations(grpc.getBoundsList()));
	api.setFillColor(grpc.hasFillColor() ? grpc.getFillColor().getValue() : null);
	api.setBorderColor(grpc.hasBorderColor() ? grpc.getBorderColor().getValue() : null);
	api.setOpacity(grpc.hasOpacity() ? grpc.getOpacity().getValue() : null);
	api.setMetadata(grpc.getMetadataMap());
	return api;
    }

    /**
     * Convert zone create request from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GZoneCreateRequest asGrpcZoneCreateRequest(IZoneCreateRequest api) throws SiteWhereException {
	GZoneCreateRequest.Builder grpc = GZoneCreateRequest.newBuilder();
	if (api.getToken() != null) {
	    grpc.setToken(GOptionalString.newBuilder().setValue(api.getToken()));
	}
	if (api.getAreaToken() != null) {
	    grpc.setAreaToken(GOptionalString.newBuilder().setValue(api.getAreaToken()));
	}
	if (api.getName() != null) {
	    grpc.setName(GOptionalString.newBuilder().setValue(api.getName()));
	}
	grpc.addAllBounds(CommonModelConverter.asGrpcLocations(api.getBounds()));
	if (api.getFillColor() != null) {
	    grpc.setFillColor(GOptionalString.newBuilder().setValue(api.getFillColor()));
	}
	if (api.getBorderColor() != null) {
	    grpc.setBorderColor(GOptionalString.newBuilder().setValue(api.getBorderColor()));
	}
	if (api.getOpacity() != null) {
	    grpc.setOpacity(GOptionalDouble.newBuilder().setValue(api.getOpacity()));
	}
	if (api.getMetadata() != null) {
	    grpc.putAllMetadata(api.getMetadata());
	}
	return grpc.build();
    }

    /**
     * Convert zone search criteria from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static ZoneSearchCriteria asApiZoneSearchCriteria(GZoneSearchCriteria grpc) throws SiteWhereException {
	ZoneSearchCriteria api = new ZoneSearchCriteria(grpc.getPaging().getPageNumber(),
		grpc.getPaging().getPageSize());
	api.setAreaId(grpc.hasAreaId() ? CommonModelConverter.asApiUuid(grpc.getAreaId()) : null);
	return api;
    }

    /**
     * Convert zone search criteria from API to GRPC.
     * 
     * @param code
     * @return
     * @throws SiteWhereException
     */
    public static GZoneSearchCriteria asGrpcZoneSearchCriteria(IZoneSearchCriteria api) throws SiteWhereException {
	GZoneSearchCriteria.Builder grpc = GZoneSearchCriteria.newBuilder();
	if (api.getAreaId() != null) {
	    grpc.setAreaId(CommonModelConverter.asGrpcUuid(api.getAreaId()));
	}
	grpc.setPaging(CommonModelConverter.asGrpcPaging(api));
	return grpc.build();
    }

    /**
     * Convert zone search results from GRPC to API.
     * 
     * @param response
     * @return
     * @throws SiteWhereException
     */
    public static ISearchResults<IZone> asApiZoneSearchResults(GZoneSearchResults response) throws SiteWhereException {
	List<IZone> results = new ArrayList<IZone>();
	for (GZone grpc : response.getZonesList()) {
	    results.add(DeviceModelConverter.asApiZone(grpc));
	}
	return new SearchResults<IZone>(results, response.getCount());
    }

    /**
     * Convert zone from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static Zone asApiZone(GZone grpc) throws SiteWhereException {
	Zone api = new Zone();
	api.setName(grpc.getName());
	api.setBounds(CommonModelConverter.asApiLocations(grpc.getBoundsList()));
	api.setFillColor(grpc.getFillColor());
	api.setBorderColor(grpc.getBorderColor());
	api.setOpacity(grpc.getOpacity());
	CommonModelConverter.setEntityInformation(api, grpc.getEntityInformation());
	return api;
    }

    /**
     * Convert zone from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GZone asGrpcZone(IZone api) throws SiteWhereException {
	GZone.Builder grpc = GZone.newBuilder();
	grpc.setName(api.getName());
	grpc.addAllBounds(CommonModelConverter.asGrpcLocations(api.getBounds()));
	grpc.setFillColor(api.getFillColor());
	grpc.setBorderColor(api.getBorderColor());
	grpc.setOpacity(api.getOpacity());
	grpc.setEntityInformation(CommonModelConverter.asGrpcEntityInformation(api));
	return grpc.build();
    }

    /**
     * Convert device registration payload from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceRegistrationPayload asApiDeviceRegistrationPayload(GDeviceRegistationPayload grpc)
	    throws SiteWhereException {
	DeviceRegistrationPayload api = new DeviceRegistrationPayload();
	api.setSourceId(grpc.getSourceId());
	api.setDeviceToken(grpc.getDeviceToken());
	api.setOriginator(grpc.hasOriginator() ? grpc.getOriginator().getValue() : null);
	api.setDeviceRegistrationRequest(DeviceModelConverter.asApiDeviceRegistrationRequest(grpc.getRegistration()));
	return api;
    }

    /**
     * Convert device registration payload from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceRegistationPayload asGrpcDeviceRegistrationPayload(IDeviceRegistrationPayload api)
	    throws SiteWhereException {
	GDeviceRegistationPayload.Builder grpc = GDeviceRegistationPayload.newBuilder();
	grpc.setSourceId(api.getSourceId());
	grpc.setDeviceToken(api.getDeviceToken());
	if (api.getOriginator() != null) {
	    grpc.setOriginator(GOptionalString.newBuilder().setValue(api.getOriginator()));
	}
	grpc.setRegistration(DeviceModelConverter.asGrpcDeviceRegistrationRequest(api.getDeviceRegistrationRequest()));
	return grpc.build();
    }
}