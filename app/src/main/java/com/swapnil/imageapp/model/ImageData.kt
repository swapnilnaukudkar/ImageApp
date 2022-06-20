package com.swapnil.imageapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "images")
data class ImageData(

	@PrimaryKey
	@field:SerializedName("date")
	var date: String,

	@field:SerializedName("copyright")
	val copyright: String?,

	val isFavorite: Boolean,

	@field:SerializedName("media_type")
	val mediaType: String?,

	@field:SerializedName("hdurl")
	val hdurl: String?,

	@field:SerializedName("service_version")
	val serviceVersion: String? ,

	@field:SerializedName("explanation")
	val explanation: String? ,

	@field:SerializedName("title")
	val title: String?,

	@field:SerializedName("url")
	val url: String?
)
