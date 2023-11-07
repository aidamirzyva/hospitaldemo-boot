package az.orient.hospitaldemoboot.service;

import az.orient.hospitaldemoboot.dto.request.ReqAppointment;
import az.orient.hospitaldemoboot.dto.response.RespAppointment;
import az.orient.hospitaldemoboot.dto.response.Response;

import java.util.List;


public interface AppointmentService {


    Response<List<RespAppointment>> getAppointmentListByPatientId(Long patientId);

//    Response createAppointment(ReqAppointment reqAppointment);

    Response createAppointment(ReqAppointment reqAppointment);

//    Response updateAppointment(ReqAppointment reqAppointment);
//
//    Response deleteAppointment(Long appointmentId);
//
//
//    // Response<List<RespAppointment>> getAppoinmentList();
}
