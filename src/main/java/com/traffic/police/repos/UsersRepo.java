package com.traffic.police.repos;

import com.traffic.police.models.ApplicationUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<ApplicationUsersEntity, Long> {
    @Query("SELECT a FROM ApplicationUsersEntity a WHERE a.email = :email and a.password=:password")
    ApplicationUsersEntity findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    @Query("SELECT a FROM ApplicationUsersEntity a WHERE a.email = :email ")
    ApplicationUsersEntity findByEmail(@Param("email") String email);

    @Query("SELECT a FROM ApplicationUsersEntity a WHERE a.userid = :userid ")
    ApplicationUsersEntity findByUserid(@Param("userid") int userid);

}
