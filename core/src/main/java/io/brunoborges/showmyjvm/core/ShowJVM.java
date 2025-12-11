package io.brunoborges.showmyjvm.core;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShowJVM {

    public static void main(String[] args) {
        System.out.println(new ShowJVM().dumpJVMDetails());
    }

    private StringBuilder buffer = new StringBuilder(5000);

    private void append(Object s) {
        buffer.append(s).append("\n");
    }

    private void append(String msg, Object... s) {
        buffer.append(String.format(msg, s)).append("\n");
    }

    private String bytesToMBString(long bytes) {
        return bytes / 1024 / 1024 + " MB";
    }

    public JVMDetails extractJVMDetails() {
        JVMDetails jvmDetails = new JVMDetails();
        jvmDetails.pidHostname(ManagementFactory.getRuntimeMXBean().getName());

        // Runtime MBean
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        jvmDetails.vmName(runtimeMXBean.getVmName());
        jvmDetails.vmVersion(runtimeMXBean.getVmVersion());
        jvmDetails.vmVendor(runtimeMXBean.getVmVendor());
        jvmDetails.inputArguments(runtimeMXBean.getInputArguments());

        // Class Loading MBean
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        jvmDetails.totalLoadedClassCount(classLoadingMXBean.getTotalLoadedClassCount());
        jvmDetails.unloadedClassCount(classLoadingMXBean.getUnloadedClassCount());
        jvmDetails.loadedClassCount(classLoadingMXBean.getLoadedClassCount());

        // Compilation MBean
        CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();
        jvmDetails.compilerName(compilationMXBean.getName());
        jvmDetails.totalCompilationTime(compilationMXBean.getTotalCompilationTime());

        // Memory MBean
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        jvmDetails.heapMemoryUsage(memoryMXBean.getHeapMemoryUsage());
        jvmDetails.nonHeapMemoryUsage(memoryMXBean.getNonHeapMemoryUsage());

        // Memory Pool MBean
        List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
        List<MemoryPoolDetails> memoryPoolDetails = memoryPoolMXBeans.stream().map(bean -> {
            MemoryPoolDetails details = new MemoryPoolDetails();
            details.setName(bean.getName());
            details.setType(bean.getType());
            details.setUsage(bean.getUsage());
            details.setPeakUsage(bean.getPeakUsage());
            details.setCollectionUsage(bean.getCollectionUsage());
            details.setManagerNames(bean.getMemoryManagerNames());
            return details;
        }).collect(Collectors.toList());
        jvmDetails.memoryPoolMXBeans(memoryPoolDetails);

        // Thread MBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        jvmDetails.threadCount(threadMXBean.getThreadCount());
        jvmDetails.peakThreadCount(threadMXBean.getPeakThreadCount());
        jvmDetails.totalStartedThreadCount(threadMXBean.getTotalStartedThreadCount());

        // Operating System MBean
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        jvmDetails.osName(operatingSystemMXBean.getName());
        jvmDetails.osVersion(operatingSystemMXBean.getVersion());
        jvmDetails.osArch(operatingSystemMXBean.getArch());
        jvmDetails.availableProcessors(operatingSystemMXBean.getAvailableProcessors());
        jvmDetails.systemLoadAverage(operatingSystemMXBean.getSystemLoadAverage());

        // Use reflection to access com.sun.management.OperatingSystemMXBean to avoid access restrictions
        try {
            Class<?> osBeanClass = Class.forName("com.sun.management.OperatingSystemMXBean");
            if (osBeanClass.isInstance(operatingSystemMXBean)) {
                Object osBean = osBeanClass.cast(operatingSystemMXBean);
                
                long committedVM = (Long) osBeanClass.getMethod("getCommittedVirtualMemorySize").invoke(osBean);
                long totalMemory = (Long) osBeanClass.getMethod("getTotalMemorySize").invoke(osBean);
                long freeMemory = (Long) osBeanClass.getMethod("getFreeMemorySize").invoke(osBean);
                long totalSwap = (Long) osBeanClass.getMethod("getTotalSwapSpaceSize").invoke(osBean);
                long freeSwap = (Long) osBeanClass.getMethod("getFreeSwapSpaceSize").invoke(osBean);
                double cpuLoad = (Double) osBeanClass.getMethod("getCpuLoad").invoke(osBean);
                double processCpuLoad = (Double) osBeanClass.getMethod("getProcessCpuLoad").invoke(osBean);
                long processCpuTime = (Long) osBeanClass.getMethod("getProcessCpuTime").invoke(osBean);
                
                jvmDetails.committedVirtualMemory(committedVM);
                jvmDetails.totalMemorySize(totalMemory);
                jvmDetails.freeMemorySize(freeMemory);
                jvmDetails.totalSwapSpaceSize(totalSwap);
                jvmDetails.freeSwapSpaceSize(freeSwap);
                jvmDetails.cpuLoad(cpuLoad);
                jvmDetails.processCpuLoad(processCpuLoad);
                jvmDetails.processCpuTime(processCpuTime);
            }
        } catch (Exception e) {
            // Extended OS bean features not available
        }

        // PrintFlagsFinal
        PrintFlagsFinal printFlagsFinal = new PrintFlagsFinal();

        // JVM Flags Final
        jvmDetails.jvmFlags(printFlagsFinal.getJVMFlags());

        // Garbage Collector MBean (reuse printFlagsFinal if possible)
        IdentifyGC identifyGC = new IdentifyGC(printFlagsFinal);
        jvmDetails.gcType(identifyGC.getGCType());

        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        jvmDetails.garbageCollectors(garbageCollectorMXBeans.stream()
                .map(gcBean -> gcBean.getName() + ": " + gcBean.getObjectName().toString())
                .collect(Collectors.toList()));

        // Environment Variables
        jvmDetails.environmentVariables(System.getenv().entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.toList()));

        // System Properties
        jvmDetails.systemProperties(System.getProperties().entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.toList()));

        return jvmDetails;
    }

    public String dumpJVMDetails() {
        runtimeProperties();
        memorySettings();
        loadedClasses();
        compilationStats();
        garbageCollectorInfo();
        cpuUsage();
        threadDetails();
        systemProperties();
        environmentVariables();
        jvmFlags();


        String _return = buffer.toString();
        buffer.setLength(0);
        return _return;
    }

    private void jvmFlags() {
        // JVM Final Flags
        append("");
        append("## JVM Final Flags:");
        new PrintFlagsFinal().getJVMFlags().forEach(this::append);
    }

    private void environmentVariables() {
        // Environment Variables
        append("");
        append("## Environment variables:");
        System.getenv().forEach(this::appendJVMProperty);
    }

    private void systemProperties() {
        // System Properties
        append("");
        append("## All System Properties:");
        System.getProperties().forEach(this::appendJVMProperty);
    }

    private void threadDetails() {
        // Threads
        ThreadMXBean threads = ManagementFactory.getThreadMXBean();
        append("");
        append("## Threads");

        append("Threads / Started Threads / Peak: " + threads.getThreadCount() + " / "
                + threads.getTotalStartedThreadCount() + " / " + threads.getPeakThreadCount());
        append("CPU Time / User Time: " + threads.getCurrentThreadCpuTime() + " / "
                + threads.getCurrentThreadUserTime());

        Arrays.stream(threads.dumpAllThreads(true, true))
                .forEach(thread -> append("Thread Name: " + thread.getThreadName()));
    }

    private void cpuUsage() {
        // CPU Usage
        append("");
        append("## CPU");

        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        append("osMXBean.getSystemLoadAverage: %s", Double.toString(osBean.getSystemLoadAverage()));
        append("osMXBean.getAvailableProcessors: %s", Integer.toString(osBean.getAvailableProcessors()));

        // Using reflection to avoid access restriction warnings in Java 8
        try {
            Class<?> osBeanClass = Class.forName("com.sun.management.OperatingSystemMXBean");
            if (osBeanClass.isInstance(osBean)) {
                Object castedOsBean = osBeanClass.cast(osBean);
                
                long committedVM = (Long) osBeanClass.getMethod("getCommittedVirtualMemorySize").invoke(castedOsBean);
                long totalMemory = (Long) osBeanClass.getMethod("getTotalMemorySize").invoke(castedOsBean);
                long freeMemory = (Long) osBeanClass.getMethod("getFreeMemorySize").invoke(castedOsBean);
                long totalSwap = (Long) osBeanClass.getMethod("getTotalSwapSpaceSize").invoke(castedOsBean);
                long freeSwap = (Long) osBeanClass.getMethod("getFreeSwapSpaceSize").invoke(castedOsBean);
                double systemCpuLoad = (Double) osBeanClass.getMethod("getCpuLoad").invoke(castedOsBean);
                double processCpuLoad = (Double) osBeanClass.getMethod("getProcessCpuLoad").invoke(castedOsBean);
                long processCpuTime = (Long) osBeanClass.getMethod("getProcessCpuTime").invoke(castedOsBean);
                
                append("osMXBean.getCommittedVirtualMemorySize: %s", bytesToMBString(committedVM));
                append("osMXBean.getTotalPhysicalMemorySize: %s", bytesToMBString(totalMemory));
                append("osMXBean.getFreePhysicalMemorySize: %s", bytesToMBString(freeMemory));
                append("osMXBean.getTotalSwapSpaceSize: %s", bytesToMBString(totalSwap));
                append("osMXBean.getFreeSwapSpaceSize: %s", bytesToMBString(freeSwap));
                append("osMXBean.getSystemCpuLoad: %s", Double.toString(systemCpuLoad));
                append("osMXBean.getProcessCpuLoad: %s", Double.toString(processCpuLoad));
                append("osMXBean.getProcessCpuTime: %s", Double.toString(processCpuTime));
            }
        } catch (Exception e) {
            append("Error accessing extended OS bean features: " + e.getMessage());
        }
    }

    private void garbageCollectorInfo() {
        // Garbage Collector
        append("");
        append("## Garbage Collectors");
        append(" - GC Type: " + new IdentifyGC().getGCType());
        List<GarbageCollectorMXBean> gcMxBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcBean : gcMxBeans) {
            append(gcBean.getName() + ": " + gcBean.getObjectName().toString());
        }
    }

    private void compilationStats() {
        // Compilation
        CompilationMXBean compiler = ManagementFactory.getCompilationMXBean();
        append("");
        append("## Compiler");
        append("Compiler Name: " + compiler.getName());
        append("Total Compilation Time: " + compiler.getTotalCompilationTime());
    }

    private void loadedClasses() {
        // Loaded Classes
        ClassLoadingMXBean classLoading = ManagementFactory.getClassLoadingMXBean();
        append("");
        append("## Loaded Classes");
        append("Total # of loaded classes (from the RuntimeInfo start): " + classLoading.getTotalLoadedClassCount());
        append("Total # of unloaded classes: " + classLoading.getUnloadedClassCount());
        append("Current # of loaded classes: " + classLoading.getLoadedClassCount());
    }

    private void memorySettings() {
        // Memory Settings
        final int mb = 1024 * 1024;
        Runtime runtime = Runtime.getRuntime();
        append("");
        append("## Memory Settings [MB]");
        append("Used Memory: " + (runtime.totalMemory() - runtime.freeMemory()) / mb + "MB");
        append("Free Memory: " + runtime.freeMemory() / mb + "MB");
        append("Total Memory: " + runtime.totalMemory() / mb + "MB");
        append("Max Memory: " + runtime.maxMemory() / mb + "MB");
    }

    private void runtimeProperties() {
        // Runtime Properties
        append("");
        append("## Runtime Properties");
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        append(runtimeMXBean.getVmName() + " " + runtimeMXBean.getVmVersion());
        append("PID @ Hostname: " + runtimeMXBean.getName());
        runtimeMXBean.getInputArguments().forEach(arg -> append("RuntimeMXBean input: " + arg));
    }

    private final StringBuilder sb_reuse = new StringBuilder();

    private void appendJVMProperty(Object key, Object value) {
        String v = value == null ? "" : value.toString();
        String line = sb_reuse.append("\"").append(key).append("\": \"").append(v.replace("\\", "\\\\")).append("\"")
                .toString();
        append(line);
        sb_reuse.setLength(0);
    }

}
