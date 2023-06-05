package io.brunoborges.showmyjvm.core;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class BasicTest {

    @Test
    public void testApp() {
        assertNotNull(new ShowJVM().dumpJVMDetails());
    }

    @Test
    public void testJVMDetails() {
        assertNotNull(new ShowJVM().extractJVMDetails());
    }

    @Test
    public void testJVMFlags() {
        assertNotNull(new PrintFlagsFinal().getJVMFlags());
    }

}
