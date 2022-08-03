package com.tads.mhsf.bazaar.mapper;

import com.tads.mhsf.bazaar.dto.DoneeInstitutionDto;
import com.tads.mhsf.bazaar.entity.DoneeInstitution;
import org.springframework.stereotype.Component;

@Component
public class DoneeInstitutionMapper {

    public DoneeInstitution dtoToEntity(DoneeInstitutionDto doneeInstitutionDTO) {
        return new DoneeInstitution(
                doneeInstitutionDTO.getId(),
                doneeInstitutionDTO.getName(),
                doneeInstitutionDTO.getAddress(),
                doneeInstitutionDTO.getPhoneNumber(),
                doneeInstitutionDTO.getDescription()
        );
    }

    public DoneeInstitutionDto entityToDto(DoneeInstitution doneeInstitution) {
        return new DoneeInstitutionDto(
                doneeInstitution.getId(),
                doneeInstitution.getName(),
                doneeInstitution.getAddress(),
                doneeInstitution.getPhoneNumber(),
                doneeInstitution.getDescription()
        );
    }

}
