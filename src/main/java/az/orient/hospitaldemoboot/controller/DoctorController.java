package az.orient.hospitaldemoboot.controller;

import az.orient.hospitaldemoboot.dto.request.ReqDoctor;
import az.orient.hospitaldemoboot.dto.request.ReqToken;
import az.orient.hospitaldemoboot.dto.response.RespDoctor;
import az.orient.hospitaldemoboot.dto.response.Response;
import az.orient.hospitaldemoboot.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {


    private final DoctorService doctorService;

    @PostMapping("/GetDoctorList")
    public Response<List<RespDoctor>> getDoctorList(@RequestBody ReqToken reqToken) {

        return doctorService.getDoctorList(reqToken);

    }

    @PostMapping("/GetDoctorById")
    public Response<RespDoctor> getDoctorById(@RequestBody ReqDoctor reqDoctor) {

        return doctorService.getDoctorById(reqDoctor);


    }

    @PostMapping("/AddDoctor")
    //doctor add yaratmaq ucun/ add edende coxlu sayda melumat gedir ona gore parametr olaraq json gondereceyik. cansimul--springde @RequestBody gedir
    public Response addDoctor(@RequestBody ReqDoctor reqDoctor) {
        return doctorService.addDoctor(reqDoctor);

    }

    @PutMapping("/UpdateDoctor")
    public Response updateDoctor(@RequestBody ReqDoctor reqDoctor) {
        return doctorService.updateDoctor(reqDoctor);
    }


    @PutMapping("/DeleteDoctor") //qarsisinda yazilan path varieble olanda mutleq yazilmalidi
    public Response deleteDoctor(@RequestBody ReqDoctor reqDoctor) {
        return doctorService.deleteDoctor(reqDoctor);
    }


}

