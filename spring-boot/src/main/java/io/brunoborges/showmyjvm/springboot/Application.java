package io.brunoborges.showmyjvm.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.brunoborges.showmyjvm.core.JVMDetails;
import io.brunoborges.showmyjvm.core.ShowJVM;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

@RestController
class ShowController {

    @GetMapping(value = { "/", "/inspect" }, produces = "text/plain")
    public String jvmDetailsDump() {
        return new ShowJVM().dumpJVMDetails();
    }

    @GetMapping(value = { "/", "/inspect.json" }, produces = "application/json")
    public JVMDetails jvmDetailsJSON() {
        return new ShowJVM().extractJVMDetails();
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();

        om.enable(SerializationFeature.INDENT_OUTPUT);
        om.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);

        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);

        om.setDefaultPrettyPrinter(prettyPrinter);

        return om;
    }

}
