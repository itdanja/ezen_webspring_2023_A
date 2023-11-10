package ezenweb.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity@Table(name = "productimg")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString @Builder
public class ProductImgEntity extends BaseTime { /*제품이미지파일*/
    @Id private String uuidFileName; // 이미지식별이름[PK]
    @Column private String realFileName; // 이미지실제이름
    // FK 만들기
    @JoinColumn(name = "pno") @ManyToOne
    private ProductEntity productEntity;
}

