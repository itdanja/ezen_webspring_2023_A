package day04;

import org.springframework.stereotype.Component;

import java.util.List;
@Component // 해당 클래스를 스프링 컨테이너에 빈 등록
public class TodoDao {
    // 1. [C]
    public boolean doPost( TodoDto todoDto ){
        System.out.println("TodoDao.doPost");
        System.out.println("todoDto = " + todoDto);
        return false;
    }
    // 2. [R]
    public List<TodoDto> doGet(){
        System.out.println("TodoDao.doGet");
        return null;
    }
    // 3. [U]
    public boolean doPut( TodoDto todoDto ){
        System.out.println("TodoDao.doPut");
        System.out.println("todoDto = " + todoDto);
        return false;
    }
    // 4. [D]
    public boolean doDelete( int tno ){
        System.out.println("TodoDao.doDelete");
        System.out.println("tno = " + tno);
        return false;
    }
}
