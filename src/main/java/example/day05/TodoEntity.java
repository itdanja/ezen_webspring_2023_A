package example.day05;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TodoEntity")
@Builder @Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int tno;

    @Column( length = 100 )
    private String tcontent;

    @Column( columnDefinition = "false")
    private boolean tstate;

    public TodoDto toDto(){
        return TodoDto.builder()
                .tno( this.tno )
                .tcontent(this.tcontent)
                .tstate( this.tstate )
                .build();
    }

}
