package com.example.retrofit_u

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Albumservice {

    @GET("albums")
    suspend fun getAlbums(): Response<Album>

    @GET("albums")
    suspend fun getSortedAlbums(@Query("userId") userId: Int): Response<Album>

    @GET("albums/{id}")
    suspend fun getAlbum(@Path("id") albumId:Int): Response<AlbumItem>

    @POST("albums")
    suspend fun postAlbum(@Body album: AlbumItem): Response<AlbumItem>
}






