package example.day05;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // JPA(ORM매핑) MYSQL 테이블과 매핑
@Builder // 빌더패턴
@Getter @Setter  // getter setter 메소드
@NoArgsConstructor@AllArgsConstructor // 빈생성자/풀생성자
public class TodoEntity {
    @Id // PK로 선정할 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY )// auto_increment
    private int tno;
    private String tcontent;
    private boolean tstate;
}

/*
    CREATE TABLE  todo(
        tno int auto_increment ,
        tcontent varchar(100) ,
        tstate boolean ,
        primary key( tno )
    );
 */
