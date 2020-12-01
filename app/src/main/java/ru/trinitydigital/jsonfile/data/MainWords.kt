package ru.trinitydigital.jsonfile.data

import com.google.gson.annotations.SerializedName

data class MainWords(
	@SerializedName("words") val words: List<Words>
)