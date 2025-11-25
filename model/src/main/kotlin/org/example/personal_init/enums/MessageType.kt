package org.example.personal_init.enums

import org.example.personal_init.exception.EnumValueIsNotDefineException

enum class MessageType {
    REGISTER,
    RESET,
    MODIFY;

    companion object {
        fun from(value: String): MessageType =
            entries.firstOrNull { it.name.lowercase() == value } ?: throw EnumValueIsNotDefineException()
    }
}