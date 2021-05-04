package com.example.news.pojo

import com.google.gson.annotations.SerializedName

data class GetArticleDetailData(

	@field:SerializedName("GetArticleDetailData")
	val getArticleDetailData: List<GetArticleDetailDataItem?>? = null
)

data class GetArticleDetailDataItem(

	@field:SerializedName("summary")
	val summary: String? = null,

	@field:SerializedName("featured")
	val featured: Boolean? = null,

	@field:SerializedName("publishedAt")
	val publishedAt: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("newsSite")
	val newsSite: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("launches")
	val launches: List<Any?>? = null,

	@field:SerializedName("events")
	val events: List<EventItem?>? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class EventItem(

	@field:SerializedName("provider")
	val provider: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
