package ezenweb.model.repository;

import ezenweb.model.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository// 리포지토리( @Component포함 )
public interface MemberEntityRepository extends JpaRepository<MemberEntity , Integer >  {

}
