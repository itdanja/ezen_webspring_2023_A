package ezenweb.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString @Builder
public class TodoDto {
    private int tno;
    private String tcontent;
    private boolean tstate;
}
