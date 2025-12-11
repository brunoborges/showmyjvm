package io.brunoborges.showmyjvm.core;

import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;

public class MemoryPoolDetails {

    private String name;
    private MemoryType type;
    private MemoryUsage usage;
    private MemoryUsage peakUsage;
    private MemoryUsage collectionUsage;
    private String[] managerNames;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MemoryType getType() {
        return type;
    }

    public void setType(MemoryType type) {
        this.type = type;
    }

    public MemoryUsage getUsage() {
        return usage;
    }

    public void setUsage(MemoryUsage usage) {
        this.usage = usage;
    }

    public MemoryUsage getPeakUsage() {
        return peakUsage;
    }

    public void setPeakUsage(MemoryUsage peakUsage) {
        this.peakUsage = peakUsage;
    }

    public MemoryUsage getCollectionUsage() {
        return collectionUsage;
    }

    public void setCollectionUsage(MemoryUsage collectionUsage) {
        this.collectionUsage = collectionUsage;
    }

    public String[] getManagerNames() {
        return managerNames;
    }

    public void setManagerNames(String[] managerNames) {
        this.managerNames = managerNames;
    }
}
