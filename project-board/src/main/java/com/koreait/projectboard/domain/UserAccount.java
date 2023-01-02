package com.koreait.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.util.Objects;


@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "userid"),
        @Index(columnList = "email", unique=true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class UserAccount extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false,length = 50) private String userId;
    @Setter @Column(nullable = false) private String userPassword;
    @Setter @Column(length = 100) private String email;
    @Setter @Column(length = 100) private String nickname;
    @Setter private String memo;

    protected UserAccount(){}


    public UserAccount(String userId, String userPassword, String email, String nickname, String memo) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo){
        return new UserAccount(userId,userPassword,email,nickname,memo);
    }
    @Override
    public int hashCode() {     // id를 넣었을 때의 메모리 주소 얻기 위한 메소드     // 누구(id)의 해시코드? -> 메모리 주소를 뽑아 오는 것.
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {      // 완벽하게 같은 객체인지 확인하는 메소드
        if(this == obj) return true;                        // 값 비교
        if(!(obj instanceof UserAccount userAccount)) return false; // 객체 비교
        return id != null && id.equals(userAccount.id);         // 완벽히 같으면 true
    }
}
