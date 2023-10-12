package example.day01.webMvc;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@ToString@Builder
public class WebDto { // TODO 클래스
    private int tno; // TODO 번호
    private String title; // TODO 내용
    private LocalDate dueDate; // TODO 작성일
    private boolean finished; // TODO 실행여부
}
