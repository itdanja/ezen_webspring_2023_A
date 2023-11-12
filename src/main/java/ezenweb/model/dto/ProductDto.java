package ezenweb.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString @Builder
public class ProductDto {
    private String pno;     //  제품번호 [ PK ]
    private String pname;   //  제품명
    private String pcomment; // 제품설명
    private int pprice;     //  제품가격
    private byte pstate;    //  제품상태 [ 0:판매중 , 1:판매중지 , 2:재고없음 3.폐기 등등 ]
    private int pstock;     //  제품재고

    //+등록시
    private int pcno;
    private List<MultipartFile> fileList;

    //+출력시
    private List<ProductImgDto> imgList;
    private ProductCategoryDto categoryDto;

}
