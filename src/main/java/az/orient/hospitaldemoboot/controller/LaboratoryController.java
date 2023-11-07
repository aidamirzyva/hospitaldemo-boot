package az.orient.hospitaldemoboot.controller;

import az.orient.hospitaldemoboot.dto.request.ReqLaboratory;
import az.orient.hospitaldemoboot.dto.response.RespLaboratory;
import az.orient.hospitaldemoboot.dto.response.Response;
import az.orient.hospitaldemoboot.service.LaboratoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/laboratory")
@RequiredArgsConstructor
public class LaboratoryController {

    private final LaboratoryService laboratoryService;

    @GetMapping("/GetLaboratoryList")
    public Response<List<RespLaboratory>> getLaboratoryList() {


        return laboratoryService.getLaboratoryList();
    }


    @GetMapping("/GetLaboratoryListByPatientId/{patientId}")
    public Response<List<RespLaboratory>> getLaboratoryListByPatientId(@PathVariable Long patientId) {

        return laboratoryService.getLaboratoryListByPatientId(patientId);
    }

    @PostMapping("/AddLaboratory")

    public Response addLaboratory(@RequestBody ReqLaboratory reqLaboratory) {
        return laboratoryService.addLaboratory(reqLaboratory);

    }

    @PutMapping("/UpdateLaboratory")
    public Response updateDoctor(@RequestBody ReqLaboratory reqLaboratory) {
        return laboratoryService.updateLaboratory(reqLaboratory);
    }


    @PutMapping("/DeleteLaboratory/{laboratoryId}") //qarsisinda yazilan path varieble olanda mutleq yazilmalidi
    public Response deleteLaboratory(@PathVariable Long laboratoryId) {

        return laboratoryService.deleteLaboratory(laboratoryId);
    }



}
