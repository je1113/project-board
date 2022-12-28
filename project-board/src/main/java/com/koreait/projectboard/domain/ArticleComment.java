package com.koreait.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter @ManyToOne(optional = false) private Article article;   // 게시글(id)    // 객체로 쓰려고 뒤에 id 안붙임  // 게시글 번호 하나에 댓글 여러개 등록될 수 있음. @ManyToOne, optional = false: null 값이 되더라도 값을 false로 두고 처리해야 되도록 만들어줌.
    @Setter @Column(nullable = false, length = 500) private String content;             // 본문

    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt;    // 생성일시
    @CreatedBy @Column(nullable = false) private String createdBy;           // 생성자
    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt;   // 수정일시
    @LastModifiedBy @Column(nullable = false) private String modifiedBy;          // 수정자

    protected ArticleComment() {}       // 빈 생성자

    protected ArticleComment(Article article, String content) {     // 굳이 외부에서 부를 일 없고 내부에서만 쓸 것이기 때문에 private
        this.article = article;
        this.content = content;
    }

    public static ArticleComment of(Article article, String content){
        return new ArticleComment(article, content);
    }

    @Override
    public int hashCode() {     // id를 넣었을 때의 메모리 주소 얻기 위한 메소드     // 누구(id)의 해시코드? -> 메모리 주소를 뽑아 오는 것.
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {      // 완벽하게 같은 객체인지 확인하는 메소드
        if(this == obj) return true;                        // 값 비교
        if(!(obj instanceof ArticleComment articleComment)) return false; // 객체 비교
        return id != null && id.equals(articleComment.id);         // 완벽히 같으면 true
    }
}