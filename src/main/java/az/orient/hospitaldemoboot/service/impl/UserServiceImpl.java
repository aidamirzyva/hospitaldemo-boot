package az.orient.hospitaldemoboot.service.impl;

import az.orient.hospitaldemoboot.dto.request.ReqLogin;
import az.orient.hospitaldemoboot.dto.request.ReqToken;
import az.orient.hospitaldemoboot.dto.response.RespStatus;
import az.orient.hospitaldemoboot.dto.response.RespToken;
import az.orient.hospitaldemoboot.dto.response.RespUser;
import az.orient.hospitaldemoboot.dto.response.Response;
import az.orient.hospitaldemoboot.entity.User;
import az.orient.hospitaldemoboot.entity.UserToken;
import az.orient.hospitaldemoboot.enums.EnumAvialableStatus;
import az.orient.hospitaldemoboot.exception.ExceptionConstants;
import az.orient.hospitaldemoboot.exception.HospitalException;
import az.orient.hospitaldemoboot.repository.UserRepository;
import az.orient.hospitaldemoboot.repository.UserTokenRepository;
import az.orient.hospitaldemoboot.service.UserService;
import az.orient.hospitaldemoboot.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final Utility utility;
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    @Override
    public Response<RespUser> login(ReqLogin reqLogin) {
        Response<RespUser> response = new Response<>();
        RespUser respUser = new RespUser();
        try{
           String username =  reqLogin.getUsername();
           String password = reqLogin.getPassword();
           if( username == null || password==null) {
               throw  new HospitalException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
           }
           User user =userRepository.findUserByUsernameAndPasswordAndActive(username,password, EnumAvialableStatus.ACTIVE.value);
            if (user == null){
                throw  new HospitalException(ExceptionConstants.USER_NOT_FOUND,"User not found" );
            }
            String token = UUID.randomUUID().toString();
            UserToken userToken = UserToken.builder()
                    .user(user)
                    .token(token)
                    .build();
            userTokenRepository.save(userToken);
            respUser.setUsername(username);
            respUser.setFullName(user.getFullName());
            respUser.setRespToken(new RespToken(user.getId(), token));
            response.setT(respUser);
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
    public Response logout(ReqToken reqToken) {
        Response response = new Response<>();
        try {
            UserToken userToken = utility.checkToken(reqToken);
            userToken.setActive(EnumAvialableStatus.DEACTIVE.value);
            userTokenRepository.save(userToken);
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
}
