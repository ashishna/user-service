package pro.codeschool.userservice.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.validation.constraints.NotNull

@Entity(name = "PASSWORD_RESET")
class PasswordReset {

    @Id
    @GeneratedValue
    @Column(name="id")
    long id

    @NotNull
    @Column(name="date_time")
    long dateTime

    @NotNull
    @Column(name="temp_token")
    String token

    @NotNull
    @Column(name="is_expired")
    boolean isExpired

    @NotNull
    @Column(name="is_used")
    boolean isUsed

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    UserEntity user
}
