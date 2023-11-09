package ezenweb.model.dto;

import ezenweb.model.entity.ProductEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductImgDto {
    private String uuidFileName; // 이미지식별이름[PK]
    private String realFileName; // 이미지실제이름
}
