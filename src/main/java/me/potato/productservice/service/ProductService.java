package me.potato.productservice.service;

import lombok.RequiredArgsConstructor;
import me.potato.productservice.dto.ProductDto;
import me.potato.productservice.repository.ProductRepository;
import me.potato.productservice.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public Flux<ProductDto> getAll() {
        return productRepository.findAll().map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getById(String id) {
        return productRepository.findById(id).map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> insertProduct(Mono<ProductDto> dto) {
        return dto
                .map(EntityDtoUtil::toEntity)
                .flatMap(productRepository::save)
                .map(EntityDtoUtil::toDto);
    }

//    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> dto) {
//        return productRepository.findById(id)
//                .flatMap(product -> dto
//                        .map(EntityDtoUtil::toEntity)
//                        .doOnNext(e -> e.setId(product.getId())))
//                .flatMap(productRepository::save)
//                .map(EntityDtoUtil::toDto);
//    }

    public Mono<ProductDto> changeProduct(String id, Mono<ProductDto> dto) {
        return productRepository.findById(id)
                .flatMap(product -> dto
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(e -> e.setId(product.getId())))
                .flatMap(productRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> updateProduct(String id, Map<String, Object> fieldsDto) {
        return productRepository.findById(id)
                .doOnNext(product ->
                        fieldsDto.forEach((key, value) -> {
                            if (key.equals("description")) {
                                product.setDescription((String) value);
                            } else if (key.equals("price")) {
                                product.setPrice((Integer) value);
                            }
                        })
                )
                .flatMap(productRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }
}
