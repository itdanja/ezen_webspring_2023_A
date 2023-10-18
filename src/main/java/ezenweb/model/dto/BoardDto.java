package ezenweb.model.dto;

import ezenweb.model.entity.BoardEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.LastModifiedDate;

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
    // +
    private LocalDateTime cdate;
    private LocalDateTime udate;

    // dto -> entity
    // 1. entity 저장할때
    public BoardEntity saveToEntity() {
        return BoardEntity.builder()
                .btitle( this.btitle )
                .bcontent( this.bcontent )
                .bfile( this.bfile )
                .mno( this.mno )
                .build();
    }
}