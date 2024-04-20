module ProjektniJava {
	requires javafx.controls;
	requires javafx.fxml;
	requires jdk.compiler;
	requires javafx.base;
	requires java.logging;
	
	opens application to javafx.graphics, javafx.fxml;
}
