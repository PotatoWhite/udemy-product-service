package me.potato.productservice.controller;


import lombok.RequiredArgsConstructor;
import me.potato.productservice.dto.ProductDto;
import me.potato.productservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public Flux<ProductDto> getAll() {
        return productService.getAll();
    }

    @GetMapping("/price-range")
    public Flux<ProductDto> getProductsByPriceBetween(@RequestParam int min, @RequestParam int max) {
        return productService.getProductsByPriceBetween(min, max);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> getById(@PathVariable String id) {
        return productService.getById(id)
                .map(ResponseEntity.ok()::body)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ProductDto> insertProduct(@RequestBody Mono<ProductDto> dto) {
        return productService.insertProduct(dto);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> changeProduct(@PathVariable String id, @RequestBody Mono<ProductDto> dto) {
        return productService.changeProduct(id, dto)
                .map(ResponseEntity.ok()::body)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable String id, @RequestBody Map<String, Object> fieldsDto) {
        return productService.updateProduct(id, fieldsDto)
                .map(ResponseEntity.ok()::body)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }

}
