package day04;

import lombok.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter@ToString
@Builder
public class TodoDto {
    private int tno;
    private String tcontent;
    private boolean tstate;
}
