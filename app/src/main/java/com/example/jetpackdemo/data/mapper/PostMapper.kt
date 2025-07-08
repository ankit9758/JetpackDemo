package com.example.jetpackdemo.data.mapper

import com.example.jetpackdemo.data.model.PostDto
import com.example.jetpackdemo.domain.model.Post

fun PostDto.toDomain(): Post {
    return Post(
        id = id,
        title = title,
        body = body,userId=userId, views = views
    )
}
