package ezenweb.model.repository;

import ezenweb.model.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardEntityRepository
    //extends JpaRepository< 조작할엔티티 , 조작할엔티티의PK필드타입 > {
    extends JpaRepository< BoardEntity, Integer > {
}
