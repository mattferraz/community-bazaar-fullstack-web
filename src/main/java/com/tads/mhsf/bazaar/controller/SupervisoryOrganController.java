package com.tads.mhsf.bazaar.controller;

import com.tads.mhsf.bazaar.dto.SupervisoryOrganDto;
import com.tads.mhsf.bazaar.service.SupervisoryOrganService;
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
@RequestMapping("api/v1/supervisory-organs")
public class SupervisoryOrganController {

    private final SupervisoryOrganService supervisoryOrganService;

    public SupervisoryOrganController(SupervisoryOrganService supervisoryOrganService) {
        this.supervisoryOrganService = supervisoryOrganService;
    }

    @PostMapping
    public ResponseEntity<SupervisoryOrganDto> createSupervisoryOrgan(UriComponentsBuilder uriComponentsBuilder,
                                                                      @RequestBody @Valid
                                                                      SupervisoryOrganDto supervisoryOrganDTO) {
        final String url = "api/v1/supervisory-organs/{id}";
        SupervisoryOrganDto createdDto = supervisoryOrganService.createSupervisoryOrgan(supervisoryOrganDTO);
        URI createdUri = uriComponentsBuilder.path(url).buildAndExpand(createdDto.getId()).toUri();
        return ResponseEntity.created(createdUri).body(createdDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupervisoryOrganDto> getSupervisoryOrganById(@PathVariable Integer id) {
        return ResponseEntity.ok(supervisoryOrganService.findSupervisoryOrganById(id));
    }

    @GetMapping
    public ResponseEntity<List<SupervisoryOrganDto>> getAllSupervisoryOrgans() {
        return ResponseEntity.ok(supervisoryOrganService.findAllSupervisoryOrgans());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSupervisoryOrganById(@PathVariable Integer id,
                                                           @RequestBody @Valid
                                                           SupervisoryOrganDto supervisoryOrganDto) {
        supervisoryOrganService.updateSupervisoryOrgan(id, supervisoryOrganDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupervisoryOrganById(@PathVariable Integer id) {
        supervisoryOrganService.deleteSupervisoryOrganById(id);
        return ResponseEntity.noContent().build();
    }
}
