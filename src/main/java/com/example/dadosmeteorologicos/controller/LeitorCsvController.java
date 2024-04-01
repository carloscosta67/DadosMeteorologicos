package com.example.dadosmeteorologicos.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;

import com.example.dadosmeteorologicos.Services.CSVResolve;
import com.example.dadosmeteorologicos.Services.LeitorCsvService;
import com.example.dadosmeteorologicos.Services.RegistroDtoService;
import com.example.dadosmeteorologicos.exceptions.CSVInvalidoException;
import com.example.dadosmeteorologicos.model.RegistroDto;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
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

    private String siglaCidadeInserida;
    private String numeroEstacaoInserido;
    
    @FXML
    public void initialize() {
        System.out.println("Iniciado Leitor CSV");
        salvarCsvButton.setVisible(false);
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

    // Botão que faz o processo de validar CSV, ler e salvar no banco
    public void salvarBanco(ActionEvent actionEvent) {
        CSVResolve leitor = new CSVResolve(caminhoArquivo);
        try {
            leitor.validarCSV();
        } catch (CSVInvalidoException e) {
            // Se a validação do CSV falhar, mostre a caixa de diálogo de erro e retorna para escolher novamente o arquivo
            mostrarDialogoCabecalhoCsvInvalido();
            return;
        } 
        if (leitor.isNomeInvalido()) {
            Optional<String[]> result = mostrarDialogoNomeInvalido();
            if (result.isPresent()) {
                leitor.setCodigoCidade(siglaCidadeInserida);
                leitor.setCodigoEstacao(numeroEstacaoInserido);
            } else {
                // O usuário cancelou o diálogo, lidar com isso aqui
                return;
            }

        } 
            //Caso entrar no catch do nome invalido, esse codigo deve aguardar para ser executado, pois o usuario deve inserir os dados:
            List<String[]> csvFiltrado = leitor.filtrarCSV();
            List<RegistroDto> listaRegistroDto = RegistroDtoService.criaRegistroDto(csvFiltrado);
            LeitorCsvService service = new LeitorCsvService();
            service.salvarRegistro(listaRegistroDto);        
            System.out.println("foram salvos: " + listaRegistroDto.size() + " registros");
    }
        
    //Dialogos de validação
    private Optional<String[]> mostrarDialogoNomeInvalido() {
        // Crie a caixa de diálogo personalizada
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Não foi possível identificar a cidade e a estação do arquivo CSV");
    
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
        // se apertar o buttonTypeSair ele deve fechar o dialogo e impedir o resto do codigo.
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeSalvar, buttonTypeSair);
        
        // Obtenha o botão "Salvar" e inicialmente o desabilite
        Button salvarButton = (Button) dialog.getDialogPane().lookupButton(buttonTypeSalvar);
        salvarButton.setDisable(true);

        // Ouvinte de propriedade que será acionado sempre que o texto em qualquer campo for alterado
        ChangeListener<String> textChangeListener = (observable, oldValue, newValue) -> {
            boolean allFieldsFilled = !CampoNomeCidade.getText().trim().isEmpty() &&
                                    !CampoSiglaCidade.getText().trim().isEmpty() &&
                                    !CampoNumeroEstacao.getText().trim().isEmpty();
            salvarButton.setDisable(!allFieldsFilled);
        };

        // Ouvinte de propriedade aos campos de texto
        CampoNomeCidade.textProperty().addListener(textChangeListener);
        CampoSiglaCidade.textProperty().addListener(textChangeListener);
        CampoNumeroEstacao.textProperty().addListener(textChangeListener);

        // Defina o resultado da caixa de diálogo para ser um array com os valores dos campos de entrada
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeSalvar) {
                String[] result = new String[3];
                result[0] = CampoNomeCidade.getText();
                result[1] = CampoSiglaCidade.getText();
                result[2] = CampoNumeroEstacao.getText();
                return result;
            }
            return null;
        });

        // Mostre a caixa de diálogo e obtenha o resultado
        Optional<String[]> result = dialog.showAndWait();

        
        result.ifPresent(res -> {
            // Faça algo com os valores inseridos pelo usuário
            // Por exemplo, armazene-os em variáveis ou use-os para atualizar o arquivo CSV
            String nomeCidadeInserido = res[0];
            siglaCidadeInserida = res[1];
            numeroEstacaoInserido = res[2];

            // Verificar se a cidade e a estaçao já existem no banco de dados
            LeitorCsvService service = new LeitorCsvService();
            service.CriarCidadeEstacaoCsv(nomeCidadeInserido, siglaCidadeInserida, numeroEstacaoInserido);

            //  Tem que verificar se deu tudo certo e mostrar para o usuario
    
            System.out.println("Nome da cidade: " + nomeCidadeInserido + ", sigla da cidade: " 
                + siglaCidadeInserida + ", número da estação: " + numeroEstacaoInserido);
        });
        return result;
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

