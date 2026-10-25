package io.brunoborges.showmyjvm.core;

import java.io.File;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lists the root CA certificates bundled with the JDK in {@code ${java.home}/lib/security/cacerts}.
 */
public final class CACertificates {

    private static final Logger LOG = LoggerFactory.getLogger(CACertificates.class);

    // The bundled cacerts file does not change while the JVM runs, so parse it only once.
    private static volatile List<CACertificate> cached;

    private CACertificates() {
    }

    public static File cacertsFile() {
        return new File(System.getProperty("java.home"), "lib/security/cacerts".replace('/', File.separatorChar));
    }

    public static List<CACertificate> list() {
        List<CACertificate> result = cached;
        if (result == null) {
            synchronized (CACertificates.class) {
                result = cached;
                if (result == null) {
                    result = load(cacertsFile());
                    cached = result;
                }
            }
        }
        return result;
    }

    static List<CACertificate> load(File file) {
        if (!file.isFile()) {
            LOG.warn("JDK cacerts file not found at {}", file);
            return List.of();
        }
        try {
            // Trusted certificate entries are readable without the store password.
            KeyStore keyStore = KeyStore.getInstance(file, (char[]) null);
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            HexFormat hex = HexFormat.ofDelimiter(":").withUpperCase();
            List<CACertificate> certificates = new ArrayList<>();
            for (String alias : Collections.list(keyStore.aliases())) {
                Certificate cert = keyStore.getCertificate(alias);
                if (!(cert instanceof X509Certificate x509)) {
                    continue;
                }
                CACertificate ca = new CACertificate();
                ca.setAlias(alias);
                String subject = x509.getSubjectX500Principal().getName();
                ca.setName(displayName(subject, alias));
                ca.setSubject(subject);
                ca.setIssuer(x509.getIssuerX500Principal().getName());
                ca.setSerialNumber(x509.getSerialNumber().toString(16).toUpperCase());
                ca.setNotBefore(x509.getNotBefore().toInstant().toString());
                ca.setNotAfter(x509.getNotAfter().toInstant().toString());
                ca.setSignatureAlgorithm(x509.getSigAlgName());
                ca.setPublicKeyAlgorithm(x509.getPublicKey().getAlgorithm());
                ca.setSha256Fingerprint(hex.formatHex(sha256.digest(x509.getEncoded())));
                certificates.add(ca);
            }
            certificates.sort(Comparator.comparing(CACertificate::getName, String.CASE_INSENSITIVE_ORDER));
            return List.copyOf(certificates);
        } catch (Exception e) {
            LOG.warn("Unable to read JDK cacerts file at {}", file, e);
            return List.of();
        }
    }

    /**
     * Human-readable certificate name: the subject CN, falling back to OU, then O, then the keystore alias.
     */
    static String displayName(String subjectDn, String alias) {
        try {
            Map<String, String> rdns = new HashMap<>();
            for (Rdn rdn : new LdapName(subjectDn).getRdns()) {
                rdns.putIfAbsent(rdn.getType().toUpperCase(), String.valueOf(rdn.getValue()));
            }
            for (String type : List.of("CN", "OU", "O")) {
                String value = rdns.get(type);
                if (value != null && !value.isBlank()) {
                    return value;
                }
            }
        } catch (InvalidNameException e) {
            LOG.debug("Unable to parse certificate subject {}", subjectDn, e);
        }
        return alias;
    }
}
