package com.assessment.photoslibrary.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class PhotosListResponse(
    @SerializedName("photos")
    val photos: PhotosMetaData
)

data class PhotosMetaData(
    @SerializedName("page")
    val page: String,
    @SerializedName("pages")
    val pages: String,
    @SerializedName("perpage")
    val perpage: String,
    @SerializedName("total")
    val total: String,
    @SerializedName("photo")
    val photo: List<PhotoModel>
)

@Parcelize
data class PhotoModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("secret")
    val secret: String,
    @SerializedName("server")
    val server: String,
    @SerializedName("farm")
    val farm: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("ispublic")
    val ispublic: Int,
    @SerializedName("isfriend")
    val isfriend: Int,
    @SerializedName("isfamily")
    val isfamily: Int

) : Parcelable
