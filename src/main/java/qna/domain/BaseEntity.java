package qna.domain;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private Long createdBy;

    @LastModifiedBy
    @Column(name = "modified_by")
    private Long modifiedBy;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected LocalDateTime getCreatedAt() {
        return createdAt;
    }

    protected LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
