package az.orient.hospitaldemoboot.util;

import az.orient.hospitaldemoboot.dto.request.ReqToken;
import az.orient.hospitaldemoboot.entity.User;
import az.orient.hospitaldemoboot.entity.UserToken;
import az.orient.hospitaldemoboot.enums.EnumAvialableStatus;
import az.orient.hospitaldemoboot.exception.ExceptionConstants;
import az.orient.hospitaldemoboot.exception.HospitalException;
import az.orient.hospitaldemoboot.repository.UserRepository;
import az.orient.hospitaldemoboot.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Utility {

    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    public UserToken checkToken(ReqToken reqToken){

        Long userId = reqToken.getUserId();
        String token = reqToken.getToken();
        if(userId==null || token == null){
            throw new HospitalException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
        }
        User user = userRepository.findUserByIdAndActive(userId, EnumAvialableStatus.ACTIVE.value);
        if(user==null){
            throw new HospitalException(ExceptionConstants.USER_NOT_FOUND,"User not found");
        }
        UserToken userToken =userTokenRepository.findUserTokenByUserAndTokenAndActive(user,token,EnumAvialableStatus.ACTIVE.value);
        if(userToken==null){
            throw new HospitalException(ExceptionConstants.INVALID_TOKEN,"Invalid token");
        }

        return userToken;
    }


}
