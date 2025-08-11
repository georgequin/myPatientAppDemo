package com.example.mypatients.data.remote

data class PostPayload(
    val title: String,
    val body: String,
    val userId: Int = 1
)

data class PostResponse(
    val id: Int
)