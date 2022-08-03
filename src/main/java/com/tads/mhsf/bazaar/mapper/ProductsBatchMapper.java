package com.tads.mhsf.bazaar.mapper;

import com.tads.mhsf.bazaar.dto.ProductsBatchDto;
import com.tads.mhsf.bazaar.entity.ProductsBatch;
import org.springframework.stereotype.Component;

@Component
public class ProductsBatchMapper {

    private final SupervisoryOrganMapper supervisoryOrganMapper;
    private final DoneeInstitutionMapper doneeInstitutionMapper;

    public ProductsBatchMapper(SupervisoryOrganMapper supervisoryOrganMapper,
                               DoneeInstitutionMapper doneeInstitutionMapper) {
        this.supervisoryOrganMapper = supervisoryOrganMapper;
        this.doneeInstitutionMapper = doneeInstitutionMapper;
    }

    public ProductsBatch dtoToEntity(ProductsBatchDto productsBatchDTO) {
        return new ProductsBatch(
                productsBatchDTO.getId(),
                productsBatchDTO.getNote(),
                productsBatchDTO.getDeliveryDate(),
                supervisoryOrganMapper.dtoToEntity(productsBatchDTO.getSupervisoryOrganDTO()),
                doneeInstitutionMapper.dtoToEntity(productsBatchDTO.getDoneeInstitutionDTO())
        );
    }

    public ProductsBatchDto entityToDto(ProductsBatch productsBatch) {
        return new ProductsBatchDto(
                productsBatch.getId(),
                productsBatch.getNote(),
                productsBatch.getDeliveryDate(),
                supervisoryOrganMapper.entityToDto(productsBatch.getSupervisoryOrgan()),
                doneeInstitutionMapper.entityToDto(productsBatch.getDoneeInstitution())
        );
    }

}
