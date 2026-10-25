module showmyjvm.core {
    requires transitive java.management;
    requires transitive jdk.management;
    requires transitive org.slf4j;
    requires java.naming;

    exports io.brunoborges.showmyjvm.core;
    opens io.brunoborges.showmyjvm.core;
}
