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
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

// Entity: 데이터베이스와 연동될 애들
@Getter
@ToString
// 부하를 많이 주는 것: select. 그중에 큰 것이 order by. -> 부하 덜 가게 하는 기능이 indexing
// 내용이 긴데 거기서 select한다면 부하 매우 큼  // 다른 프로그램? 안쓰고 개발단계에서 해결할 수 있는 방법임
@Table(indexes={                            // 여러 필드에 대해 주고 싶으면 중괄호 해서 안에 써주기
        @Index(columnList = "title"),       // 결과가 여러개일 것이라서 columnList?
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)      // 사용할 수 있게끔 Listener로 등록.
@Entity                     // 프라이머리키 설정 안하면 오류남
public class Article {      // 문제(ex.??)가 많기 떄문에 원래는 setter 이런거 함부로 쓰면 안됨 -> @Data 대신 필요한 것만 골라서 선언
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // DBMS마다 다름. MySQL의 경우 strategy를 IDENTITY로 해줌
    private Long id;
    @Setter @Column(nullable=false) private String title;                   // 제목   // 얘네 셋은 변경할 수 있도록 Setter 설정,
    @Setter @Column(nullable=false, length=10000) private String content;   // 본문   // @Column(nullable = false): Null이 안되도록 함  // length=10000 1만자까지 가능하도록 설정
    @Setter private String hashtag;                                         // 해시태그

    @CreatedDate @Column(nullable=false) private LocalDateTime createdAt;           // 생성일시 // @CreatedDate: 생성일시 자동으로 넣어줌
    @CreatedBy @Column(nullable=false, length=100) private String createdBy;        // 생성자   // @CreatedBy:
    @LastModifiedDate @Column(nullable=false) private LocalDateTime modifiedAt;     // 수정일시 // @LastModifiedDate: 작성된 날짜, 수정된 날짜에 모두 자동으로 넣어줌
    @LastModifiedBy @Column(nullable=false, length=100) private String modifiedBy;  // 수정자   // @LastModifiedBy:

    @ToString.Exclude       // join하는 순간 toString에러가 있음. 그러니 ToString을 제외하고 값 뽑아 달라
    @OrderBy("id")          // id를 기준으로 orderby 해줄 것임
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)                 // mappedBy = "article": article과 연결   // cascade = CascadeType.ALL: crud에 대해서 에러 안나고 자유롭게 만들어 줄 수 있음??
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();  // 중복되는 내용이 없으니 Set??   // hashSet인데 Link로 되어있는 것??

    protected Article(){}

    private Article(String title, String content, String hashtag) {     // 굳이 외부에서 부를 일 없고 내부에서만 쓸 것이기 때문에 private
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    private static Article of(String title, String content, String hashtag) {    // 외부에서 쓸 때는 이 함수를 통해 Article을 호출해서 사용
        return new Article(title, content, hashtag);
    }

    @Override
    public int hashCode() {     // id를 넣었을 때의 메모리 주소 얻기 위한 메소드     // 누구(id)의 해시코드? -> 메모리 주소를 뽑아 오는 것.
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {      // 완벽하게 같은 객체인지 확인하는 메소드
        if(this == obj) return true;                        // 값 비교
        if(!(obj instanceof Article article)) return false; // 객체 비교
        return id != null && id.equals(article.id);         // 완벽히 같으면 true
    }
}