package org.example.hospitalmanagementsystem_securitylearning.Controllers;

import lombok.RequiredArgsConstructor;
import org.example.hospitalmanagementsystem_securitylearning.Dto.AppointmentResponseDto;
import org.example.hospitalmanagementsystem_securitylearning.Dto.CreateAppointmentRequestDto;
import org.example.hospitalmanagementsystem_securitylearning.Dto.PatientResponseDto;
import org.example.hospitalmanagementsystem_securitylearning.Service.AppointmentService;
import org.example.hospitalmanagementsystem_securitylearning.Service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponseDto> createNewAppointment(@RequestBody CreateAppointmentRequestDto createAppointmentRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createNewAppointment(createAppointmentRequestDto));
    }

    @GetMapping("/profile")
    public ResponseEntity<PatientResponseDto> getPatientProfile() {
        Long patientId = 4L;
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }

}
