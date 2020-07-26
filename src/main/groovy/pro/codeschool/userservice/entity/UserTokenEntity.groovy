package pro.codeschool.userservice.entity

import org.springframework.data.jpa.domain.support.AuditingEntityListener

import javax.persistence.*

@Entity(name="USER_TOKENS")
@EntityListeners(AuditingEntityListener)
class UserTokenEntity extends Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    Long id

    @Column(name="user_id", insertable = false, updatable = false)
    String userId

    @Column(name="token_value")
    String token

    @Column(name="token_expiry", nullable = false)
    long tokenExpiry

    @Column(name="is_expired")
    boolean isExpired = false

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    UserEntity user

}
