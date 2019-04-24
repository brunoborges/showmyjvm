package com.microsoft.showmyjvm.springboot;

import com.microsoft.showmyjvm.core.ShowJVM;
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

    @GetMapping(value = "/api/showjvm", produces = "text/plain")
    public String showjvm() {
        return new ShowJVM().getJVMDetails();
    }

}
