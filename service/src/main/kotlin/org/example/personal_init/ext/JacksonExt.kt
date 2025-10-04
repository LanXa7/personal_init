package org.example.personal_init.ext

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

val objectMapper: ObjectMapper =
    ObjectMapper().registerModule(KotlinModule.Builder().build())
        .registerModule(JavaTimeModule().apply {
            addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(dateTimeFormatter))
            addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(dateTimeFormatter))
        })
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)


inline fun <reified T> JsonNode.getList(): List<T> =
    objectMapper.convertValue(this, object : TypeReference<List<T>>() {})

inline fun <reified T> JsonNode.getMutableList(): MutableList<T> =
    objectMapper.convertValue(this, object : TypeReference<MutableList<T>>() {})

inline fun <reified T> String.fromJson(): T =
    objectMapper.readValue(this, object : TypeReference<T>() {})

fun Any.toJsonString(): String =
    objectMapper.writeValueAsString(this)

fun <K, V> Map<K, V>.toJsonString(): String =
    objectMapper.writeValueAsString(this)

fun String.toJsonNode(): JsonNode =
    objectMapper.readTree(this)

inline fun <reified K, reified V> String.toMap(): Map<K, V> =
    objectMapper.readValue(this, object : TypeReference<Map<K, V>>() {})

inline fun <reified T> String.toList(): List<T> =
    objectMapper.readValue(this, object : TypeReference<List<T>>() {})

inline fun <reified K : Any, reified V : Any> String.toMapFromArray(
    fieldName: String,
    keyField: String,
    valueField: String
): Map<K, V> {
    val node: JsonNode = objectMapper.readTree(this)
    return node[fieldName].associate { item ->
        val key = objectMapper.convertValue(item[keyField], K::class.java)
        val value = objectMapper.convertValue(item[valueField], V::class.java)
        key to value
    }
}