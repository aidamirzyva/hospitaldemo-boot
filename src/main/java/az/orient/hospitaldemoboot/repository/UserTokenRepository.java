package az.orient.hospitaldemoboot.repository;


import az.orient.hospitaldemoboot.entity.UserToken;
import az.orient.hospitaldemoboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken,Long> {


     UserToken findUserTokenByUserAndTokenAndActive(User user, String token, Integer active);
}
