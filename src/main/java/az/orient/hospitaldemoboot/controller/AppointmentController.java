package az.orient.hospitaldemoboot.controller;

import az.orient.hospitaldemoboot.dto.request.ReqAppointment;
import az.orient.hospitaldemoboot.dto.response.RespAppointment;
import az.orient.hospitaldemoboot.dto.response.Response;
import az.orient.hospitaldemoboot.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/GetAppointmentListByPatientId/{patientId}")
    public Response<List<RespAppointment>> getAppointmentListByPatientId(@PathVariable Long patientId) {

        return appointmentService.getAppointmentListByPatientId(patientId);
    }

    @PostMapping("/CreateAppointment")
    public Response createAppointment(@RequestBody ReqAppointment reqAppointment) {
        return appointmentService.createAppointment(reqAppointment);
    }
//
//    @PutMapping("/UpdatePatient")
//    public Response updateAppointment(@RequestBody ReqAppointment reqAppointment) {
//        return appointmentService.updateAppointment(reqAppointment);
//    }
//    @PutMapping("/DeleteAppointment/{appointmentId}") //qarsisinda yazilan path varieble olanda mutleq yazilmalidi
//    public Response deleteAppointment(@PathVariable Long appointmentId) {
//
//        return appointmentService.deleteAppointment(appointmentId);
//    }
}
