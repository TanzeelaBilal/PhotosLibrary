package com.assessment.photoslibrary.data.remote

import com.assessment.photoslibrary.model.response.Photo
import com.assessment.photoslibrary.model.response.PhotosListResponse
import com.assessment.photoslibrary.model.response.Photos
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class RemoteDataSourceTest {
    @Mock
    lateinit var apiService: ApiService

    @InjectMocks
    lateinit var remoteDataSource: RemoteDataSource

    @Test
    fun `test getPhotos success`() {
        val photosListResponse =
            PhotosListResponse(Photos(1, 10, 100, 100, listOf<Photo>()))
        val response = Response.success(photosListResponse)
        Mockito.`when`(runBlocking { apiService.getRecentPhotos(page = 1) }).thenReturn(response)

        val result = runBlocking { remoteDataSource.getPictures(page = 1) }

        assertNotNull(result)
        assertEquals(response, result)
    }

    @Test
    fun `test getUser failure`() {
        val response = Response.error<PhotosListResponse>(
            404,
            "".toResponseBody("application/json".toMediaTypeOrNull())
        )

        Mockito.`when`(runBlocking { apiService.getRecentPhotos(page = 1) }).thenReturn(response)

        val result = runBlocking { remoteDataSource.getPictures(page = 1) }

        assertEquals(response, result)
    }
}