package com.tads.mhsf.bazaar.mapper;

import com.tads.mhsf.bazaar.dto.BusinessHourDto;
import com.tads.mhsf.bazaar.entity.BusinessHour;
import com.tads.mhsf.bazaar.util.TimeUtils;
import org.springframework.stereotype.Component;

@Component
public class BusinessHourMapper {

    private final DoneeInstitutionMapper doneeInstitutionMapper;

    public BusinessHourMapper(DoneeInstitutionMapper doneeInstitutionMapper) {
        this.doneeInstitutionMapper = doneeInstitutionMapper;
    }

    public BusinessHour dtoToEntity(BusinessHourDto businessHourDTO) {
        return new BusinessHour(
                businessHourDTO.getId(),
                businessHourDTO.getWeekday(),
                TimeUtils.convertLocalTimeToElapsedMinutes(businessHourDTO.getOpenTime()),
                TimeUtils.convertLocalTimeToElapsedMinutes(businessHourDTO.getCloseTime()),
                doneeInstitutionMapper.dtoToEntity(businessHourDTO.getDoneeInstitutionDto())
        );
    }

    public BusinessHourDto entityToDto(BusinessHour businessHour) {
        return new BusinessHourDto(
                businessHour.getId(),
                businessHour.getWeekday(),
                TimeUtils.convertElapsedMinutesToLocalTime(businessHour.getOpenTime()),
                TimeUtils.convertElapsedMinutesToLocalTime(businessHour.getCloseTime()),
                doneeInstitutionMapper.entityToDto(businessHour.getDoneeInstitution())
        );
    }

}
