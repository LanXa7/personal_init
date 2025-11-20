package org.example.personal_init.ext

import com.fasterxml.jackson.annotation.JsonInclude
import tools.jackson.core.type.TypeReference
import tools.jackson.databind.JsonNode
import tools.jackson.databind.json.JsonMapper
import tools.jackson.module.kotlin.kotlinModule
import java.time.format.DateTimeFormatter

private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

val jsonMapper: JsonMapper = JsonMapper.builder()
    .addModule(kotlinModule())
    .changeDefaultPropertyInclusion {
        it.withValueInclusion(JsonInclude.Include.NON_NULL)
    }.build()


inline fun <reified T> JsonNode.getList(): List<T> =
    jsonMapper.convertValue(this, object : TypeReference<List<T>>() {})

inline fun <reified T> JsonNode.getMutableList(): MutableList<T> =
    jsonMapper.convertValue(this, object : TypeReference<MutableList<T>>() {})

inline fun <reified T> String.fromJson(): T =
    jsonMapper.readValue(this, object : TypeReference<T>() {})

fun Any.toJsonString(): String =
    jsonMapper.writeValueAsString(this)

fun <K, V> Map<K, V>.toJsonString(): String =
    jsonMapper.writeValueAsString(this)

fun String.toJsonNode(): JsonNode =
    jsonMapper.readTree(this)

inline fun <reified K, reified V> String.toMap(): Map<K, V> =
    jsonMapper.readValue(this, object : TypeReference<Map<K, V>>() {})

inline fun <reified T> String.toList(): List<T> =
    jsonMapper.readValue(this, object : TypeReference<List<T>>() {})

inline fun <reified K : Any, reified V : Any> String.toMapFromArray(
    fieldName: String,
    keyField: String,
    valueField: String
): Map<K, V> {
    val node: JsonNode = jsonMapper.readTree(this)
    return node[fieldName].associate { item ->
        val key = jsonMapper.convertValue(item[keyField], K::class.java)
        val value = jsonMapper.convertValue(item[valueField], V::class.java)
        key to value
    }
}