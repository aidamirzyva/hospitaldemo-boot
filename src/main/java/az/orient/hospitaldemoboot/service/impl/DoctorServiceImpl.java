package az.orient.hospitaldemoboot.service.impl;

import az.orient.hospitaldemoboot.dto.request.ReqDoctor;
import az.orient.hospitaldemoboot.dto.request.ReqToken;
import az.orient.hospitaldemoboot.dto.response.RespDoctor;
import az.orient.hospitaldemoboot.dto.response.RespStatus;
import az.orient.hospitaldemoboot.dto.response.Response;
import az.orient.hospitaldemoboot.entity.Doctor;
import az.orient.hospitaldemoboot.enums.EnumAvialableStatus;
import az.orient.hospitaldemoboot.exception.ExceptionConstants;
import az.orient.hospitaldemoboot.exception.HospitalException;
import az.orient.hospitaldemoboot.repository.DoctorRepository;
import az.orient.hospitaldemoboot.repository.UserRepository;
import az.orient.hospitaldemoboot.repository.UserTokenRepository;
import az.orient.hospitaldemoboot.service.DoctorService;
import az.orient.hospitaldemoboot.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserTokenRepository userTokenRepository;
    private final UserRepository userRepository;
    private final Utility utility;

    @Override
    public Response<List<RespDoctor>> getDoctorList(ReqToken reqToken) {
        Response<List<RespDoctor>> response = new Response<>();
        try {
            utility.checkToken(reqToken);
            List<Doctor> doctorList = doctorRepository.findAllByActive(EnumAvialableStatus.ACTIVE.value);
            //list bosdursa onda bu xetani ver///
            if (doctorList.isEmpty()) {
                throw new HospitalException(ExceptionConstants.DOCTOR_NOT_FOUND, "Doctor not found");
            }
            List<RespDoctor> respDoctorList = doctorList.stream().map(this::mapping).collect(Collectors.toList());

            response.setT(respDoctorList);
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
    public Response<RespDoctor> getDoctorById(ReqDoctor reqDoctor) {
        Response<RespDoctor> response = new Response<>();
        try {
            Long doctorId = reqDoctor.getDoctorId();
            utility.checkToken(reqDoctor.getReqToken());
            if (doctorId == null) {
                throw new HospitalException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Doctor doctor = doctorRepository.findDoctorByIdAndActive(doctorId, EnumAvialableStatus.ACTIVE.value);
            if (doctor == null) {
                throw new HospitalException(ExceptionConstants.DOCTOR_NOT_FOUND, "Doctor not found");
            }
            RespDoctor respDoctor = mapping(doctor);
            response.setT(respDoctor);
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
    public Response addDoctor(ReqDoctor reqDoctor) {
        Response response = new Response();

        try {
            String name = reqDoctor.getName();
            String surname = reqDoctor.getSurname();
            ReqToken reqToken = reqDoctor.getReqToken();
            utility.checkToken(reqToken);
            if (name == null || surname == null) {
                throw new HospitalException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Doctor doctor = Doctor.builder().
                    name(name).
                    surname(surname).
                    specialty(reqDoctor.getSpecialty()).
                    pin(reqDoctor.getPin()).
                    seria(reqDoctor.getSeria()).
                    dob(reqDoctor.getDob()).
                    phone(reqDoctor.getPhone()).
                    address(reqDoctor.getAddress()).
                    email(reqDoctor.getEmail()).

                    build();
            //repository save metodu var ozu insert edir. saveicine patient gonderib ozu arxada save edir
            doctorRepository.save(doctor);
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
    public Response updateDoctor(ReqDoctor reqDoctor) {

        Response response = new Response();
        try {
            String name = reqDoctor.getName();
            String surname = reqDoctor.getSurname();
            Long doctorId = reqDoctor.getDoctorId();
            ReqToken reqToken = reqDoctor.getReqToken();
            utility.checkToken(reqToken);
            if (name == null || surname == null || doctorId == null) {
                throw new HospitalException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Doctor doctor = doctorRepository.findDoctorByIdAndActive(doctorId, EnumAvialableStatus.ACTIVE.value);
            if (doctor == null) {
                throw new HospitalException(ExceptionConstants.DOCTOR_NOT_FOUND, "Doctor not found");

            }
            doctor.setName(name);
            doctor.setSurname(surname);
            doctor.setSpecialty(doctor.getSpecialty());
            doctor.setPin(doctor.getPin());
            doctor.setSeria(doctor.getSeria());
            doctor.setDob(doctor.getDob());
            doctor.setPhone(doctor.getPhone());
            doctor.setAddress(doctor.getAddress());
            doctor.setEmail(doctor.getEmail());
            doctorRepository.save(doctor);
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
    public Response deleteDoctor(ReqDoctor reqDoctor) {

        Response response = new Response();
        try {
            Long doctorId = reqDoctor.getDoctorId();
            ReqToken reqToken=reqDoctor.getReqToken();
            utility.checkToken(reqToken);
            if (doctorId == null) {
                throw new HospitalException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Doctor doctor = doctorRepository.findDoctorByIdAndActive(doctorId, EnumAvialableStatus.ACTIVE.value);
            if (doctor == null) {
                throw new HospitalException(ExceptionConstants.DOCTOR_NOT_FOUND, "Doctor not found");

            }
            doctor.setActive(EnumAvialableStatus.DEACTIVE.value);
            doctorRepository.save(doctor);
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

    private RespDoctor mapping(Doctor doctor) {
        RespDoctor respDoctor = RespDoctor.builder().
                doctorId(doctor.getId()).
                name(doctor.getName()).
                surname(doctor.getSurname()).
                specialty(doctor.getSpecialty()).
                pin(doctor.getPin()).
                seria(doctor.getSeria()).
                dob(doctor.getDob()).
                phone(doctor.getPhone()).
                address(doctor.getAddress()).
                email(doctor.getEmail()).

                build();
        return respDoctor;
    }
}




