package az.orient.hospitaldemoboot.service;

import az.orient.hospitaldemoboot.dto.request.ReqLaboratory;
import az.orient.hospitaldemoboot.dto.response.RespLaboratory;
import az.orient.hospitaldemoboot.dto.response.Response;

import java.util.List;

public interface LaboratoryService {

    Response<List<RespLaboratory>> getLaboratoryList();

    Response<List<RespLaboratory>> getLaboratoryListByPatientId(Long patientId);

    Response addLaboratory(ReqLaboratory reqLaboratory);

    Response updateLaboratory(ReqLaboratory reqLaboratory);

    Response deleteLaboratory(Long laboratoryId);
}
