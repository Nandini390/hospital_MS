package org.example.hospitalmanagementsystem_securitylearning.Controllers;

import lombok.RequiredArgsConstructor;
import org.example.hospitalmanagementsystem_securitylearning.Dto.AppointmentResponseDto;
import org.example.hospitalmanagementsystem_securitylearning.Service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final AppointmentService appointmentService;

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointmentsOfDoctor() {
        return ResponseEntity.ok(appointmentService.getAllAppointmentsOfDoctor(1L));
    }

}
