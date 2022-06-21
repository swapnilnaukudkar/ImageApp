package com.swapnil.imageapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 *  [ImageData]: Data class for Nasa Image Data.
 *  			Entity class for room db to store Image data locally
 *
 */
@Entity(tableName = "images")
data class ImageData(

	@PrimaryKey
	@field:SerializedName("date")
	var date: String,

	@field:SerializedName("copyright")
	val copyright: String?,

	// add new field to get isFavorite
	var isFavorite: Boolean,

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
