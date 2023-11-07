package az.orient.hospitaldemoboot.service.impl;


import az.orient.hospitaldemoboot.dto.request.ReqLaboratory;
import az.orient.hospitaldemoboot.dto.response.RespLaboratory;
import az.orient.hospitaldemoboot.dto.response.RespStatus;
import az.orient.hospitaldemoboot.dto.response.Response;
import az.orient.hospitaldemoboot.entity.Laboratory;
import az.orient.hospitaldemoboot.entity.Patient;
import az.orient.hospitaldemoboot.enums.EnumAvialableStatus;
import az.orient.hospitaldemoboot.exception.ExceptionConstants;
import az.orient.hospitaldemoboot.exception.HospitalException;
import az.orient.hospitaldemoboot.repository.LaboratoryRepository;
import az.orient.hospitaldemoboot.repository.PatientRepository;
import az.orient.hospitaldemoboot.service.LaboratoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LaboratoryServiceImpl implements LaboratoryService {

     private final LaboratoryRepository laboratoryRepository;
     private final PatientRepository patientRepository;


    @Override
    public Response<List<RespLaboratory>> getLaboratoryList() {
        Response<List<RespLaboratory>> response = new Response<>();

        try{
            List<Laboratory> labotoratoryList=laboratoryRepository.findAllByActive(EnumAvialableStatus.ACTIVE.value);
            if(labotoratoryList.isEmpty()){
                throw new HospitalException(ExceptionConstants.LABORATORY_NOT_FOUND, "Laboratory not found");
            }
            List<RespLaboratory> respLaboratoryList=labotoratoryList.stream().map(this::mapping).collect(Collectors.toList());
            response.setT(respLaboratoryList);
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
    public Response<List<RespLaboratory>> getLaboratoryListByPatientId(Long patientId) {
        Response<List<RespLaboratory>> response = new Response<>();
        try{
            if(patientId ==  null) {
                throw new HospitalException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Patient patient = patientRepository.findPatientByIdAndActive(patientId,EnumAvialableStatus.ACTIVE.value);
            if(patient==null){
                throw new HospitalException(ExceptionConstants.PATIENT_NOT_FOUND,"Patient not found");
            }
            List<Laboratory> laboratoryList = laboratoryRepository.findAllByPatientAndActive(patient, EnumAvialableStatus.ACTIVE.value);
            if (laboratoryList.isEmpty()){
                throw new HospitalException(ExceptionConstants.LABORATORY_NOT_FOUND, "Laboratory not found");
            }
            List<RespLaboratory> respLaboratoryList= laboratoryList.stream().map(this::mapping).collect(Collectors.toList());
            response.setT(respLaboratoryList);
            response.setStatus(RespStatus.getSuccessMessage());

        }catch (HospitalException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNALL_EXCEPTION, "Internal Exception"));
            ex.printStackTrace();
        }


        return response;
    }

    @Override
    public Response addLaboratory(ReqLaboratory reqLaboratory) {
        Response response = new Response();

        try{
            String analysisName = reqLaboratory.getAnalysisName();
            String analysisResult = reqLaboratory.getAnalysisResult();
            Double labAmount = reqLaboratory.getLabAmount();
            if( analysisName == null || analysisResult == null || labAmount == null) {
                throw new HospitalException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Laboratory laboratory = Laboratory.builder()
                    .analysisName(analysisName)
                    .analysisResult(analysisResult)
                    .labAmount(labAmount)
                    .build();
            laboratoryRepository.save(laboratory);
            response.setStatus(RespStatus.getSuccessMessage());

        }catch (HospitalException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNALL_EXCEPTION, "Internal Exception"));
            ex.printStackTrace();
        }

        return response;
    }

    @Override
    public Response updateLaboratory(ReqLaboratory reqLaboratory) {
        Response response = new Response();
        try{
            String analysisName = reqLaboratory.getAnalysisName();
            String analysisResult = reqLaboratory.getAnalysisResult();
            Long  laboratoryId = reqLaboratory.getLaboratoryId();
            if( analysisName==null || analysisResult==null || laboratoryId == null ){
                throw new HospitalException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Laboratory laboratory = laboratoryRepository.findLaboratoryByIdAndActive(laboratoryId,EnumAvialableStatus.ACTIVE.value);
            if(laboratory==null){
                throw new HospitalException(ExceptionConstants.LABORATORY_NOT_FOUND, "Laboratory not found");
            }
            laboratory.setAnalysisName(analysisName);
            laboratory.setAnalysisResult(analysisResult);
            laboratoryRepository.save(laboratory);
            response.setStatus(RespStatus.getSuccessMessage());

        }catch (HospitalException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNALL_EXCEPTION, "Internal Exception"));
            ex.printStackTrace();
        }

        return response;
    }

    @Override
    public Response deleteLaboratory(Long laboratoryId) {
        Response response = new Response();
        try{
            if(laboratoryId== null){
                throw new HospitalException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
            }
            Laboratory laboratory = laboratoryRepository.findLaboratoryByIdAndActive(laboratoryId,EnumAvialableStatus.ACTIVE.value);
            if(laboratory == null){
                throw new HospitalException(ExceptionConstants.LABORATORY_NOT_FOUND, "Laboratory not found");
            }
            laboratory.setActive(EnumAvialableStatus.DEACTIVE.value);
            laboratoryRepository.save(laboratory);
            response.setStatus(RespStatus.getSuccessMessage());

        }catch (HospitalException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNALL_EXCEPTION, "Internal Exception"));
            ex.printStackTrace();
        }

        return response;
    }
    private RespLaboratory mapping(Laboratory laboratory){
        RespLaboratory respLaboratory = RespLaboratory.builder()
                        .id(laboratory.getId())
                        .analysisName(laboratory.getAnalysisName())
                        .analysisResult(laboratory.getAnalysisResult())
                        .labAmount(laboratory.getLabAmount())
                        .build();
        return respLaboratory;
    }
}
