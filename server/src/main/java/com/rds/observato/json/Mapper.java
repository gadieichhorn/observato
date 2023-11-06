package com.rds.observato.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.blackbird.BlackbirdModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.dropwizard.jackson.*;

public class Mapper {

  public static ObjectMapper create() {
    return JsonMapper.builder()
        .addModule(new GuavaModule())
        .addModule(new JavaTimeModule())
        .addModule(new GuavaExtrasModule())
        .addModule(new CaffeineModule())
        //        .addModule(new JodaModule())
        .addModule(new BlackbirdModule())
        .addModule(new FuzzyEnumModule())
        .addModule(new ParameterNamesModule())
        .addModule(new Jdk8Module())
        .addModule(new JavaTimeModule())
        .propertyNamingStrategy(new AnnotationSensitivePropertyNamingStrategy())
        .registerSubtypes(ImmutableMap.class)
        .registerSubtypes(ImmutableList.class)
        .registerSubtypes(ImmutableSet.class)
        .disable(MapperFeature.USE_GETTERS_AS_SETTERS)
        .enable(MapperFeature.ALLOW_COERCION_OF_SCALARS)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
        .disable(DeserializationFeature.WRAP_EXCEPTIONS)
        .disable(DeserializationFeature.ACCEPT_FLOAT_AS_INT)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
        .enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS)
        .enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
        .serializationInclusion(JsonInclude.Include.NON_NULL)
        .build()
        .setSubtypeResolver(new DiscoverableSubtypeResolver());
  }
}
