module showmyjvm.core {
    requires transitive java.management;
    requires transitive jdk.management;
    requires transitive org.slf4j;

    exports io.brunoborges.showmyjvm.core;
    opens io.brunoborges.showmyjvm.core;
}
