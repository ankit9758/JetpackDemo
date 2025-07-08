package com.example.jetpackdemo.data.mapper

import com.example.jetpackdemo.data.model.ProductDto
import com.example.jetpackdemo.domain.model.Product

fun ProductDto.toDomain() = Product(
    id    = id,
    name  = title,
    price = price,
    image = image
)