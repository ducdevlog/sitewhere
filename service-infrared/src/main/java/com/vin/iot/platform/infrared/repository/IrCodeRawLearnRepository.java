/*
 *
 *  Developed by Anhgv by VinGroup on 8/12/19, 11:25 AM
 *  Last modified 7/17/19, 9:59 AM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.vin.iot.platform.infrared.repository;

import com.vin.iot.platform.infrared.domain.IrCodeRawLearn;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IrCodeRawLearnRepository extends MongoRepository<IrCodeRawLearn, String> {

}
