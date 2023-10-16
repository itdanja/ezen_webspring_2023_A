package ezenweb.model.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseTime {
    @CreatedDate
    private LocalDateTime cdate; // 레코드/엔티티 생성날짜
    @LastModifiedDate
    private LocalDateTime udate; // 레코드/엔티티 수정날짜
}
/*
    엔티티의 생성/수정 일시를 감지해서 자동 업데이트 해주는 클래스
    어노테이션
        1. @CreatedDate
        2. @LastModifiedDate
        3. @MappedSuperclass
        4. @EntityListeners( AuditingEntityListener.class )

 */
