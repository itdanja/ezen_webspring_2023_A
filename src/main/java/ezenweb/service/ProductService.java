package ezenweb.service;

import ezenweb.model.dto.ProductCategoryDto;
import ezenweb.model.dto.ProductDto;
import ezenweb.model.entity.ProductCategoryEntity;
import ezenweb.model.entity.ProductEntity;
import ezenweb.model.entity.ProductImgEntity;
import ezenweb.model.repository.ProductCategoryEntityRepository;
import ezenweb.model.repository.ProductEntityRepository;
import ezenweb.model.repository.ProductImgEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired private ProductEntityRepository productEntityRepository;
    @Autowired private ProductCategoryEntityRepository productCategoryEntityRepository;
    @Autowired private ProductImgEntityRepository productImgEntityRepository;
    @Autowired private FileService fileService;

    @Transactional public boolean addCategory( ProductCategoryDto productCategoryDto  ){
        ProductCategoryEntity productCategoryEntity
                = productCategoryEntityRepository.save( productCategoryDto.toEntity() );
        return productCategoryEntity.getPcno() >=1 ? true : false ;
    }

    @Transactional public List<ProductCategoryDto> printCategory(){
        return productCategoryEntityRepository.findAll().stream().map( (e)->{return e.toDto(); } ).collect(Collectors.toList());
    }

    @Transactional public boolean updateCategory(ProductCategoryDto productCategoryDto){
        Optional<ProductCategoryEntity> productCategoryEntityOptional
                = productCategoryEntityRepository.findById( productCategoryDto.getPcno() );
        if( productCategoryEntityOptional.isPresent() ){
            productCategoryEntityOptional.get().setPcname( productCategoryDto.getPcname() );
            return true;
        }
        return false; }
    @Transactional public boolean deleteCategory(int pcno){
        Optional<ProductCategoryEntity> productCategoryEntityOptional
                = productCategoryEntityRepository.findById( pcno );
        if( productCategoryEntityOptional.isPresent() ){
            productCategoryEntityRepository.deleteById(pcno);
            return true;
        }
        return false;
    }


    // 1. 제품[ 제품 이미지 ] 등록
    @Transactional public boolean addProduct(ProductDto productDto){
        System.out.println( productDto );

        // 1. 카테고리 불러오기
        ProductCategoryEntity productCategoryEntity = productCategoryEntityRepository.findById( productDto.getPcno() ).get();
        // 2. 제품엔티티 생성
        ProductEntity productEntity = ProductEntity.builder()
                .pno(UUID.randomUUID().toString() )
                .pname( productDto.getPname() ).pcomment( productDto.getPcomment() )
                .pstock( productDto.getPstock() ).pprice( productDto.getPprice() )
                .productCategoryEntity( productCategoryEntity )
                .productImgEntityList( new ArrayList<>() )
                .build();
        //3. 이미지엔티티 생성
        productDto.getFileList().stream().map( file ->{ return fileService.fileUpload( file ); } ).collect(Collectors.toList()).forEach(
                (uuidFile)->{
                    productEntity.getProductImgEntityList().add(
                            ProductImgEntity.builder()
                                    .uuidfilename( uuidFile )
                                    .realfilename( uuidFile.split("_")[1] )
                                    .productEntity( productEntity )
                                    .build()
                    );
                }
        );
        // 4. 제품 등록
        productEntityRepository.save( productEntity );
        return true;
    }

    @Transactional public List<ProductDto> printProduct( ){
        System.out.println( productEntityRepository.findAll().get(0));
        System.out.println( productEntityRepository.findAll().get(0).getProductCategoryEntity());
        System.out.println( productEntityRepository.findAll().get(0).getProductImgEntityList() );

        return null;
    }


}













