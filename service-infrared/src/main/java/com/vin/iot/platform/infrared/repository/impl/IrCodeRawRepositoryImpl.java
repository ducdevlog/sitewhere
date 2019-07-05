package com.vin.iot.platform.infrared.repository.impl;

import com.vin.iot.platform.infrared.domain.IrCodeRaw;
import com.vin.iot.platform.infrared.repository.IrCodeRawRepositoryCustom;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IrCodeRawRepositoryImpl implements IrCodeRawRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<IrCodeRaw> getIrCodeRaws(IrCodeRaw irCodeRaw) {
        Query query = new Query();
        if (StringUtils.isNotEmpty(irCodeRaw.getCodesetName())) {
            query.addCriteria(Criteria.where("CODESET_NAME").is(irCodeRaw.getCodesetName()));
        }
        if (StringUtils.isNotEmpty(irCodeRaw.getFunctionName())) {
            query.addCriteria(Criteria.where("FUNCTION_NAME").is(irCodeRaw.getFunctionName()));
        }
        if (StringUtils.isNotEmpty(irCodeRaw.getPower())) {
            query.addCriteria(Criteria.where("POWER").is(irCodeRaw.getPower()));
        }
        if (StringUtils.isNotEmpty(irCodeRaw.getMode())) {
            query.addCriteria(Criteria.where("MODE").is(irCodeRaw.getMode()));
        }
        if (StringUtils.isNotEmpty(irCodeRaw.getFan())) {
            query.addCriteria(Criteria.where("FAN").is(irCodeRaw.getFan()));
        }
        if (StringUtils.isNotEmpty(irCodeRaw.getTemp())) {
            query.addCriteria(Criteria.where("TEMP").is(irCodeRaw.getTemp()));
        }
        if (StringUtils.isNotEmpty(irCodeRaw.getSwing())) {
            query.addCriteria(Criteria.where("SWING").is(irCodeRaw.getSwing()));
        }
        if (StringUtils.isNotEmpty(irCodeRaw.getFunctionName())) {
            query.addCriteria(Criteria.where("TIMER").is(irCodeRaw.getTimer()));
        }
        if (StringUtils.isNotEmpty(irCodeRaw.getTimer())) {
            query.addCriteria(Criteria.where("TIMER").is(irCodeRaw.getTimer()));
        }
        if (StringUtils.isNotEmpty(irCodeRaw.getTimerMins())) {
            query.addCriteria(Criteria.where("TIMER_MINS").is(irCodeRaw.getTimerMins()));
        }
        if (StringUtils.isNotEmpty(irCodeRaw.getSleep())) {
            query.addCriteria(Criteria.where("SLEEP").is(irCodeRaw.getSleep()));
        }
        if (StringUtils.isNotEmpty(irCodeRaw.getSleepMins())) {
            query.addCriteria(Criteria.where("SLEEP_MINS").is(irCodeRaw.getSleepMins()));
        }
        if (StringUtils.isNotEmpty(irCodeRaw.getLed())) {
            query.addCriteria(Criteria.where("LED").is(irCodeRaw.getLed()));
        }
        if (StringUtils.isNotEmpty(irCodeRaw.getComfort())) {
            query.addCriteria(Criteria.where("COMFORT").is(irCodeRaw.getComfort()));
        }
        if (StringUtils.isNotEmpty(irCodeRaw.getEcono())) {
            query.addCriteria(Criteria.where("ECONO").is(irCodeRaw.getEcono()));
        }
        if (StringUtils.isNotEmpty(irCodeRaw.getPowerful())) {
            query.addCriteria(Criteria.where("POWERFUL").is(irCodeRaw.getPowerful()));
        }
        if (StringUtils.isNotEmpty(irCodeRaw.getIrFreq())) {
            query.addCriteria(Criteria.where("IR_FREQ").is(irCodeRaw.getIrFreq()));
        }
        if (StringUtils.isNotEmpty(irCodeRaw.getIrCode())) {
            query.addCriteria(Criteria.where("IR_CODE").is(irCodeRaw.getIrCode()));
        }

        return mongoTemplate.find(query, IrCodeRaw.class);
    }
}
