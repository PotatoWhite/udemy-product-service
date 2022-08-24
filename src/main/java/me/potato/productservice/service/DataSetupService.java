package me.potato.productservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.potato.productservice.dto.ProductDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class DataSetupService implements CommandLineRunner {

    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        var dto1 = new ProductDto("4k-tv", 1000);
        var dto2 = new ProductDto("slr-camera", 750);
        var dto3 = new ProductDto("iphone-x", 800);
        var dto4 = new ProductDto("headphone", 100);

        Flux.just(dto1, dto2, dto3, dto4)
                .flatMap(p -> productService.insertProduct(Mono.just(p)))
                .subscribe(p -> log.info("Inserted product: {}", p));
    }
}
