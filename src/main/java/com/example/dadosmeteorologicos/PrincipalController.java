package com.example.dadosmeteorologicos;

import java.io.File;
import java.util.List;

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

    public void PegaDados(@SuppressWarnings("exports") ActionEvent actionEvent) {
        System.out.println(nome.getText());
        System.out.println(senha.getText());

        CSVResolve leitor = new CSVResolve(caminhoArquivo);
        leitor.validarCSV();
        List<String[]> csvFiltrado = leitor.CsvFiltrado();
        List<RegistroDto> listaRegistroDto = RegistroDtoService.criaRegistroDto(csvFiltrado);

        IniciaBanco banco = new IniciaBanco();
        banco.iniciarBanco();
        banco.salvarRegistro(listaRegistroDto);
        System.out.println(banco.selecionarTodosRegistros());
        System.out.println();
        System.out.println("--------------- --------------------");
        System.out.println();
        System.out.println(banco.selecionarTodosRegistrosSuspeitos());
    }

    @FXML
    void selecionarCsv(ActionEvent event) {
        // Obtém a referência do Stage atual
        Stage stage = (Stage) selecionarArquivo.getScene().getWindow();
    
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar arquivo CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos CSV", "*.csv"));
        File file = fileChooser.showOpenDialog(stage); // Passa a referência do Stage para o método
        caminhoArquivo = file.getAbsolutePath();
        if (file != null) {
            // Aqui você pode fazer algo com o arquivo CSV selecionado
            System.out.println("Arquivo selecionado: " + file.getAbsolutePath());
        }
    }
}

