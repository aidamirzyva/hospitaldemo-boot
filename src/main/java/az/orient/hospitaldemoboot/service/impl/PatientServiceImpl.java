package az.orient.hospitaldemoboot.service.impl;

import az.orient.hospitaldemoboot.dto.request.ReqPatient;
import az.orient.hospitaldemoboot.dto.request.ReqToken;
import az.orient.hospitaldemoboot.dto.response.RespPatient;
import az.orient.hospitaldemoboot.dto.response.RespStatus;
import az.orient.hospitaldemoboot.dto.response.Response;
import az.orient.hospitaldemoboot.entity.Patient;
import az.orient.hospitaldemoboot.enums.EnumAvialableStatus;
import az.orient.hospitaldemoboot.exception.ExceptionConstants;
import az.orient.hospitaldemoboot.exception.HospitalException;
import az.orient.hospitaldemoboot.repository.PatientRepository;
import az.orient.hospitaldemoboot.repository.UserRepository;
import az.orient.hospitaldemoboot.repository.UserTokenRepository;
import az.orient.hospitaldemoboot.service.PatientService;
import az.orient.hospitaldemoboot.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final UserTokenRepository userTokenRepository;
    private final UserRepository userRepository;
    private final Utility utility;
    @Override
    public Response<List<RespPatient>> getPatientList(ReqToken reqToken) {
        Response<List<RespPatient>> response = new Response<>();
        try {
            utility.checkToken(reqToken);

            List<Patient> patientList = patientRepository.findAllByActive(EnumAvialableStatus.ACTIVE.value);
            //list bosdursa onda bu xetani ver///
            if (patientList.isEmpty()) {
                throw new HospitalException(ExceptionConstants.PATIENT_NOT_FOUND, "Patient not found");
            }
            List<RespPatient> respPatientList = patientList.stream().map(this::mapping).collect(Collectors.toList());

            response.setT(respPatientList);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (HospitalException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNALL_EXCEPTION, "Internal Exception"));
            ex.printStackTrace();
        }
        return response;
    }




    @Override
    public Response<RespPatient> getPatientById(ReqPatient reqPatient) {
        Response<RespPatient> response = new Response<>();
        try {
            Long patientId = reqPatient.getPatientId();
            utility.checkToken(reqPatient.getReqToken());
            if (patientId == null) {
                throw new HospitalException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Patient patient = patientRepository.findPatientByIdAndActive(patientId, EnumAvialableStatus.ACTIVE.value);
            if (patient == null) {
                throw new HospitalException(ExceptionConstants.PATIENT_NOT_FOUND, "Patient not found");
            }
            RespPatient respPatient = mapping(patient);
            response.setT(respPatient);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (HospitalException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNALL_EXCEPTION, "Internal Exception"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response addPatient(ReqPatient reqPatient) {
        Response response = new Response();

        try {
            String name = reqPatient.getName();
            String surname = reqPatient.getSurname();
            ReqToken reqToken = reqPatient.getReqToken();
            utility.checkToken(reqToken);
            if (name == null || surname == null) {
                throw new HospitalException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Patient patient = Patient.builder().
                    name(name).
                    surname(surname).
                    pin(reqPatient.getPin()).
                    seria(reqPatient.getSeria()).
                    dob(reqPatient.getDob()).
                    phone(reqPatient.getPhone()).
                    address(reqPatient.getAddress()).
                    blood(reqPatient.getBlood()).
                    email(reqPatient.getEmail()).

                    build();
            //repository save metodu var ozu insert edir. saveicine patient gonderib ozu arxada save edir
            patientRepository.save(patient);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (HospitalException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNALL_EXCEPTION, "Internal Exception"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response updatePatient(ReqPatient reqPatient) {
        Response response = new Response();
        try {
            String name = reqPatient.getName();
            String surname = reqPatient.getSurname();
            Long patientId = reqPatient.getPatientId();
            ReqToken reqToken = reqPatient.getReqToken();
            utility.checkToken(reqToken);
            if (name == null || surname == null || patientId == null) {
                throw new HospitalException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Patient patient = patientRepository.findPatientByIdAndActive(patientId, EnumAvialableStatus.ACTIVE.value);
            if (patient == null) {
                throw new HospitalException(ExceptionConstants.PATIENT_NOT_FOUND, "Patient not found");

            }
            patient.setName(name);
            patient.setSurname(surname);
            patient.setPin(reqPatient.getPin());
            patient.setSeria(reqPatient.getSeria());
            patient.setDob(reqPatient.getDob());
            patient.setPhone(reqPatient.getPhone());
            patient.setAddress(reqPatient.getAddress());
            patient.setBlood(reqPatient.getBlood());
            patient.setEmail(reqPatient.getEmail());
            patientRepository.save(patient);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (HospitalException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNALL_EXCEPTION, "Internal Exception"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response deletePatient(ReqPatient reqPatient) {
        Response response = new Response();
        try {
            Long patientId= reqPatient.getPatientId();
            ReqToken reqToken = reqPatient.getReqToken();
            utility.checkToken(reqToken);
            if (patientId == null) {
                throw new HospitalException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            } //patientId gore obyekt gotururuk sonra onun null-gunu yoxlayiriq
            Patient patient = patientRepository.findPatientByIdAndActive(patientId, EnumAvialableStatus.ACTIVE.value);
            if (patient == null) {
                throw new HospitalException(ExceptionConstants.PATIENT_NOT_FOUND, "Patient not found");

            }
            patient.setActive(EnumAvialableStatus.DEACTIVE.value);
            patientRepository.save(patient);
            response.setStatus(RespStatus.getSuccessMessage());

        } catch (HospitalException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNALL_EXCEPTION, "Internal Exception"));
            ex.printStackTrace();
        }


        return response;
    }

    private RespPatient mapping(Patient patient) {
        RespPatient respPatient = RespPatient.builder().
                patientId(patient.getId()).
                name(patient.getName()).
                surname(patient.getSurname()).
                pin(patient.getPin()).
                seria(patient.getSeria()).
                dob(patient.getDob()).
                phone(patient.getPhone()).
                address(patient.getAddress()).
                blood(patient.getBlood()).
                email(patient.getEmail()).

                build();
        return respPatient;
    }

}