package io.brunoborges.showmyjvm.quarkus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.brunoborges.showmyjvm.core.JVMDetails;
import io.quarkus.jackson.JacksonMixin;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.inject.Singleton;

@Singleton
public class JVMDetailsCustomMapper implements ObjectMapperCustomizer {

    @Override
    public void customize(ObjectMapper om) {
        om.enable(SerializationFeature.INDENT_OUTPUT);
        om.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);

        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);

        om.setDefaultPrettyPrinter(prettyPrinter);
    }

    @JacksonMixin(JVMDetails.class)
    @JsonIgnoreProperties(value = {"memoryPoolMXBeans"})
    public static class FilterMemoryPool {
    }

}
