package com.example.dadosmeteorologicos.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;

import com.example.dadosmeteorologicos.Services.CSVResolve;
import com.example.dadosmeteorologicos.Services.LeitorCsvService;
import com.example.dadosmeteorologicos.Services.RegistroDtoService;
import com.example.dadosmeteorologicos.exceptions.CSVInvalidoException;
import com.example.dadosmeteorologicos.exceptions.NomeCSVInvalidoException;
import com.example.dadosmeteorologicos.model.RegistroDto;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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

    // Botão que faz o processo de validar CSV, ler e salvar no banco
    public void salvarBanco(ActionEvent actionEvent) {
        CSVResolve leitor = new CSVResolve(caminhoArquivo);
        try {
            leitor.validarCSV();
        } catch (CSVInvalidoException e) {
            // Se a validação do CSV falhar, mostre a caixa de diálogo de erro e retorna para escolher novamente o arquivo
            mostrarDialogoCabecalhoCsvInvalido();
                return;
        } catch (NomeCSVInvalidoException e) {
            //A FAZER: Deve ser criado uma metodo(funcao) para pegar o nome da cidade, sigla da cidade e numero da estacao e verificar 
            // se existe no banco
            // A FAZER: Deve ser solicitado ao usuário que insira o nome da cidade, sigla da cidade e número da estação e criar a cidade 
            // no banco
            Platform.runLater(this::mostrarDialogoNomeInvalido);
            return;
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

    //Dialogos de validação
    private void mostrarDialogoNomeInvalido() {
        // Crie a caixa de diálogo personalizada
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Nome inválido");
    
        // Crie os campos de entrada
        TextField CampoNomeCidade = new TextField();
        TextField CampoSiglaCidade = new TextField();
        TextField CampoNumeroEstacao = new TextField();
     
    
        // Adicione os campos de entrada à caixa de diálogo
        GridPane grid = new GridPane();
        grid.add(new Label("Nome da cidade:"), 0, 0);
        grid.add(CampoNomeCidade, 1, 0);
        grid.add(new Label("Sigla da cidade:"), 0, 1);
        grid.add(CampoSiglaCidade, 1, 1);
        grid.add(new Label("Número da estação:"), 0, 2);
        grid.add(CampoNumeroEstacao, 1, 2);
        dialog.getDialogPane().setContent(grid);
    
        // Crie os botões de "Salvar" e "Sair"
        ButtonType buttonTypeSalvar = new ButtonType("Salvar", ButtonData.OK_DONE);
        ButtonType buttonTypeSair = new ButtonType("Sair", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeSalvar, buttonTypeSair);
    
        // Defina o resultado da caixa de diálogo para ser um array com os valores dos campos de entrada
        dialog.setResultConverter(dialogButton -> {
            String[] result = new String[3];
            result[0] = CampoNomeCidade.getText();
            result[1] = CampoSiglaCidade.getText();
            result[2] = CampoNumeroEstacao.getText();
            return result;
        });
    
        // Mostre a caixa de diálogo e obtenha o resultado
        Optional<String[]> result = dialog.showAndWait();
        result.ifPresent(res -> {
            // Faça algo com os valores inseridos pelo usuário
            // Por exemplo, armazene-os em variáveis ou use-os para atualizar o arquivo CSV
            String nomeCidade = res[0];
            String siglaCidade = res[1];
            String numeroEstacao = res[2];
    
            System.out.println("Nome da cidade: " + nomeCidade + ", sigla da cidade: " 
                + siglaCidade + ", número da estação: " + numeroEstacao);
        });
    }

    public void mostrarDialogoCabecalhoCsvInvalido(){
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Cabeçalho do CSV fora do padrão esperado");
            alert.showAndWait();
        });
    }
}

