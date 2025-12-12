package io.brunoborges.showmyjvm.springboot;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.brunoborges.showmyjvm.core.JVMDetails;
import io.brunoborges.showmyjvm.core.ShowJVM;

@RestController
@RequestMapping("/jvm")
public class ShowController {

    @GetMapping(value = "/inspect", produces = MediaType.TEXT_PLAIN_VALUE)
    public String jvmDetailsDump() {
        return new ShowJVM().dumpJVMDetails();
    }

    @GetMapping(value = "/inspect.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public JVMDetails jvmDetailsJSON() {
        return new ShowJVM().extractJVMDetails();
    }

}