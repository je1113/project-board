package com.koreait.projectboard.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)      // 사용할 수 있게끔 Listener로 등록.
@MappedSuperclass   // 상속 시 부모한테 걸어주는 거
public class AuditingFields {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable=false) private LocalDateTime createdAt;           // 생성일시 // @CreatedDate: 생성일시 자동으로 넣어줌

    @CreatedBy
    @Column(nullable=false, length=100) private String createdBy;        // 생성자   // @CreatedBy:

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(nullable=false) private LocalDateTime modifiedAt;     // 수정일시 // @LastModifiedDate: 작성된 날짜, 수정된 날짜에 모두 자동으로 넣어줌

    @LastModifiedBy
    @Column(nullable=false, length=100) private String modifiedBy;  // 수정자   // @LastModifiedBy:
}
