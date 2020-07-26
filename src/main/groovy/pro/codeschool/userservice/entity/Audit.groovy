package pro.codeschool.userservice.entity

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate

import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
class Audit {

    @Column(name = '_audit_created_date', nullable = false, updatable = false)
    @CreatedDate
    Long createdTimestamp

    @Column(name = '_audit_updated_date')
    @LastModifiedDate
    Long updatedTimestamp

    @Column(name = '_audit_created_by')
    @CreatedBy
    String createdBy;

    @Column(name = '_audit_modified_by')
    @LastModifiedBy
    String modifiedBy;
}
