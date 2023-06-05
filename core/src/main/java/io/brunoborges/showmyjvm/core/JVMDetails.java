package io.brunoborges.showmyjvm.core;

import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;

import io.brunoborges.showmyjvm.core.PrintFlagsFinal.JVMFlag;

public class JVMDetails {

    String id;
    private List<String> systemProperties;
    private List<String> environmentVariables;
    private double processCpuLoad;
    private long freeSwapSpaceSize;
    private double cpuLoad;
    private long processCpuTime;
    private List<String> garbageCollectors;
    private List<JVMFlag> jvmFlags;

    public JVMDetails() {
    }

    public void jvmId(String name) {
        this.id = name;
    }

    public void systemProperties(List<String> collect) {
        this.systemProperties = collect;
    }

    public void environmentVariables(List<String> collect) {
        this.environmentVariables = collect;
    }

    public void garbageCollectors(List<String> collect) {
        this.garbageCollectors = collect;
    }

    public void processCpuTime(long processCpuTime) {
        this.processCpuTime = processCpuTime;
    }

    public void cpuLoad(double cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public void freeSwapSpaceSize(long freeSwapSpaceSize) {
        this.freeSwapSpaceSize = freeSwapSpaceSize;
    }

    public void processCpuTime(double processCpuLoad) {
        this.processCpuLoad = processCpuLoad;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the systemProperties
     */
    public List<String> getSystemProperties() {
        return systemProperties;
    }

    /**
     * @return the environmentVariables
     */
    public List<String> getEnvironmentVariables() {
        return environmentVariables;
    }

    /**
     * @return the processCpuLoad
     */
    public double getProcessCpuLoad() {
        return processCpuLoad;
    }

    /**
     * @return the freeSwapSpaceSize
     */
    public long getFreeSwapSpaceSize() {
        return freeSwapSpaceSize;
    }

    /**
     * @return the cpuLoad
     */
    public double getCpuLoad() {
        return cpuLoad;
    }

    /**
     * @return the processCpuTime
     */
    public long getProcessCpuTime() {
        return processCpuTime;
    }

    /**
     * @return the garbageCollectors
     */
    public List<String> getGarbageCollectors() {
        return garbageCollectors;
    }

    /**
     * @return the jvmFlags
     */
    public List<JVMFlag> getJvmFlags() {
        return jvmFlags;
    }

    public void compilerName(String name) {
    }

    public void totalCompilationTime(long totalCompilationTime) {
    }

    public void loadedClassCount(int loadedClassCount) {
    }

    public void unloadedClassCount(long unloadedClassCount) {
    }

    public void totalLoadedClassCount(long totalLoadedClassCount) {
    }

    public void inputArguments(List<String> inputArguments) {
    }

    public void vmVendor(String vmVendor) {
    }

    public void vmVersion(String vmVersion) {
    }

    public void vmName(String vmName) {
    }

    public void pidHostname(String name) {
    }

    public void heapMemoryUsage(MemoryUsage heapMemoryUsage) {
    }

    public void nonHeapMemoryUsage(MemoryUsage nonHeapMemoryUsage) {
    }

    public void memoryPoolMXBeans(List<MemoryPoolMXBean> memoryPoolMXBeans) {
    }

    public void threadCount(int threadCount) {
    }

    public void peakThreadCount(int peakThreadCount) {
    }

    public void totalStartedThreadCount(long totalStartedThreadCount) {
    }

    public void osName(String name) {
    }

    public void osVersion(String version) {
    }

    public void osArch(String arch) {
    }

    public void availableProcessors(int availableProcessors) {
    }

    public void systemLoadAverage(double systemLoadAverage) {
    }

    public void committedVirtualMemory(long committedVirtualMemorySize) {
    }

    public void totalMemorySize(long totalMemorySize) {
    }

    public void freeMemorySize(long freeMemorySize) {
    }

    public void totalSwapSpaceSize(long totalSwapSpaceSize) {
    }

    public void processCpuLoad(double processCpuLoad2) {
    }

    public void jvmFlags(List<JVMFlag> jvmFlags) {
        this.jvmFlags = jvmFlags;
    }

}
