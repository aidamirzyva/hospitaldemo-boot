package az.orient.hospitaldemoboot.service;

import az.orient.hospitaldemoboot.dto.request.ReqDoctor;
import az.orient.hospitaldemoboot.dto.request.ReqToken;
import az.orient.hospitaldemoboot.dto.response.RespDoctor;
import az.orient.hospitaldemoboot.dto.response.Response;

import java.util.List;



public interface DoctorService {
    Response<List<RespDoctor>> getDoctorList(ReqToken reqToken);

    Response<RespDoctor> getDoctorById(ReqDoctor reqDoctor);

    Response addDoctor(ReqDoctor reqDoctor);

    Response updateDoctor(ReqDoctor reqDoctor);

    Response deleteDoctor(ReqDoctor reqDoctor);
}
