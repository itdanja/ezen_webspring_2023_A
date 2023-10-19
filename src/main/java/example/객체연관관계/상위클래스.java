package example.객체연관관계;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Setter @Getter @Builder @ToString
public class 상위클래스 {
    private String data ;
    @Builder.Default // 빌더패턴 사용시 기본값[new ArrayList<>();]으로 사용
    public List<하위클래스> 참조하위객체들 = new ArrayList<>();
}
