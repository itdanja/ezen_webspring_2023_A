package example.day05;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

// MVC 3티어 계층에서 사용되는 Service계층[ 비지니스로직 : 기능 처리 공간 ]
@Service // Spring MVC 중 해당 클래스를 Service 로 사용 // 스프링 빈 등록
public class TodoService {

    @Autowired
    private TodoEntityRepository todoEntityRepository;

    // 1. [C]
    public boolean doPost( TodoDto todoDto ){
        TodoEntity todoEntity = todoEntityRepository.save( todoDto.toEntity() );
        return todoEntity.getTno() >=1 ? true : false;
    }
    // 2. [R]
    public List<TodoDto> doGet(){
        List<TodoDto> list = todoEntityRepository.findAll(Sort.by(Sort.Direction.DESC, "tno"))
                .stream()
                .map(entity -> entity.toDto() )
                .collect(Collectors.toList());

        return  list;
    }
    // 3. [U]
    @Transactional
    public boolean doPut( TodoDto todoDto ){
        todoEntityRepository.findById( todoDto.getTno() ).get().setTstate( todoDto.isTstate() );
        return true;
    }
    // 4. [D]
    @Transactional
    public boolean doDelete( int tno ){
        todoEntityRepository.deleteById( tno );
        return true;
    }
}
