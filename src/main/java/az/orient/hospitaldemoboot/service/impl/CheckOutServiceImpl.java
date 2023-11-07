package az.orient.hospitaldemoboot.service.impl;

import az.orient.hospitaldemoboot.dto.request.ReqCheckOut;
import az.orient.hospitaldemoboot.dto.response.*;
import az.orient.hospitaldemoboot.entity.Appointment;
import az.orient.hospitaldemoboot.entity.CheckOut;
import az.orient.hospitaldemoboot.enums.EnumAvialableStatus;
import az.orient.hospitaldemoboot.exception.ExceptionConstants;
import az.orient.hospitaldemoboot.exception.HospitalException;
import az.orient.hospitaldemoboot.repository.AppointmentRepository;
import az.orient.hospitaldemoboot.repository.CheckOutRepository;
import az.orient.hospitaldemoboot.service.CheckOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckOutServiceImpl implements CheckOutService {

    private final CheckOutRepository checkOutRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public Response<List<RespCheckOut>> getCheckOutList(Long appointmentId) {
        Response<List<RespCheckOut>> response = new Response<>();
        try {
            if(appointmentId == null){
                throw  new HospitalException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
            }
            Appointment appointment = appointmentRepository.findAppointmentByIdAndActive(appointmentId, EnumAvialableStatus.ACTIVE.value);
            if(appointment == null){
                throw  new HospitalException(ExceptionConstants.APPOINTMENT_NOT_FOUND,"Appointment not found");
            }
            List<CheckOut> checkOutList = checkOutRepository.findAllByAppointmentAndActive(appointment,EnumAvialableStatus.ACTIVE.value);
            if(checkOutList.isEmpty()){
                throw new HospitalException(ExceptionConstants.CHECKOUT_NOT_FOUND,"CheckOut not found");
            }
            List<RespCheckOut> respCheckOutList = checkOutList.stream().map(checkOut -> mapping(checkOut)).collect(Collectors.toList());
            response.setT(respCheckOutList);
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

    @Override
    public Response createOperation(ReqCheckOut reqCheckout) {
        Response response = new Response ();
        try{
            Long dtAppointmentId = reqCheckout.getAppointmentId();
             Double totalAmount =reqCheckout.getTotalAmount();
             if(dtAppointmentId==null || totalAmount==null){
                 throw new HospitalException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
             }
             if(totalAmount<=0){
                 throw new HospitalException(ExceptionConstants.INVALID_AMOUNT, "Invalid amount");
             }
             Appointment appointment = appointmentRepository.findAppointmentByIdAndActive(dtAppointmentId,EnumAvialableStatus.ACTIVE.value);
             if(appointment==null){
                 throw new HospitalException(ExceptionConstants.APPOINTMENT_NOT_FOUND,"Appointment not found");
             }

             CheckOut checkOut = CheckOut.builder()
                     .appointment(appointment)
                     .totalAmount(totalAmount)
                     .build();
             checkOutRepository.save(checkOut);
             response.setStatus(RespStatus.getSuccessMessage());

        }catch (HospitalException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNALL_EXCEPTION, "Internal Exception"));
            ex.printStackTrace();
        }


        return null;
    }
    private RespCheckOut mapping(CheckOut checkOut){
        RespPatient respPatient = RespPatient.builder()
                .patientId(checkOut.getAppointment().getPatient().getId())
                .name(checkOut.getAppointment().getPatient().getName())
                .surname(checkOut.getAppointment().getPatient().getSurname())
                .pin(checkOut.getAppointment().getPatient().getPin())
              .build();
        RespDoctor respDoctor = RespDoctor.builder()
                .doctorId(checkOut.getAppointment().getDoctor().getId())
                .name(checkOut.getAppointment().getDoctor().getName())
                .surname(checkOut.getAppointment().getDoctor().getSurname())
                .build();
        RespLaboratory respLaboratory = RespLaboratory.builder()
                .analysisName(checkOut.getAppointment().getLaboratory().getAnalysisName())
                .analysisResult(checkOut.getAppointment().getLaboratory().getAnalysisResult())
                .build();



        RespAppointment respAppointment = RespAppointment.builder()
                .appointmentNo(checkOut.getAppointment().getAppointmentNo())
                .examinationPrice(checkOut.getAppointment().getExaminationPrice())
                .respPatient(respPatient)
                .respDoctor(respDoctor)
                .respLaboratory(respLaboratory)
                .build();

        RespCheckOut respCheckOut= RespCheckOut.builder()
                .appointment(respAppointment)
                .totalAmount(checkOut.getTotalAmount())
                .operationDate(checkOut.getDataDate())
                .build();
        return respCheckOut;
    }
}
