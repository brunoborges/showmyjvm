package io.brunoborges.showmyjvm.core;

import java.lang.management.MemoryUsage;
import java.util.List;

import io.brunoborges.showmyjvm.core.IdentifyGC.GCType;
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
    private String compilerName;
    private long totalCompilationTime;
    private int loadedClassCount;
    private long unloadedClassCount;
    private long totalLoadedClassCount;
    private List<String> inputArguments;
    private String vmVendor;
    private String vmVersion;
    private String vmName;
    private String pidHostname;
    private MemoryUsage heapMemoryUsage;
    private MemoryUsage nonHeapMemoryUsage;
    private List<MemoryPoolDetails> memoryPoolMXBeans;
    private int threadCount;
    private int peakThreadCount;
    private long totalStartedThreadCount;
    private String osName;
    private String osVersion;
    private String osArch;
    private double systemLoadAverage;
    private long freeMemorySize;
    private long totalMemorySize;
    private long committedVirtualMemory;
    private long totalSwapSpaceSize;
    private int availableProcessors;
    private GCType gcType;

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
        this.compilerName = name;
    }

    public void totalCompilationTime(long totalCompilationTime) {
        this.totalCompilationTime = totalCompilationTime;
    }

    public void loadedClassCount(int loadedClassCount) {
        this.loadedClassCount = loadedClassCount;
    }

    public void unloadedClassCount(long unloadedClassCount) {
        this.unloadedClassCount = unloadedClassCount;
    }

    public void totalLoadedClassCount(long totalLoadedClassCount) {
        this.totalLoadedClassCount = totalLoadedClassCount;
    }

    public void inputArguments(List<String> inputArguments) {
        this.inputArguments = inputArguments;
    }

    public void vmVendor(String vmVendor) {
        this.vmVendor = vmVendor;
    }

    public void vmVersion(String vmVersion) {
        this.vmVersion = vmVersion;
    }

    public void vmName(String vmName) {
        this.vmName = vmName;
    }

    public void pidHostname(String name) {
        this.pidHostname = name;
    }

    public void heapMemoryUsage(MemoryUsage heapMemoryUsage) {
        this.heapMemoryUsage = heapMemoryUsage;
    }

    public void nonHeapMemoryUsage(MemoryUsage nonHeapMemoryUsage) {
        this.nonHeapMemoryUsage = nonHeapMemoryUsage;
    }

    public void memoryPoolMXBeans(List<MemoryPoolDetails> memoryPoolMXBeans) {
        this.memoryPoolMXBeans = memoryPoolMXBeans;
    }

    public void threadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public void peakThreadCount(int peakThreadCount) {
        this.peakThreadCount = peakThreadCount;
    }

    public void totalStartedThreadCount(long totalStartedThreadCount) {
        this.totalStartedThreadCount = totalStartedThreadCount;
    }

    public void osName(String name) {
        this.osName = name;
    }

    public void osVersion(String version) {
        this.osVersion = version;
    }

    public void osArch(String arch) {
        this.osArch = arch;
    }

    /**
     * @return the compilerName
     */
    public String getCompilerName() {
        return compilerName;
    }

    /**
     * @return the totalCompilationTime
     */
    public long getTotalCompilationTime() {
        return totalCompilationTime;
    }

    /**
     * @return the loadedClassCount
     */
    public int getLoadedClassCount() {
        return loadedClassCount;
    }

    /**
     * @return the unloadedClassCount
     */
    public long getUnloadedClassCount() {
        return unloadedClassCount;
    }

    /**
     * @return the totalLoadedClassCount
     */
    public long getTotalLoadedClassCount() {
        return totalLoadedClassCount;
    }

    /**
     * @return the inputArguments
     */
    public List<String> getInputArguments() {
        return inputArguments;
    }

    /**
     * @return the vmVendor
     */
    public String getVmVendor() {
        return vmVendor;
    }

    /**
     * @return the vmVersion
     */
    public String getVmVersion() {
        return vmVersion;
    }

    /**
     * @return the vmName
     */
    public String getVmName() {
        return vmName;
    }

    /**
     * @return the pidHostname
     */
    public String getPidHostname() {
        return pidHostname;
    }

    /**
     * @return the heapMemoryUsage
     */
    public MemoryUsage getHeapMemoryUsage() {
        return heapMemoryUsage;
    }

    /**
     * @return the nonHeapMemoryUsage
     */
    public MemoryUsage getNonHeapMemoryUsage() {
        return nonHeapMemoryUsage;
    }

    /**
     * @return the memoryPoolMXBeans
     */
    public List<MemoryPoolDetails> getMemoryPoolMXBeans() {
        return memoryPoolMXBeans;
    }

    /**
     * @return the threadCount
     */
    public int getThreadCount() {
        return threadCount;
    }

    /**
     * @return the peakThreadCount
     */
    public int getPeakThreadCount() {
        return peakThreadCount;
    }

    /**
     * @return the totalStartedThreadCount
     */
    public long getTotalStartedThreadCount() {
        return totalStartedThreadCount;
    }

    /**
     * @return the osName
     */
    public String getOsName() {
        return osName;
    }

    /**
     * @return the osVersion
     */
    public String getOsVersion() {
        return osVersion;
    }

    /**
     * @return the osArch
     */
    public String getOsArch() {
        return osArch;
    }

    /**
     * @return the systemLoadAverage
     */
    public double getSystemLoadAverage() {
        return systemLoadAverage;
    }

    /**
     * @return the freeMemorySize
     */
    public long getFreeMemorySize() {
        return freeMemorySize;
    }

    /**
     * @return the totalMemorySize
     */
    public long getTotalMemorySize() {
        return totalMemorySize;
    }

    /**
     * @return the committedVirtualMemory
     */
    public long getCommittedVirtualMemory() {
        return committedVirtualMemory;
    }

    /**
     * @return the totalSwapSpaceSize
     */
    public long getTotalSwapSpaceSize() {
        return totalSwapSpaceSize;
    }

    /**
     * @return the availableProcessors
     */
    public int getAvailableProcessors() {
        return availableProcessors;
    }

    public void availableProcessors(int availableProcessors) {
        this.availableProcessors = availableProcessors;
    }

    public void systemLoadAverage(double systemLoadAverage) {
        this.systemLoadAverage = systemLoadAverage;
    }

    public void committedVirtualMemory(long committedVirtualMemorySize) {
        this.committedVirtualMemory = committedVirtualMemorySize;
    }

    public void totalMemorySize(long totalMemorySize) {
        this.totalMemorySize = totalMemorySize;
    }

    public void freeMemorySize(long freeMemorySize) {
        this.freeMemorySize = freeMemorySize;
    }

    public void totalSwapSpaceSize(long totalSwapSpaceSize) {
        this.totalSwapSpaceSize = totalSwapSpaceSize;
    }

    public void processCpuLoad(double processCpuLoad2) {
        this.processCpuLoad = processCpuLoad2;
    }

    public void jvmFlags(List<JVMFlag> jvmFlags) {
        this.jvmFlags = jvmFlags;
    }

    public void gcType(GCType identifyGC) {
        this.gcType = identifyGC;
    }

    /**
     * @return the gcType
     */
    public GCType getGCType() {
     return gcType;
    }

}
