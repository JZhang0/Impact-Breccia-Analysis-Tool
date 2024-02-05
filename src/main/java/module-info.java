module app {
    exports src.main.java;
    exports src.main.java.GUI;
    exports utils.Processing;
    exports utils.File;

    requires java.desktop;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires opencv;
}
