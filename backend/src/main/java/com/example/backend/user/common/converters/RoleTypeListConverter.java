package com.example.backend.user.common.converters;

import com.example.backend.user.common.enums.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import javax.persistence.AttributeConverter;

public class RoleTypeListConverter implements AttributeConverter<List<Role>, String> {

  private static final ObjectMapper mapper = new ObjectMapper()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

  @Override
  public String convertToDatabaseColumn(List<Role> attribute) {
    try {
      return mapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public List<Role> convertToEntityAttribute(String dbData) {
    try {
      return mapper.readValue(dbData, new TypeReference<>() {
      });
    } catch (IOException e) {
      throw new IllegalArgumentException("Error converting JSON to List<Role>", e);
    }
  }
}
