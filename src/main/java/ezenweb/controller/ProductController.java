package ezenweb.controller;

import ezenweb.model.dto.ProductCategoryDto;
import ezenweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @DeleteMapping("/catoegory")
    public boolean deleteCategory( @RequestParam int pcno ){
        return productService.deleteCategory( pcno );
    }
}
