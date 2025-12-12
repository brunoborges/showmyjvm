module sparkjava {
    requires showmyjvm.core;
    requires com.google.gson;
    requires spark.core;

    opens io.brunoborges.showmyjvm.sparkjava to com.google.gson;
}
