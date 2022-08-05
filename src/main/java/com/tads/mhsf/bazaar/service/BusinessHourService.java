package com.tads.mhsf.bazaar.service;

import com.tads.mhsf.bazaar.dto.BusinessHourDto;
import com.tads.mhsf.bazaar.entity.BusinessHour;
import com.tads.mhsf.bazaar.exception.DataNotFoundException;
import com.tads.mhsf.bazaar.mapper.BusinessHourMapper;
import com.tads.mhsf.bazaar.repository.BusinessHoursRepository;
import com.tads.mhsf.bazaar.repository.DoneeInstitutionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BusinessHourService {

    private final BusinessHourMapper businessHourMapper;
    private final BusinessHoursRepository businessHoursRepository;
    private final DoneeInstitutionRepository doneeInstitutionRepository;

    public BusinessHourService(BusinessHourMapper businessHourMapper,
                               BusinessHoursRepository businessHoursRepository,
                               DoneeInstitutionRepository doneeInstitutionRepository) {
        this.businessHourMapper = businessHourMapper;
        this.businessHoursRepository = businessHoursRepository;
        this.doneeInstitutionRepository = doneeInstitutionRepository;
    }

    @Transactional
    public BusinessHourDto createBusinessHour(Integer doneeInstitutionId, BusinessHourDto businessHourDto) {
        if (doneeInstitutionRepository.findById(doneeInstitutionId).isPresent()) {
            BusinessHour businessHour = businessHourMapper.dtoToEntity(businessHourDto);
            return businessHourMapper.entityToDto(businessHoursRepository.save(businessHour));
        } else {
            throw new DataNotFoundException("Donee Institution not found with id: " + doneeInstitutionId);
        }
    }

    @Transactional
    public List<BusinessHourDto> findAllBusinessHoursFromDoneeInstitution(Integer doneeInstitutionId) {
        if (doneeInstitutionRepository.findById(doneeInstitutionId).isPresent()) {
            return businessHoursRepository
                    .findAllBusinessHoursFromDoneeInstitutionId(doneeInstitutionId)
                    .stream()
                    .map(businessHourMapper::entityToDto)
                    .toList();
        } else {
            throw new DataNotFoundException("Donee Institution not found with id: " + doneeInstitutionId);
        }
    }

    @Transactional
    public void updateBusinessHour(int id, Integer doneeInstitutionId, BusinessHourDto businessHourDto) {
        BusinessHour businessHour = businessHoursRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Business hour not found with id: " + id));

        if (businessHour.getDoneeInstitution().getId().equals(doneeInstitutionId)) {
            BusinessHour newBusinessHour = businessHourMapper.dtoToEntity(businessHourDto);
            newBusinessHour.setId(id);
            businessHoursRepository.update(newBusinessHour);
        } else {
            throw new DataNotFoundException("Donee institution with id " + doneeInstitutionId +
                    "doesn't have business hour with id: " + id);
        }
    }

    @Transactional
    public void deleteBusinessHourById(int id, Integer doneeInstitutionId) {
        BusinessHour businessHour = businessHoursRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Business hour not found with id: " + id));

        if (businessHour.getDoneeInstitution().getId().equals(doneeInstitutionId)) {
            businessHoursRepository.deleteById(id);
        } else {
            throw new DataNotFoundException("Donee institution with id " + doneeInstitutionId +
                    "doesn't have business hour with id: " + id);
        }

    }

}
