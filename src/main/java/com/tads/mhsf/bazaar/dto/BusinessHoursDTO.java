package com.tads.mhsf.bazaar.dto;

import com.tads.mhsf.bazaar.entity.BusinessHours;
import com.tads.mhsf.bazaar.util.TimeUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class BusinessHoursDTO {
    private final int id;
    private final int weekday;
    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final DoneeInstitutionDTO doneeInstitutionDTO;

    public BusinessHoursDTO(BusinessHours entity) {
        this.id = entity.getId();
        this.weekday = entity.getWeekday();
        this.openTime = TimeUtils.convertElapsedMinutesToLocalTime(entity.getOpenTime());
        this.closeTime = TimeUtils.convertElapsedMinutesToLocalTime(entity.getCloseTime());
        this.doneeInstitutionDTO = new DoneeInstitutionDTO(entity.getDoneeInstitution());
    }
}
