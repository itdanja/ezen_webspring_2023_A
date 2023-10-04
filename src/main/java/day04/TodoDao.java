package day04;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@Component
public class TodoDao {

    // 2. 저장
    public boolean doPost(  TodoDto todoDto ){
        System.out.println("TodoDao.doPost");
        System.out.println("todoDto = " + todoDto);
        return false;
    }
    // 3. 출력
    public List< TodoDto > doGet(){
        System.out.println("TodoDao.doGet");
        return null;
    }
    // 4. 수정
    public boolean doPut( TodoDto todoDto ){
        System.out.println("TodoDao.doPut");
        System.out.println("todoDto = " + todoDto);
        return false;
    }
    // 5. 삭제
    public boolean doDelete( int tno ){
        System.out.println("TodoDao.doDelete");
        System.out.println("tno = " + tno);
        return false;
    }

}
/*
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public TodoDao(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/springweb" , "root","1234");
        }catch (Exception e ){ System.out.println("DB error"+e);}
    }

 */