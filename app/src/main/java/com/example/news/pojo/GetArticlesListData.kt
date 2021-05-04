package com.example.news.pojo

import com.google.gson.annotations.SerializedName

data class GetArticlesListData(

	@field:SerializedName("GetArticlesListData")
	val getArticlesListData: List<GetArticlesListDataItem?>? = null
)

data class LaunchesItem(

	@field:SerializedName("provider")
	val provider: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class GetArticlesListDataItem(

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
	val events: List<EventsItem?>? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class EventsItem(

	@field:SerializedName("provider")
	val provider: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
