package ezenweb.model.dto;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString @Builder
public class ProductCategoryDto {
    private int pcno;       // 카테고리번호 [ PK ]
    private String pcname;  // 카테고리명
}
