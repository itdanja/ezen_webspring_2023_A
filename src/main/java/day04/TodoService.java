package day04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoDao todoDao;

    // 2. 저장
    public boolean doPost(  TodoDto todoDto ){
        System.out.println("TodoService.doPost");
        System.out.println("todoDto = " + todoDto);
        return todoDao.doPost( todoDto );
    }
    // 3. 출력
    public List< TodoDto > doGet(){
        System.out.println("TodoService.doGet");
        return todoDao.doGet();
    }
    // 4. 수정
    public boolean doPut( TodoDto todoDto ){
        System.out.println("TodoService.doPut");
        System.out.println("todoDto = " + todoDto);
        return todoDao.doPut( todoDto );
    }
    // 5. 삭제
    public boolean doDelete( int tno ){
        System.out.println("TodoService.doDelete");
        System.out.println("tno = " + tno);
        return todoDao.doDelete( tno );
    }
}
