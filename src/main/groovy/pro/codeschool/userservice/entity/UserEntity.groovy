package pro.codeschool.userservice.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.*

@Entity(name="USERS")
class UserEntity implements Serializable {

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

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "audit_created_date")
    Date createdDate

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "audit_updatedDate")
    Date updatedDate

    @Column(name = "audit_created_by")
    String createdBy
}
