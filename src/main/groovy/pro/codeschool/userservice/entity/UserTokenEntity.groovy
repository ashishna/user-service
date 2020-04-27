package pro.codeschool.userservice.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.*

@Entity(name="USER_TOKENS")
class UserTokenEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    Long id

    @Column(name="user_id", insertable = false, updatable = false)
    String userId

    @Column(name="token_value")
    String token

    @Column(name="token_timestamp")
    String dateTime

    @Column(name="is_expired")
    boolean isExpired = false

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    private Date modifyDate;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    UserEntity user

}
