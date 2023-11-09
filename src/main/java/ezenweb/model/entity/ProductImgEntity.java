package ezenweb.model.entity;

import javax.persistence.*;

@Entity@Table(name = "productimg")
public class ProductImgEntity { /*제품이미지파일*/
    @Id private String uuidFileName; // 이미지식별이름[PK]
    @Column private String realFileName; // 이미지실제이름
    // FK 만들기
    @JoinColumn(name = "pno") @ManyToOne
    private ProductEntity productEntity;
}

