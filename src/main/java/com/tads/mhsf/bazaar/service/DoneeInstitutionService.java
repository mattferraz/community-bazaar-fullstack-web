package com.tads.mhsf.bazaar.service;

import com.tads.mhsf.bazaar.dto.DoneeInstitutionDto;
import com.tads.mhsf.bazaar.entity.DoneeInstitution;
import com.tads.mhsf.bazaar.exception.DataNotFoundException;
import com.tads.mhsf.bazaar.exception.UnprocessableRequestException;
import com.tads.mhsf.bazaar.mapper.DoneeInstitutionMapper;
import com.tads.mhsf.bazaar.repository.BusinessHoursRepository;
import com.tads.mhsf.bazaar.repository.DoneeInstitutionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoneeInstitutionService {

    private final DoneeInstitutionMapper doneeInstitutionMapper;
    private final BusinessHoursRepository businessHoursRepository;
    private final DoneeInstitutionRepository doneeInstitutionRepository;

    public DoneeInstitutionService(DoneeInstitutionMapper doneeInstitutionMapper,
                                   BusinessHoursRepository businessHoursRepository,
                                   DoneeInstitutionRepository doneeInstitutionRepository) {
        this.doneeInstitutionMapper = doneeInstitutionMapper;
        this.businessHoursRepository = businessHoursRepository;
        this.doneeInstitutionRepository = doneeInstitutionRepository;
    }

    @Transactional
    public DoneeInstitutionDto createDoneeInstitution(DoneeInstitutionDto doneeInstitutionDto) {
        if (doneeInstitutionDto.getId() != null) {
            throw new UnprocessableRequestException("Request body shouldn't contain id");
        }
        DoneeInstitution doneeInstitution = doneeInstitutionRepository
                .save(doneeInstitutionMapper.dtoToEntity(doneeInstitutionDto));

        return doneeInstitutionMapper.entityToDto(doneeInstitution);
    }

    @Transactional
    public DoneeInstitutionDto findDoneeInstitutionById(int id) {
        DoneeInstitution doneeInstitution = doneeInstitutionRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Donee Institution not found with id: " + id));

        return doneeInstitutionMapper.entityToDto(doneeInstitution);
    }

    @Transactional
    public List<DoneeInstitutionDto> findAllDoneeInstitutions() {
        return doneeInstitutionRepository
                .findAll()
                .stream()
                .map(doneeInstitutionMapper::entityToDto)
                .toList();
    }

    @Transactional
    public void updateDoneeInstitution(int id, DoneeInstitutionDto newDoneeInstitutionDto) {
        if (newDoneeInstitutionDto.getId() != id) {
            throw new UnprocessableRequestException("The IDs provided in the URI and in the request body don't match.");
        }
        if (doneeInstitutionRepository.findById(id).isPresent()) {
            DoneeInstitution newDoneeInstitution = doneeInstitutionMapper.dtoToEntity(newDoneeInstitutionDto);
            newDoneeInstitution.setId(id);
            doneeInstitutionRepository.update(newDoneeInstitution);
        } else {
            throw new DataNotFoundException("Donee Institution not found with id: " + id);
        }
    }

    @Transactional
    public void deleteDoneeInstitutionById(int id) {
        if (doneeInstitutionRepository.findById(id).isPresent()) {
            businessHoursRepository.deleteAllBusinessHoursFromDoneeInstitutionId(id);
            doneeInstitutionRepository.deleteById(id);
        } else {
            throw new DataNotFoundException("Donee Institution not found with id: " + id);
        }
    }

}
