package com.tads.mhsf.bazaar.service;

import com.tads.mhsf.bazaar.dto.SupervisoryOrganDto;
import com.tads.mhsf.bazaar.entity.SupervisoryOrgan;
import com.tads.mhsf.bazaar.exception.BadRequestException;
import com.tads.mhsf.bazaar.exception.DataNotFoundException;
import com.tads.mhsf.bazaar.exception.UnprocessableRequestException;
import com.tads.mhsf.bazaar.mapper.SupervisoryOrganMapper;
import com.tads.mhsf.bazaar.repository.SupervisoryOrganRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SupervisoryOrganService {

    private final SupervisoryOrganRepository supervisoryOrganRepository;
    private final SupervisoryOrganMapper supervisoryOrganMapper;

    public SupervisoryOrganService(SupervisoryOrganRepository supervisoryOrganRepository, SupervisoryOrganMapper supervisoryOrganMapper) {
        this.supervisoryOrganRepository = supervisoryOrganRepository;
        this.supervisoryOrganMapper = supervisoryOrganMapper;
    }

    @Transactional
    public SupervisoryOrganDto createSupervisoryOrgan(SupervisoryOrganDto supervisoryOrganDTO) {
        if (supervisoryOrganDTO.getId() != null) {
            throw new BadRequestException("Request body shouldn't contain id");
        }
        SupervisoryOrgan supervisoryOrgan = supervisoryOrganRepository
                .save(supervisoryOrganMapper.dtoToEntity(supervisoryOrganDTO));

        return supervisoryOrganMapper.entityToDto(supervisoryOrgan);
    }

    @Transactional
    public SupervisoryOrganDto findSupervisoryOrganById(int id) {
        SupervisoryOrgan supervisoryOrgan = supervisoryOrganRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Supervisory organ not found with id: " + id));

        return supervisoryOrganMapper.entityToDto(supervisoryOrgan);
    }

    @Transactional
    public List<SupervisoryOrganDto> findAllSupervisoryOrgans() {
        return supervisoryOrganRepository
                .findAll()
                .stream()
                .map(supervisoryOrganMapper::entityToDto)
                .toList();
    }

    @Transactional
    public void updateSupervisoryOrgan(int id, SupervisoryOrganDto newSupervisoryOrganDto) {
        if (newSupervisoryOrganDto.getId() != id) {
            throw new UnprocessableRequestException("The IDs provided in the URI and in the request body don't match.");
        }
        if (supervisoryOrganRepository.findById(id).isPresent()) {
            SupervisoryOrgan newSupervisoryOrgan = supervisoryOrganMapper.dtoToEntity(newSupervisoryOrganDto);
            newSupervisoryOrgan.setId(id);
            supervisoryOrganRepository.update(newSupervisoryOrgan);
        } else {
            throw new DataNotFoundException("Supervisory organ not found with id: " + id);
        }
    }

    @Transactional
    public void deleteSupervisoryOrganById(int id) {
        if (supervisoryOrganRepository.findById(id).isPresent()) {
            supervisoryOrganRepository.deleteById(id);
        } else {
            throw new DataNotFoundException("Supervisory organ not found with id: " + id);
        }
    }

}
