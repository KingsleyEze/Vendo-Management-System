package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.domains.constants.UserType;
import org.ng.undp.vdms.domains.security.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Samuel on 10/13/2016.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT q FROM  User q WHERE  q.userTypes IN (:userTypes)")
    public List<User> findAllByUserTypes(@Param("userTypes") String[] userTypes);



    public List<User> findAllByRoles(  Set<Role> userTypes);

    @Query("SELECT q FROM  User q WHERE  q.userTypes IN (:userTypes) and q.deleted_at is NULL")
    public List<User> findAllByRoles(@Param("userTypes") String[] userTypes);


    public User findOneByUsername(String username);
    public User findOneByUuid(String uuid);

    public User findOneByUsernameAndPassword(String username, String password);

   public User findByResetPasswordToken(String token);
    public User findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update User u set u.lastLoggedIn = ?1 where u.username = ?2")
    public void setFixedLastLoggedIn(LocalDateTime lastLoggedIn, String username);





    @Query("SELECT u FROM User u WHERE  u.id IN(:userIds)")
    public List<User> findByUserIds(@Param("userIds") List<Long> userIds);
}

