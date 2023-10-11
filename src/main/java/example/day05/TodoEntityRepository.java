package example.day05;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoEntityRepository extends JpaRepository< TodoEntity , Integer > {
}
