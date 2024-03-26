package com.example.dadosmeteorologicos.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.example.dadosmeteorologicos.App;
import com.example.dadosmeteorologicos.Services.CSVResolve;
import com.example.dadosmeteorologicos.Services.RegistroDtoService;
import com.example.dadosmeteorologicos.model.RegistroDto;
import com.example.dadosmeteorologicos.repository.IniciaBanco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PrincipalController {
    @FXML
    private Button submitButton;
    @FXML
    private TextField nome;
    @FXML
    private PasswordField senha;
    @FXML
    private Button selecionarArquivo;
    private String caminhoArquivo;
    @FXML
    private Button gerarRelatorio;

    @FXML
    private Button valorMedio;

    public void PegaDados(ActionEvent actionEvent) {
        CSVResolve leitor = new CSVResolve(caminhoArquivo);
        leitor.validarCSV();
        List<String[]> csvFiltrado = leitor.CsvFiltrado();
        List<RegistroDto> listaRegistroDto = RegistroDtoService.criaRegistroDto(csvFiltrado);

        IniciaBanco banco = new IniciaBanco();
        banco.iniciarBanco();
        banco.salvarRegistro(listaRegistroDto);
    }

    @FXML
    void selecionarCsv(ActionEvent event) {
        // Obtém a referência do Stage atual
        Stage stage = (Stage) selecionarArquivo.getScene().getWindow();
    
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar arquivo CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos CSV", "*.csv"));
        // Passa a referência do Stage para o método
        File file = fileChooser.showOpenDialog(stage); 
        caminhoArquivo = file.getAbsolutePath();
        if (file != null) {

            System.out.println("Arquivo selecionado: " + file.getAbsolutePath());
        }
    }

    @FXML
    void tabelaRegistroController(ActionEvent event) {
        try {
        App.setRoot("TabelaRegistro");
        } catch (IOException e) {
        System.err.format("Erro ao abrir a tela de registros: %s", e.getMessage());
        }
    }

    @FXML
    void valorMedioViewController(ActionEvent event) {
        try {
        App.setRoot("valorMedio");
        } catch (IOException e) {
        System.err.format("Erro ao abrir a tela de valor médio: %s", e.getMessage());
        }
    }
}

