package ezenweb.model.repository;

import ezenweb.model.entity.ProductEntity;
import ezenweb.model.entity.ProductImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductEntityRepository   extends JpaRepository<ProductEntity, String> {


    @Query( value = "select pname , pstock from product", nativeQuery = true)
    List< Map<Object , Object> > findByBarChart();

    @Query( value = "select pc.pcname , count(*) as count from product p inner join productcategory pc on p.pcno = pc.pcno group by pc.pcname;"
            , nativeQuery = true)
    List< Map<Object , Object> > findByPieChart();

}
