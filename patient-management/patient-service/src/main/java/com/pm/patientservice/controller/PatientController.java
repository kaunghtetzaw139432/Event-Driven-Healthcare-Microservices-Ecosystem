package com.pm.patientservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.service.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Tag(name = "Patient API", description = "API for managing patients")
public class PatientController {

    private final PatientService patientService;

    @Operation(summary = "Get all patients", description = "Retrieve a list of all registered patients in the system.")
    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        List<PatientResponseDto> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @Operation(summary = "Create a new patient", description = "Register a new patient. Checks for email uniqueness before saving.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Patient already exists with this email")
    })
    @PostMapping
    public ResponseEntity<PatientResponseDto> createPatient(@Valid @RequestBody PatientRequestDto dto) {
        PatientResponseDto createdPatient = patientService.createPatient(dto);
        return ResponseEntity.ok(createdPatient);
    }

    @Operation(summary = "Update an existing patient", description = "Update patient details by UUID. Ensures the new email is not taken by others.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "409", description = "Email already taken by another patient")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(
            @PathVariable UUID id,
            @Valid @RequestBody PatientRequestDto dto) {
        PatientResponseDto response = patientService.updatePatient(id, dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a patient", description = "Remove a patient record from the system using their UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Patient deleted successfully");
        return ResponseEntity.ok(response);
    }
}