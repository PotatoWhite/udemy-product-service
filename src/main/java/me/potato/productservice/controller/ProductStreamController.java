package me.potato.productservice.controller;

import lombok.RequiredArgsConstructor;
import me.potato.productservice.dto.ProductDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@RestController
@RequestMapping("product")
public class ProductStreamController {
    private final Flux<ProductDto> flux;

    @GetMapping(value = "stream/{maxPrice}", produces = "text/event-stream")
    public Flux<ProductDto> getProductUpdates(@PathVariable int maxPrice) {
        return flux
                .subscribeOn(Schedulers.boundedElastic())
                .log()
                .filter(product -> product.getPrice() <= maxPrice);
    }
}
