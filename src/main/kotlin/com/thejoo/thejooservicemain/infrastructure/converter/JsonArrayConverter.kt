package com.thejoo.thejooservicemain.infrastructure.converter

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class ArrayStringConverter: AttributeConverter<List<String>, String> {
    private val mapper = ObjectMapper()

    override fun convertToDatabaseColumn(attribute: List<String>?): String {
        return mapper.writeValueAsString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String?): List<String> {
        return mapper.readValue(dbData, object : TypeReference<List<String>>() {})
    }
}