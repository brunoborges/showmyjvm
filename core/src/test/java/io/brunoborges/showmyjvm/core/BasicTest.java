package io.brunoborges.showmyjvm.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    public void testCACertificates() {
        var certificates = CACertificates.list();
        assertFalse(certificates.isEmpty(), "JDK cacerts should contain root certificates");
        var first = certificates.get(0);
        assertNotNull(first.getAlias());
        assertNotNull(first.getSubject());
        assertNotNull(first.getNotAfter());
        assertNotNull(first.getSha256Fingerprint());
        assertFalse(new ShowJVM().extractJVMDetails().getCaCertificates().isEmpty());
    }

    @Test
    public void testCACertificateDisplayName() {
        assertEquals("My Root, Inc. CA", CACertificates.displayName("CN=My Root\\, Inc. CA,O=Org,C=US", "alias"));
        assertEquals("Unit", CACertificates.displayName("OU=Unit,O=Org,C=US", "alias"));
        assertEquals("Org", CACertificates.displayName("O=Org,C=US", "alias"));
        assertEquals("alias", CACertificates.displayName("not a dn", "alias"));
        CACertificates.list().forEach(c -> assertNotNull(c.getName()));
    }

    @Test
    public void testJVMFlags() {
        assertNotNull(new PrintFlagsFinal().getJVMFlags());
    }

}
