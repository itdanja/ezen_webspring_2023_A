package example.day06;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// Repository사용처는 Service
@Repository // 스프링 컨테이너에 등록 [ 왜?? 다른곳에 객체를 써야 되니까  ]
public interface NoteEntityRepository extends JpaRepository< NoteEntity , Integer > {
}
