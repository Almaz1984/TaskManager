package com.example.taskmanager

import android.app.ActivityManager
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp
import com.google.gson.JsonParseException
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement

import com.google.gson.JsonDeserializer


data class Task (
    @SerializedName("id") val id:String,
    @SerializedName("date_start") val dataStart:Long,
    @SerializedName("date_finish") val dataFinish:Long,
    @SerializedName("name") val taskName:String,
    @SerializedName("description") val taskDescription:String
    ) {
}

//val deserializeTimestamp: JsonDeserializer<Timestamp?> =
//    JsonDeserializer<Timestamp?> { json, typeOfT, context -> if (json == null) null else Timestamp(json.asLong*1000) }