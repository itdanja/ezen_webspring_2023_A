package ezenweb.model.dto;

import ezenweb.model.entity.ProductCategoryEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductCategoryDto {
    private int pcno;
    private String pcname;  // 카테고리명

    public ProductCategoryEntity toEntity(){
        return ProductCategoryEntity.builder().pcname( this.pcname).build();
    }
}
