package com.example.dadosmeteorologicos.controller;

import java.io.IOException;

import com.example.dadosmeteorologicos.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class TesteController {
    

        @FXML
    void valorMedioViewController(ActionEvent event) {
        try {
        App.setRoot("valorMedio");
        } catch (IOException e) {
        System.err.format("Erro ao abrir a tela de valor médio: %s", e.getMessage());
        }
    }

}
