package com.microsoft.showmyjvm.micronaut;

import com.microsoft.showmyjvm.core.ShowJVM;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.runtime.Micronaut;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }
}

@Controller("/api/showjvm")
class ShowController {

    @Get(produces = MediaType.TEXT_PLAIN)
    public String showjvm() {
        return new ShowJVM().getJVMDetails();
    }

}
