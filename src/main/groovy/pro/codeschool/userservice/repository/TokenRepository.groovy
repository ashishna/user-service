package pro.codeschool.userservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import pro.codeschool.userservice.entity.UserTokenEntity

interface TokenRepository extends JpaRepository<UserTokenEntity, Long> {

    @Modifying
    @Query('delete from USER_TOKENS ut where ut.tokenExpiry <= ?1')
    void purgeExpired(long epochSecond)

}
