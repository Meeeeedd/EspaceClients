package com.example.espaceclient;

import com.example.espaceclient.service.DonsService;
import com.example.espaceclient.utils.SimpleHttpServer;
import com.paypal.base.rest.APIContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/espaceclient/login.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();

        /*DonsService donsService = new DonsService();
        APIContext apiContext = new APIContext("3", "25577542Mine", "sandbox");
        SimpleHttpServer.startServer(donsService, apiContext);
    */
    }

    public static void main(String[] args) {
        launch(args);
    }
}