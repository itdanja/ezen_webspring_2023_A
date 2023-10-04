package day01.lombok;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor  // 빈생성자 자동생성
@AllArgsConstructor // 풀생성자 자동생성
@Getter@Setter      // getter,setter 메소드 자동생성
@ToString           // toString 메소드 자동생성
@Builder            // 빌더패턴
public class LombokDto {
    private int tno;
    private String title;
    private LocalDate dueDate;
    private boolean finished;
}
