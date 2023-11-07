package az.orient.hospitaldemoboot.service.impl;

import az.orient.hospitaldemoboot.dto.request.ReqAppointment;
import az.orient.hospitaldemoboot.dto.response.*;
import az.orient.hospitaldemoboot.entity.Appointment;
import az.orient.hospitaldemoboot.entity.Doctor;
import az.orient.hospitaldemoboot.entity.Laboratory;
import az.orient.hospitaldemoboot.entity.Patient;
import az.orient.hospitaldemoboot.enums.EnumAvialableStatus;
import az.orient.hospitaldemoboot.exception.ExceptionConstants;
import az.orient.hospitaldemoboot.exception.HospitalException;
import az.orient.hospitaldemoboot.repository.AppointmentRepository;
import az.orient.hospitaldemoboot.repository.DoctorRepository;
import az.orient.hospitaldemoboot.repository.LaboratoryRepository;
import az.orient.hospitaldemoboot.repository.PatientRepository;
import az.orient.hospitaldemoboot.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final LaboratoryRepository laboratoryRepository;

    @Override
    public Response<List<RespAppointment>> getAppointmentListByPatientId(Long patientId) {
        Response<List<RespAppointment>> response = new Response<>();
        try {
            if (patientId == null) {
                throw new HospitalException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Patient patient = patientRepository.findPatientByIdAndActive(patientId, EnumAvialableStatus.ACTIVE.value);
            if (patient == null) {
                throw new HospitalException(ExceptionConstants.PATIENT_NOT_FOUND, "Patient not founf");
            }
            List<Appointment> appointmentList = appointmentRepository.findAllByPatientAndActive(patient, EnumAvialableStatus.ACTIVE.value);
            if (appointmentList.isEmpty()) {
                throw new HospitalException(ExceptionConstants.APPOINTMENT_NOT_FOUND, "Appointment not found");
            }
            List <RespAppointment> respAppointmentList = appointmentList.stream().map(this::mapping).collect(Collectors.toList());
            response.setT(respAppointmentList);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (HospitalException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNALL_EXCEPTION, "Internal Exception"));
            ex.printStackTrace();
        }


        return null;
    }

    @Override
    public Response createAppointment(ReqAppointment reqAppointment) {
          Response response = new Response ();
          try{
              Integer appointmentNo= reqAppointment.getAppointmentNo();
              Double examinationPrice =reqAppointment.getExaminationPrice();
              Long doctorId = reqAppointment.getDoctorId();
              Long patientId = reqAppointment.getPatientId();
              Long laboratoryId = reqAppointment.getLaboratoryId();
              if( appointmentNo == null || examinationPrice==null || doctorId==null ||patientId==null ) {
                  throw new HospitalException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
              }
              Patient patient = patientRepository.findPatientByIdAndActive(patientId,EnumAvialableStatus.ACTIVE.value);
              if(patient == null){
                  throw  new HospitalException(ExceptionConstants.PATIENT_NOT_FOUND,"Patient not found");

              }

              Doctor doctor = doctorRepository.findDoctorByIdAndActive(doctorId,EnumAvialableStatus.ACTIVE.value);
              if(doctor == null){
                  throw new HospitalException(ExceptionConstants.DOCTOR_NOT_FOUND,"Doctor not found");
              }

              Laboratory laboratory = laboratoryRepository.findLaboratoryByIdAndActive(laboratoryId,EnumAvialableStatus.ACTIVE.value);
              if(laboratory == null){
                  throw new HospitalException(ExceptionConstants.LABORATORY_NOT_FOUND,"Laboratory not found");
              }
              Appointment appointment = Appointment.builder()
                      .appointmentNo(appointmentNo)
                      .examinationPrice(examinationPrice)
                      .patient(patient)
                      .doctor(doctor)
                      .laboratory(laboratory)
                      .build();
              appointmentRepository.save(appointment);
              response.setStatus(RespStatus.getSuccessMessage());
          }catch (HospitalException ex) {
              response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
              ex.printStackTrace();
          } catch (Exception ex) {
              response.setStatus(new RespStatus(ExceptionConstants.INTERNALL_EXCEPTION, "Internal Exception"));
              ex.printStackTrace();
          }
     return response;
    }

    private RespAppointment mapping(Appointment appointment) {
        RespPatient respPatient = RespPatient.builder()
                .name(appointment.getPatient().getName())
                .surname(appointment.getPatient().getSurname())
                .build();
        RespDoctor respDoctor = RespDoctor.builder()
                .name(appointment.getDoctor().getName())
                .surname(appointment.getDoctor().getSurname())
                .build();
        RespLaboratory respLaboratory = RespLaboratory.builder()
                .analysisName(appointment.getLaboratory().getAnalysisName())
                .analysisResult(appointment.getLaboratory().getAnalysisResult())
                .labAmount(appointment.getLaboratory().getLabAmount())
                .build();

        RespAppointment respAppointment = RespAppointment.builder()
                .id(appointment.getId())
                .appointmentNo(appointment.getAppointmentNo())
                .respPatient(respPatient)
                .respDoctor(respDoctor)
                .respLaboratory(respLaboratory)
                .build();

        return respAppointment;
    }

}
