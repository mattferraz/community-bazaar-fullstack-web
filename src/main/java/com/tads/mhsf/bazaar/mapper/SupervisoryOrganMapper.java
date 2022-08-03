package com.tads.mhsf.bazaar.mapper;

import com.tads.mhsf.bazaar.dto.SupervisoryOrganDto;
import com.tads.mhsf.bazaar.entity.SupervisoryOrgan;
import org.springframework.stereotype.Component;

@Component
public class SupervisoryOrganMapper {

    public SupervisoryOrgan dtoToEntity(SupervisoryOrganDto supervisoryOrganDTO) {
        return new SupervisoryOrgan(
                supervisoryOrganDTO.getId(),
                supervisoryOrganDTO.getName(),
                supervisoryOrganDTO.getDescription()
        );
    }

    public SupervisoryOrganDto entityToDto(SupervisoryOrgan supervisoryOrgan) {
        return new SupervisoryOrganDto(
                supervisoryOrgan.getId(),
                supervisoryOrgan.getName(),
                supervisoryOrgan.getDescription()
        );
    }

}
