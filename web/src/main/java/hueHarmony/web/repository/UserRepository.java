package hueHarmony.web.repository;

import hueHarmony.web.dto.UserAuthDto;
import hueHarmony.web.dto.UserProfileDto;
import hueHarmony.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT NEW hueHarmony.web.dto.UserAuthDto(" +
            "   u.userId, " +
            "   u.password, " +
            "   (SELECT STRING_AGG(r.roleName, ',') FROM Role r JOIN u.roles ur WHERE r.roleId = ur.roleId)) " +
            "FROM User u " +
            "WHERE u.username = :username")
    UserAuthDto findUserByUserName(@Param("username") String username);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username")
    boolean existsByUsername(String username);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
    boolean existsByEmail(String email);

    @Query("SELECT NEW hueHarmony.web.dto.UserProfileDto(" +
            "u.userId," +
            "u.profileImage)" +
            " FROM User u" +
            " WHERE u.username = :username")
    UserProfileDto getUserProfileByUsername(@Param("username") String username);

}
