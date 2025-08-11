package com.example.mypatients.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("posts")
    suspend fun postPatient(@Body payload: PostPayload): Response<PostResponse>
}