package ezenweb.model.entity;

import ezenweb.model.dto.ProductCategoryDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity@Table( name="productcategory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductCategoryEntity extends BaseTime { /*제품 카테고리*/
    @Id@GeneratedValue( strategy = GenerationType.IDENTITY) private int pcno;       // 카테고리번호 [ PK ]
    @Column private String pcname;  // 카테고리명

    // 양방향 만들기
    @OneToMany( fetch = FetchType.LAZY, mappedBy = "productCategoryEntity" , cascade =CascadeType.ALL   )
    @ToString.Exclude List<ProductEntity> productEntityList = new ArrayList<>();

    public ProductCategoryDto toDto(){
        return ProductCategoryDto.builder()
                .pcname(this.pcname)
                .pcno(this.pcno)
                .build();
    }

    /*
        fetch : 양방향일때 참조를 불러오는 로딩 옵션
            fetch = FetchType.LAZY            : 참조를 사용할때 로딩 [ 지연 로딩  ]  자바에서 .get~~~ 할때 객체 참조해서 불러오고
            fetch = FetchType.EAGER [ 기본값 ] : 참조값을 즉시 로딩   [ 즉시 로딩 ]  db에서 select 할때 객체 참조해서 불러오고

        cascade : 영속성 제약조건 ( 엔티티 객체 기준 ) : 서로 연관된 객체들 끼리(부-자)의 영향을 끼치게 할껀지
            [REMOVE+PERSIST]   cascade = CascadeType.ALL       :  REMOVE 와 PERSIST 둘다 적용
            [모두제거]          cascade =CascadeType.REMOVE     : 부모 가 삭제될때 자식도 같이 삭제 [ 부모와 자식 를 모두 제거 ]
            [영속성]            cascade =CascadeType.PERSIST    : 부모 호출 할때 자식도 하나로 인식 [ 부모와 자식 를 한번에 영속성 활성화 ]
                - 부모를 저장하면 자식도 같이 저장
            [병합]            cascade =CascadeType.MERGE      : 부모 가 수정될떄 자식도 조회후 업데이트
            [새로고침]         cascade =CascadeType.REFRESH    : 부모 가 업데이트 되면 자식 값 새로고침
            [영속성제거]       cascade =CascadeType.DETACH     : 영속성 제거
     */
}
