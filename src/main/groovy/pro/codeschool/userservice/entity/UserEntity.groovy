package pro.codeschool.userservice.entity

// TODO Try doing it with Spring data

import org.hibernate.validator.internal.util.stereotypes.Lazy
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import javax.persistence.*

@Entity(name="USERS")
@EntityListeners(AuditingEntityListener)
class UserEntity extends Audit implements Serializable {

    @Id
    @GeneratedValue
    @Column(name="user_id")
    Long id;

    @Column(name="first_name")
    String firstName

    @Column(name="last_name")
    String lastName

    @Column(name="email_address", unique = true)
    String emailAddress

    @Column(name="password")
    String password

    @Column(name="is_enabled")
    boolean isEnabled

    @Column(name="is_validated")
    boolean isValidated

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    Set<UserTokenEntity> userTokens

    @Lazy
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    Set<PasswordReset> passwordResets

}
