package com.lxf;

import com.lxf.ui.Home;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {

        Home home = new Home();
        Scene scene = new Scene(home);
        primaryStage.setScene(scene);
        primaryStage.setTitle("文本搜索工具");
        primaryStage.show();
    }
}
