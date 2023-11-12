package ezenweb.service;

import ezenweb.model.dto.ProductCategoryDto;
import ezenweb.model.dto.ProductDto;
import ezenweb.model.dto.ProductImgDto;
import ezenweb.model.entity.ProductCategoryEntity;
import ezenweb.model.entity.ProductEntity;
import ezenweb.model.entity.ProductImgEntity;
import ezenweb.model.repository.ProductCategoryEntityRepository;
import ezenweb.model.repository.ProductEntityRepository;
import ezenweb.model.repository.ProductImgEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    // 0. 리포지토리
    @Autowired private ProductCategoryEntityRepository productCategoryEntityRepository;
    @Autowired private ProductEntityRepository productEntityRepository;
    @Autowired private ProductImgEntityRepository productImgEntityRepository;
    // 0.다른 서비스
    @Autowired private FileService fileService;
    // ================================ 카테고리 ============================= //
    // 1. 카테고리 등록
    @Transactional public boolean addCategory(ProductCategoryDto productCategoryDto){
        // 1. DTO --> 엔티티  // 2. 리포지토리 이용한 엔티티 세이브   // 3. 성공시 true 실패시 false
        return productCategoryEntityRepository.save(
                    ProductCategoryEntity.builder().pcname(productCategoryDto.getPcname()).build()
                ).getPcno() >=1 ? true : false;
    }
    // 2. 카테고리 출력
    @Transactional public List<ProductCategoryDto> printCategory(){
        // 1. 모든엔티티 호출 // 2. 모든엔티티리스트 -> dto리스트변환 // 3. dto리스트 반환
        return productCategoryEntityRepository.findAll().stream().map(
                    e ->{ return ProductCategoryDto.builder().pcno( e.getPcno() ).pcname( e.getPcname() ).build();  }
                ).collect(Collectors.toList());
    }
    // 3. 카테고리 수정
    @Transactional public boolean updateCategory( ProductCategoryDto productCategoryDto ){
        // 1. 수정할 엔티티 찾는다[pcno] // 2. 찾은 엔티티가 존재하면 수정o 아니면 수정x // 3.성공시true 실패시 false
        ProductCategoryEntity productCategoryEntity = toEntity( productCategoryDto.getPcno() );
        if( productCategoryEntity != null ){ productCategoryEntity.setPcname( productCategoryDto.getPcname() ); return true; }
        return false;
    }
    // 4. 카테고리 삭제
    @Transactional public boolean deleteCategory( int pcno ){
        // 1. 삭제할 엔티티 찾는다[pcno] // 2. 찾은 엔티티가 존재하면 삭제o 아니면 삭제x // 3. 성공시true 실패시 false
        ProductCategoryEntity productCategoryEntity = toEntity( pcno );
        if( productCategoryEntity != null ){ productCategoryEntityRepository.delete(productCategoryEntity); return  true; }
        return false;
    }
    // 5. 부가적인 엔티티검색용 함수
    @Transactional public ProductCategoryEntity toEntity( int pcno ){
        Optional< ProductCategoryEntity > productCategoryEntityOptional = productCategoryEntityRepository.findById( pcno );
        return productCategoryEntityOptional.isPresent() ? productCategoryEntityOptional.get() : null;
    }

    // ==============================================================================
    // 1. 제품[ 제품 이미지 ] 등록
    @Transactional public boolean addProduct(ProductDto productDto){
        System.out.println( productDto );

        // 1. 카테고리 불러오기
        ProductCategoryEntity productCategoryEntity = productCategoryEntityRepository.findById( productDto.getPcno() ).get();
        // 2. 제품엔티티 생성
        ProductEntity productEntity = ProductEntity.builder()
                .pno( productCategoryEntity.getPcno()+"-"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern( "yyyyMMddHHmmss" ) ) )
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
        List<ProductEntity> productEntities = productEntityRepository.findAll(Sort.by( Sort.Direction.DESC , "cdate") );
        return productEntities.stream().map( (p)->{
            return ProductDto.builder()
                    .pno( p.getPno() )
                    .pname( p.getPname() )
                    .pstock( p.getPstock() )
                    .pstate( p.getPstate() )
                    .pprice( p.getPprice() )
                    .pcomment( p.getPcomment() )
                    .categoryDto( ProductCategoryDto.builder().pcno(p.getProductCategoryEntity().getPcno()).pcname(p.getProductCategoryEntity().getPcname()).build() )
                    .imgList(
                            p.getProductImgEntityList().stream().map( (img)->{
                                return ProductImgDto.builder().uuidFileName(img.getUuidfilename()).realFileName(img.getRealfilename()).build();
                            }).collect(Collectors.toList())
                    )
                    .build();
        }).collect(Collectors.toList());
    }

    @Transactional public boolean updateProduct( ProductDto productDto ){
        ProductEntity productEntity = toEntity( productDto.getPno() );
        if( productEntity != null ){
            productEntity.setPname( productDto.getPname() );
            productEntity.setPprice( productDto.getPprice() );
            productEntity.setPstate( productDto.getPstate() );
            productEntity.setPstock( productDto.getPstock() );
            return true;
        }
        return false;
    }

    @Transactional public boolean deleteProduct( String pno ){
        // 1. 삭제할 엔티티 찾는다[pcno] // 2. 찾은 엔티티가 존재하면 삭제o 아니면 삭제x // 3. 성공시true 실패시 false
        ProductEntity productEntity = toEntity( pno );
        if( productEntity != null ){ productEntityRepository.delete(productEntity); return  true; }
        return false;
    }

    @Transactional public ProductEntity toEntity( String pno ){
        Optional< ProductEntity > productEntityOptional = productEntityRepository.findById( pno );
        return productEntityOptional.isPresent() ? productEntityOptional.get() : null;
    }

}

















