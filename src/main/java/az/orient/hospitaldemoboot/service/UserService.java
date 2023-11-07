package az.orient.hospitaldemoboot.service;

import az.orient.hospitaldemoboot.dto.request.ReqLogin;
import az.orient.hospitaldemoboot.dto.request.ReqToken;
import az.orient.hospitaldemoboot.dto.response.RespUser;
import az.orient.hospitaldemoboot.dto.response.Response;

public interface UserService {
    Response<RespUser> login(ReqLogin reqLogin);

    Response logout(ReqToken reqToken);
}
