package az.orient.hospitaldemoboot.service;

import az.orient.hospitaldemoboot.dto.request.ReqPatient;
import az.orient.hospitaldemoboot.dto.request.ReqToken;
import az.orient.hospitaldemoboot.dto.response.RespPatient;
import az.orient.hospitaldemoboot.dto.response.Response;

import java.util.List;


public interface PatientService {


    Response<List<RespPatient>> getPatientList(ReqToken reqToken);


    Response<RespPatient> getPatientById(ReqPatient reqPatient);

    Response addPatient(ReqPatient reqPatient);

    Response updatePatient(ReqPatient reqPatient);

    Response deletePatient(ReqPatient reqPatient);
}
