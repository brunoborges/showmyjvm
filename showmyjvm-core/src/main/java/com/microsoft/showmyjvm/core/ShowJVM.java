package com.microsoft.showmyjvm.core;


import sun.management.ManagementFactoryHelper;

import java.lang.management.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

public class ShowJVM {

    public static void main(String args[]) {
        System.out.println(new ShowJVM().getJVMDetails());
    }

    private StringBuilder buffer = new StringBuilder();

    protected StringBuilder append(Object s) {
        return buffer.append(s).append("\n");
    }

    public String getJVMDetails() {
        buffer = new StringBuilder();

        // Runtime Properties
        append("");
        append("## Runtime Properties");
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        append(runtimeMXBean.getVmName() + " " + runtimeMXBean.getVmVersion());
        append("PID @ Hostname: " + runtimeMXBean.getName());
        runtimeMXBean.getInputArguments().forEach(arg -> append("RuntimeMXBean input: " + arg));

        // Memory Settings
        final int mb = 1024 * 1024;
        Runtime runtime = Runtime.getRuntime();
        append("");
        append("## Memory Settings [MB]");
        append("Used Memory: " + (runtime.totalMemory() - runtime.freeMemory()) / mb + "MB");
        append("Free Memory: " + runtime.freeMemory() / mb + "MB");
        append("Total Memory: " + runtime.totalMemory() / mb + "MB");
        append("Max Memory: " + runtime.maxMemory() / mb + "MB");
        // printMemorySummary();

        // Loaded Classes
        /*
        ClassLoadingMXBean classLoading = ManagementFactoryHelper.getClassLoadingMXBean();
        append("");
        append("## Loaded Classes");
        append("Total # of loaded classes (from the RuntimeInfo start): " + classLoading.getTotalLoadedClassCount());
        append("Total # of unloaded classes: " + classLoading.getUnloadedClassCount());
        append("Current # of loaded classes: " + classLoading.getLoadedClassCount());
        */

        // Compilation
        /*
        CompilationMXBean compiler = ManagementFactoryHelper.getCompilationMXBean();
        append("");
        append("## Compiler");
        append("Compiler Name: "+compiler.getName());
        append("Total Compilation Time: " + compiler.getTotalCompilationTime());
        */

        // CPU Usage
        /*
        append("");
        append("## CPU Usage");
        printCPUUsage();
        */

        // Threads
        /*
        ThreadMXBean threads = ManagementFactoryHelper.getThreadMXBean();
        append("");
        append("## Threads");

        append("Threads / Started Threads: " + threads.getThreadCount() + " / " + threads.getTotalStartedThreadCount());
        append("CPU Time / User Time: " + threads.getCurrentThreadCpuTime() + " / " + threads.getCurrentThreadUserTime());

        Arrays.stream(threads.dumpAllThreads(true, true))
                .forEach(thread -> append("Thread Name: " + thread.getThreadName()));
        */

        // System Properties
        append("");
        append("## All System Properties:");
        for (Entry<Object, Object> entry : System.getProperties().entrySet()) {
            jvmprop(entry.getKey(), entry.getValue());
        }

        // Environment Variables
        append("");
        append("## Environment variables:");
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            jvmprop(entry.getKey(), entry.getValue());
        }

        return buffer.toString();
    }

    private void jvmprop(Object key, Object value) {
        String v = value == null ? "" : value.toString();
        String line = new StringBuilder().append("\"" + key + "\": \"")
                .append(v.replace("\\", "\\\\")).append("\"").toString();
        append(line);
    }

    public  void printMemorySummary() {
        MemoryMXBean memoryMXBean = ManagementFactoryHelper.getMemoryMXBean();
        printMemoryUsage(memoryMXBean.getHeapMemoryUsage(), "HEAP MEMORY");
        printMemoryUsage(memoryMXBean.getNonHeapMemoryUsage(), "NON-HEAP MEMORY");

        ManagementFactoryHelper.getMemoryPoolMXBeans()
                .forEach(memoryPool -> printMemoryUsage(memoryPool.getUsage(), memoryPool.getName()));

        ManagementFactoryHelper.getRuntimeMXBean();
    }

    private void printMemoryUsage(MemoryUsage nonHeapMemory, String memoryName) {
        append("");
        append(memoryName);
        append("------------------------");
        append("INIT: " + toMB(nonHeapMemory.getInit()));
        append("USED: " + toMB(nonHeapMemory.getUsed()));
        append("COMMITTED: " + toMB(nonHeapMemory.getCommitted()));
        append("MAX: " + toMB(nonHeapMemory.getMax()));
    }

    private String toMB(long bytes) {
        return (bytes >> 20) + " MB";
    }

    private void printCPUUsage() {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.getName().startsWith("get")
                    && Modifier.isPublic(method.getModifiers())) {
                Object value;
                try {
                    value = method.invoke(operatingSystemMXBean);
                } catch (Exception e) {
                    value = e;
                } // try
                append(method.getName() + " = " + value);
            } // if
        } // for
    }

}
