package az.orient.hospitaldemoboot.controller;

import az.orient.hospitaldemoboot.dto.request.ReqCheckOut;
import az.orient.hospitaldemoboot.dto.response.RespCheckOut;
import az.orient.hospitaldemoboot.dto.response.Response;
import az.orient.hospitaldemoboot.service.CheckOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkOut")
@RequiredArgsConstructor
public class CheckOutController {

    private final CheckOutService checkOutService;



    @GetMapping("/GetCheckOutList/{appintmentId}")
    public Response<List<RespCheckOut>> getCheckOutList(@PathVariable Long appintmentId){


        return checkOutService.getCheckOutList(appintmentId);
    }
    @PostMapping("/CreateOperation")

    public Response createOperation(@RequestBody ReqCheckOut reqCheckout){

        return checkOutService.createOperation(reqCheckout);
    }
}
