package org.example.personal_init.ext

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import tools.jackson.core.type.TypeReference
import tools.jackson.databind.JacksonModule
import tools.jackson.databind.JsonNode
import tools.jackson.databind.ObjectMapper
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer
import tools.jackson.databind.json.JsonMapper
import java.time.format.DateTimeFormatter

private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

val objectMapper: ObjectMapper = JsonMapper.builder()
    .addModule(kotlinModule())
    .addModule(
        JavaTimeModule().apply {
            val serializer = LocalDateTimeSerializer(dateTimeFormatter)
            val deserializer = LocalDateTimeDeserializer(dateTimeFormatter)
            addSerializer(serializer)
            addDeserializer(deserializer)
        } as JacksonModule
    )
    .changeDefaultPropertyInclusion {
        it.withValueInclusion(JsonInclude.Include.NON_NULL)
    }.build()


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