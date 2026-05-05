package com.pm.patientservice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.pm.billing.grpc.BillingServiceGrpc;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.kafka.kafkaProducer;
import org.springframework.stereotype.Service;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.exception.PatientAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repo.PatientRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientService {
    private final PatientRepo patientRepo;
    private  final BillingServiceGrpcClient billingServiceGrpcClient;
    private final kafkaProducer kafkaProducer;

    public List<PatientResponseDto> getAllPatients() {
        List<Patient> patients = patientRepo.findAll();
        List<PatientResponseDto> patientDtos = patients.stream()
                .map(PatientMapper::ToDTO)
                .toList();
        return patientDtos;
    }

    public PatientResponseDto createPatient(PatientRequestDto dto) {
        if (patientRepo.existsByEmail(dto.getEmail())) {
            throw new PatientAlreadyExistsException("Patient already exists with email: " + dto.getEmail());
        }
        Patient patient = patientRepo.save(
                PatientMapper.ToEntity(dto));
        billingServiceGrpcClient.createBillingAccount(patient.getId().toString(),patient.getName(),patient.getEmail());
        kafkaProducer.sendEvent(patient);
        return PatientMapper.ToDTO(patient);
    }

    public PatientResponseDto updatePatient(UUID id, PatientRequestDto dto) {
        Patient patient = patientRepo.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));
        if (patientRepo.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new PatientAlreadyExistsException("Patient already exists with email: " + dto.getEmail());
        }
        patient.setName(dto.getName());
        patient.setAddress(dto.getAddress());
        patient.setEmail(dto.getEmail());
        patient.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
         Patient updatedPatient = patientRepo.save(patient);
        return PatientMapper.ToDTO(updatedPatient);
    }
    public void deletePatient(UUID id) {
        if (!patientRepo.existsById(id)) {
            throw new PatientNotFoundException("Patient not found with id: " + id);
        }
        patientRepo.deleteById(id);
    }
}
