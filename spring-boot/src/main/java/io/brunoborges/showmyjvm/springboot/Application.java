package io.brunoborges.showmyjvm.springboot;

import io.brunoborges.showmyjvm.core.ShowJVM;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

@RestController
class ShowController {

    @GetMapping(value = { "/", "/inspect" }, produces = "text/plain")
    public String showjvm() {
        return new ShowJVM().dumpJVMDetails();
    }

}
