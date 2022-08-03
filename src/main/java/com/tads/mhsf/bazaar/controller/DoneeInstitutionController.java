package com.tads.mhsf.bazaar.controller;

import com.tads.mhsf.bazaar.dto.DoneeInstitutionDto;
import com.tads.mhsf.bazaar.service.DoneeInstitutionService;
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
@RequestMapping("api/v1/donee-institutions")
public class DoneeInstitutionController {

    private final DoneeInstitutionService doneeInstitutionService;

    public DoneeInstitutionController(DoneeInstitutionService doneeInstitutionService) {
        this.doneeInstitutionService = doneeInstitutionService;
    }

    @PostMapping
    public ResponseEntity<DoneeInstitutionDto> createDoneeInstitution(UriComponentsBuilder uriComponentsBuilder,
                                                                      @RequestBody @Valid
                                                                      DoneeInstitutionDto doneeInstitutionDto) {
        final String url = "api/v1/donee-institutions/{id}";
        DoneeInstitutionDto createdDto = doneeInstitutionService.createDoneeInstitution(doneeInstitutionDto);
        URI createdUri = uriComponentsBuilder.path(url).buildAndExpand(createdDto.getId()).toUri();
        return ResponseEntity.created(createdUri).body(createdDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoneeInstitutionDto> getDoneeInstitutionById(@PathVariable Integer id) {
        return ResponseEntity.ok(doneeInstitutionService.findDoneeInstitutionById(id));
    }

    @GetMapping
    public ResponseEntity<List<DoneeInstitutionDto>> getAllDoneeInstitutions() {
        return ResponseEntity.ok(doneeInstitutionService.findAllDoneeInstitutions());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDoneeInstitution(@PathVariable Integer id,
                                                       @RequestBody @Valid
                                                       DoneeInstitutionDto doneeInstitutionDto) {
        doneeInstitutionService.updateDoneeInstitution(id, doneeInstitutionDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoneeInstitution(@PathVariable Integer id) {
        doneeInstitutionService.deleteDoneeInstitutionById(id);
        return ResponseEntity.noContent().build();
    }

}
