package pro.codeschool.userservice.entity

import org.hibernate.annotations.CreationTimestamp

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

    @Column(name="token_expiry", nullable = false)
    long tokenExpiry

    @Column(name="is_expired")
    boolean isExpired = false

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    UserEntity user

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "_audit_create_date")
    private Date createDate;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "_audit_modify_date")
    private Date modifyDate;


}
