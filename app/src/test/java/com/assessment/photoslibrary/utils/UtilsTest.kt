package com.assessment.photoslibrary.utils

import com.assessment.photoslibrary.model.response.PhotoModel
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UtilsTest {
    @Test
    fun `test getImageUrl`() {
        val photo = mock(PhotoModel::class.java)
        val utils = spy(Utils)

        `when`(photo.farm).thenReturn("66")
        `when`(photo.server).thenReturn("65535")
        `when`(photo.id).thenReturn("48837804368")
        `when`(photo.secret).thenReturn("709b44acf8")

        val imageUrl = utils.getImageUrl(photo)

        val expectedUrl = "https://farm66.staticflickr.com/65535/48837804368_709b44acf8.jpg"
        assertEquals(expectedUrl, imageUrl)
    }
}