package az.orient.hospitaldemoboot.service;

import az.orient.hospitaldemoboot.dto.request.ReqCheckOut;
import az.orient.hospitaldemoboot.dto.response.RespCheckOut;
import az.orient.hospitaldemoboot.dto.response.Response;

import java.util.List;

public interface CheckOutService {
    Response<List<RespCheckOut>> getCheckOutList(Long appintmentId);

    Response createOperation(ReqCheckOut reqCheckout);
}
