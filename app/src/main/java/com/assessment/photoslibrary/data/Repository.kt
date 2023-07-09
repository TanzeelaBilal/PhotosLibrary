package com.assessment.photoslibrary.data

import android.graphics.Bitmap
import com.assessment.photoslibrary.data.remote.RemoteDataSource
import com.assessment.photoslibrary.model.BaseApiResponse
import com.assessment.photoslibrary.model.DogResponse
import com.assessment.photoslibrary.model.response.PhotosListResponse
import com.assessment.photoslibrary.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject


@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {

    suspend fun getPictureList(): Flow<NetworkResult<PhotosListResponse>> {
        return flow<NetworkResult<PhotosListResponse>> {
            emit(safeApiCall { remoteDataSource.getPictures() })
        }.flowOn(Dispatchers.IO)
    }

    fun saveImage(image: Bitmap, storageDir: File, imageFileName: String): Flow<Boolean> {

        val successDirCreated = if (!storageDir.exists()) {
            storageDir.mkdir()
        } else {
            true
        }

        if (successDirCreated) {
            val imageFile = File(storageDir, imageFileName)
            return try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
                flow {
                    emit(true)
                }.flowOn(Dispatchers.IO)
            } catch (e: Exception) {
                e.printStackTrace()
                flow {
                    emit(false)
                }.flowOn(Dispatchers.IO)
            }
        } else {
            return flow { emit(false) }.flowOn(Dispatchers.IO)
        }
    }

}

