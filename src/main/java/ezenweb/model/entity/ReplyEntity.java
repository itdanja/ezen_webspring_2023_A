package ezenweb.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "reply")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor @Builder
public class ReplyEntity extends BaseTime {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rno;            // 댓글 번호
    @Column
    private String rcontent;       // 댓글 내용
    @Column
    private int rindex;         // 상위 댓글 번호

    // 게시물fk
    @ManyToOne@JoinColumn(name = "bno")@ToString.Exclude
    private BoardEntity boardEntity;
    // 작성자fk
    @ManyToOne@JoinColumn(name="mno")@ToString.Exclude
    private MemberEntity memberEntity;

}