package com.tads.mhsf.bazaar.controller;

import com.tads.mhsf.bazaar.dto.BusinessHourDto;
import com.tads.mhsf.bazaar.service.BusinessHourService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/donee-institutions/{doneeInstitutionId}/business-hours")
public class BusinessHourController {

    private final BusinessHourService businessHourService;

    public BusinessHourController(BusinessHourService businessHourService) {
        this.businessHourService = businessHourService;
    }

    @PostMapping
    public ResponseEntity<BusinessHourDto> createBusinessHour(@PathVariable Integer doneeInstitutionId,
                                                              UriComponentsBuilder uriComponentsBuilder,
                                                              @RequestBody @Valid BusinessHourDto businessHourDto) {
        final String url = "api/v1/donee-institutions/{doneeInstitutionId}/business-hours/{id}";
        BusinessHourDto createdDto = businessHourService.createBusinessHour(doneeInstitutionId, businessHourDto);
        URI createdUri = uriComponentsBuilder.path(url).buildAndExpand(doneeInstitutionId, createdDto.getId()).toUri();
        return ResponseEntity.created(createdUri).body(createdDto);
    }

    @GetMapping
    public ResponseEntity<List<BusinessHourDto>> getAllBusinessHours(@PathVariable Integer doneeInstitutionId) {
        return ResponseEntity.ok(businessHourService.findAllBusinessHoursFromDoneeInstitution(doneeInstitutionId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBusinessHour(@PathVariable Integer id,
                                                   @PathVariable Integer doneeInstitutionId,
                                                   @RequestBody @Valid BusinessHourDto businessHourDto) {
        businessHourService.updateBusinessHour(id, doneeInstitutionId, businessHourDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusinessHour(@PathVariable Integer id,
                                                   @PathVariable Integer doneeInstitutionId) {
        businessHourService.deleteBusinessHourById(id, doneeInstitutionId);
        return ResponseEntity.noContent().build();
    }

}
