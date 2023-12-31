package ezenweb.controller;

import ezenweb.model.dto.ProductCategoryDto;
import ezenweb.model.dto.ProductDto;
import ezenweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    // 0.다른 서비스
    @Autowired ProductService productService;

    // ================================ 카테고리 ============================= //
    // 1. 카테고리 등록
    @PostMapping("/category")
    public boolean addCategory(@RequestBody ProductCategoryDto productCategoryDto){
        return productService.addCategory( productCategoryDto );
    }
    // 2. 카테고리 출력
    @GetMapping("/category")
    public List<ProductCategoryDto> printCategory(){
        return productService.printCategory();
    }
    // 3. 카테고리 수정
    @PutMapping("/category")
    public boolean updateCategory(@RequestBody ProductCategoryDto productCategoryDto ){
        return productService.updateCategory( productCategoryDto );
    }
    // 4. 카테고리 삭제
    @DeleteMapping("/category")
    public boolean deleteCategory( @RequestParam int pcno ){
        return productService.deleteCategory( pcno );
    }

    // ================================ 제품등록 ============================= //
    @PostMapping("") // 요청 : ? / 응답 : ?
    public boolean onProductAdd( ProductDto productDto ){
        return productService.onProductAdd( productDto );
    }
    @GetMapping("") // 요청 : ? / 응답 : ?
    public List<ProductDto> onProductAll( ){
        return productService.onProductAll();
    }

    @PutMapping("") // 요청 : ? / 응답 : ?
    public boolean onProductUpdate( @RequestBody ProductDto productDto ){
        return productService.onProductUpdate( productDto );
    }

    @DeleteMapping("") // 요청 : ? / 응답 : ?
    public boolean onProductDelete( @RequestParam String pno ){
        return productService.onProductDelete( pno );
    }

    // ================================ 차트 데이터 ============================= //
    @GetMapping("/barchart")
    public List<Map<Object,Object> > getBarChart( ){ return productService.getBarChart();}
    @GetMapping("/piechart")
    public List<Map<Object,Object> > getPieChart( ) {return productService.getPieChart(); }

}





































