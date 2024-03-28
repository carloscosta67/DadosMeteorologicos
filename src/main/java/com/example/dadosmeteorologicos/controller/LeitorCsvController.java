package com.example.dadosmeteorologicos.controller;

import java.io.File;

import java.util.List;

import com.example.dadosmeteorologicos.Services.CSVResolve;
import com.example.dadosmeteorologicos.Services.LeitorCsvService;
import com.example.dadosmeteorologicos.Services.RegistroDtoService;
import com.example.dadosmeteorologicos.model.RegistroDto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class LeitorCsvController {
    private String caminhoArquivo;

    @FXML
    protected Button salvarCsvButton;
    @FXML
    private Button selecionarArquivo;
    
    @FXML
    public void initialize() {
        System.out.println("Iniciado Leitor CSV");
        salvarCsvButton.setVisible(false);
    }

    // Método para ler o arquivo CSV e salvar no banco, deve ser separado em 2?
    public void salvarBanco(ActionEvent actionEvent) {
        CSVResolve leitor = new CSVResolve(caminhoArquivo);
        if(!leitor.validarCSV()){

        }
        List<String[]> csvFiltrado = leitor.CsvFiltrado();
        List<RegistroDto> listaRegistroDto = RegistroDtoService.criaRegistroDto(csvFiltrado);
        LeitorCsvService service = new LeitorCsvService();
        service.salvarRegistro(listaRegistroDto);        
        System.out.println("foram salvos: " + listaRegistroDto.size() + " registros");
    }

    @FXML
    void selecionarCsv(ActionEvent event) {
        // Obtém a referência do Stage atual
        Stage stage = (Stage) selecionarArquivo.getScene().getWindow();
        salvarCsvButton.setVisible(true);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar arquivo CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos CSV", "*.csv"));
        // Passa a referência do Stage para o método
        File CsvEntrada = fileChooser.showOpenDialog(stage); 
        caminhoArquivo = CsvEntrada.getAbsolutePath();
        if (CsvEntrada != null) {
            System.out.println("Arquivo selecionado: " + CsvEntrada.getAbsolutePath());
        }
    }
}

