package example.객체연관관계;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Setter @Getter @Builder @ToString
public class 하위클래스 {
    private String data ;
    @ToString.Exclude
    private 상위클래스 상위객체;
}
