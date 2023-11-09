package ezenweb.model.dto;

import ezenweb.model.entity.BoardEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor@NoArgsConstructor
@Getter@Setter@ToString@Builder
public class BoardDto {
    private int bno;
    private String btitle;
    private String bcontent;
    private int bview;
    private String bfile;
    private int mno;
    // + entity에서의 날짜타입 toLocalDate
    // dto에서의 날짜타입x 문자타입
    private String cdate;
    private String udate;
    // + 작성자 아이디
    private String memail;
    // + 첨부파일 [ spring 지원하는 첨부파일 라이브러리 ]
    private MultipartFile file;

    // dto -> entity
    // 1. entity 저장할때
    public BoardEntity saveToEntity() {
        return BoardEntity.builder()
                .btitle( this.btitle )
                .bcontent( this.bcontent )
                .bfile( this.bfile )
                .build();
    }
}
