package io.brunoborges.showmyjvm.core;

import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;

import com.sun.management.HotSpotDiagnosticMXBean;
import com.sun.management.VMOption;

public class PrintFlagsFinal {

    private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PrintFlagsFinal.class);

    private boolean fallbackToHotSpotDiagnosticMXBean = false;

    public List<JVMFlag> getJVMFlags() {
        List<JVMFlag> flagsFound;
        // load the diagnostic bean first to avoid UnsatisfiedLinkError
        final HotSpotDiagnosticMXBean hsdiag = ManagementFactory.getPlatformMXBean(HotSpotDiagnosticMXBean.class);
        List<VMOption> options;
        try {
            final Class<?> flagClass = Class.forName("com.sun.management.internal.Flag");
            final Method getAllFlagsMethod = flagClass.getDeclaredMethod("getAllFlags");
            final Method getVMOptionMethod = flagClass.getDeclaredMethod("getVMOption");
            getAllFlagsMethod.setAccessible(true);
            getVMOptionMethod.setAccessible(true);
            final Object result = getAllFlagsMethod.invoke(null);
            final List<?> flags = (List<?>) result;
            options = new ArrayList<VMOption>(flags.size());
            for (final Object flag : flags) {
                options.add((VMOption) getVMOptionMethod.invoke(flag));
            }
        } catch (Exception e) {

            Class<?> inaccessibleException = null;
            try {
                inaccessibleException = Class.forName("java.lang.reflect.InaccessibleObjectException");
            } catch (ClassNotFoundException e1) {
            }

            if (inaccessibleException != null && e.getClass().equals(inaccessibleException)) {
                LOGGER.error(
                        "This JVM does not open package jdk.management/com.sun.management.internal to this module. To include all flags, run with --add-opens jdk.management/com.sun.management.internal=ALL-UNNAMED");
            }

            LOGGER.error("There was an error. Falling back to HotSpotDiagnosticMXBean. HotSpotDiagnosticMXBean only includes writable flags.");
            if (hsdiag != null) {
                // only includes writable external flags
                options = hsdiag.getDiagnosticOptions();
            } else {
                options = Collections.emptyList();
            }
        }
        final Map<String, VMOption> optionMap = new TreeMap<>();
        for (final VMOption option : options) {
            optionMap.put(option.getName(), option);
        }

        flagsFound = new ArrayList<>(optionMap.size());

        for (final VMOption option : optionMap.values()) {
            LOGGER.info(option.getName() + " = " + option.getValue() + " (" + option.getOrigin() + ", "
                    + (option.isWriteable() ? "read-write" : "read-only") + ")");

            var jvmFlag = new JVMFlag(option.getName(), option.getValue(), option.getOrigin().name(),
                    option.isWriteable());
            flagsFound.add(jvmFlag);
        }
        LOGGER.info(options.size() + " options found");
        return flagsFound;
    }

    public static class JVMFlag {
        private String name;
        private String value;
        private String origin;
        private boolean writable;

        public JVMFlag(String name, String value, String origin, boolean writable) {
            this.name = name;
            this.value = value;
            this.origin = origin;
            this.writable = writable;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public String getOrigin() {
            return origin;
        }

        public boolean isWritable() {
            return writable;
        }
    }

}
