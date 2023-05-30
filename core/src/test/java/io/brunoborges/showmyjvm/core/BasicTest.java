package io.brunoborges.showmyjvm.core;

import org.junit.jupiter.api.Test;

import io.brunoborges.showmyjvm.core.ShowJVM;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BasicTest {

    @Test
    public void testApp() {
        assertNotNull(new ShowJVM().getJVMDetails());
    }

}
