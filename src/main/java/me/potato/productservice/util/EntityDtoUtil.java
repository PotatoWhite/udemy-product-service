package me.potato.productservice.util;

import me.potato.productservice.dto.ProductDto;
import me.potato.productservice.entity.Product;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {
    public static ProductDto toDto(Product entity) {
        var dto = new ProductDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public static Product toEntity(ProductDto dto) {
        var entity = new Product();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
