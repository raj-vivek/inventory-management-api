package com.ladyshopee.api.controller;

import com.ladyshopee.api.model.ColorAndSizes;
import com.ladyshopee.api.model.Size;
import com.ladyshopee.api.payload.dto.ProductDTO;
import com.ladyshopee.api.payload.response.MessageResponse;
import com.ladyshopee.api.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        List<ProductDTO> products = productService.getProducts(page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ProductDTO> getProductByCode(@PathVariable String code) {
        ProductDTO productDTO = productService.getProductByCode(code);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO){
        ProductDTO savedProductDTO = productService.createProduct(productDTO);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.OK);
    }

//    @GetMapping("/test")
//    public ResponseEntity<?> test(){
//        try{
//            List<ColorAndSizes> colorAndSizesList = new ArrayList<>();
//
//            Map<Size, Integer> sizes = new EnumMap<>(Size.class);
//            sizes.put(Size.S, 2);
//            sizes.put(Size.M, 3);
//            sizes.put(Size.L, 2);
//            sizes.put(Size.XL, 4);
//            sizes.put(Size.XXL, 2);
//            sizes.put(Size.XXXL, 5);
//            sizes.put(Size.XXXXL, 1);
//            ColorAndSizes colorAndSizes = ColorAndSizes.builder().color("Red").sizes(sizes).build();
//            colorAndSizesList.add(colorAndSizes);
//
//            ProductDTO productDTO = ProductDTO.builder()
//                    .name("Product 1")
//                    .buyPrice(500)
//                    .mrp(700)
//                    .colorAndSizes(colorAndSizesList)
//                    .build();
//
//            ProductDTO savedProductDTO = productService.createProduct(productDTO);
//            return new ResponseEntity<>(savedProductDTO, HttpStatus.OK);
//        } catch (Exception e) {
//            System.out.println(e);
//            return new ResponseEntity<>(new MessageResponse(HttpStatus.BAD_REQUEST.value() ,e.getMessage()), HttpStatus.BAD_REQUEST);
//        }
//    }
}
