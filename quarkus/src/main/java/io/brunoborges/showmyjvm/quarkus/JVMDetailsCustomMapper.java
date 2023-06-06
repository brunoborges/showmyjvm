package io.brunoborges.showmyjvm.quarkus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.brunoborges.showmyjvm.core.JVMDetails;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.inject.Singleton;

@Singleton
public class JVMDetailsCustomMapper implements ObjectMapperCustomizer {

    @Override
    public void customize(ObjectMapper objectMapper) {
        objectMapper.addMixIn(JVMDetails.class, FilterMemoryPool.class);
        // objectMapper.setDefaultPrettyPrinter(new DefaultPrettyPrinter());
    }

    @JsonIgnoreProperties(value = {"memoryPoolMXBeans"})
    public static class FilterMemoryPool {
    }

}
