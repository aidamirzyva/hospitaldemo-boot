package az.orient.hospitaldemoboot.controller;

import az.orient.hospitaldemoboot.dto.request.ReqPatient;
import az.orient.hospitaldemoboot.dto.request.ReqToken;
import az.orient.hospitaldemoboot.dto.response.RespPatient;
import az.orient.hospitaldemoboot.dto.response.Response;
import az.orient.hospitaldemoboot.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;


    @PostMapping("/GetPatientList")
    public Response<List<RespPatient>> getPatientList(@RequestBody ReqToken reqToken) {


        return patientService.getPatientList(reqToken);
    }


    @PostMapping("/GetPatientById")
    public Response<RespPatient> getPatientById(@RequestBody ReqPatient reqPatient){
        return patientService.getPatientById(reqPatient);


    }

    @PostMapping("/AddPatient")
    public Response addPatient(@RequestBody ReqPatient reqPatient) {
        return patientService.addPatient(reqPatient);

    }

    @PutMapping("/UpdatePatient")
    public Response updatePatient(@RequestBody ReqPatient reqPatient) {
        return patientService.updatePatient(reqPatient);
    }

    @PutMapping("/DeletePatient") //qarsisinda yazilan path varieble olanda mutleq yazilmalidi
    public Response deletePatient(@RequestBody ReqPatient reqPatient) {

        return patientService.deletePatient(reqPatient);
    }

}

// 1. QueryParam => RequestParam http://localhost:8082/hospital/patient/GetPatientById?patiendId=7
// 2. PathParam => PathVariable http://localhost:8082/hospital/patient/GetPatientById?=7 bu daha elverislidi
// 3. Consume =>  meselen---  json, xml {"id":1, "name": "Samir"}