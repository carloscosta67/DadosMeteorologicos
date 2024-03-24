package com.example.dadosmeteorologicos.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.example.dadosmeteorologicos.App;
import com.example.dadosmeteorologicos.model.RegistroDto;
import com.example.dadosmeteorologicos.repository.IniciaBanco;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class tabelaRegistrosController {

    @FXML
    private Button voltarButton;
    @FXML
    private TableView<RegistroDto> tabelaRegistros;

    @FXML
    private TableColumn<RegistroDto, Integer> colunaId;

    @FXML
    private TableColumn<RegistroDto, String> colunaCidade;

    @FXML
    private TableColumn<RegistroDto, Integer> colunaEstacao;

    @FXML
    private TableColumn<RegistroDto, LocalDate> colunaData;

    @FXML
    private TableColumn<RegistroDto, LocalTime> colunaHora;

    @FXML
    private TableColumn<RegistroDto, Double> colunaTemperaturaMedia;

    @FXML
    private TableColumn<RegistroDto, Double> colunaUmidadeMedia;

    @FXML
    private TableColumn<RegistroDto, Double> colunaVelVento;

    @FXML
    private TableColumn<RegistroDto, Double> colunaDirVento;

    @FXML
    private TableColumn<RegistroDto, Double> colunaChuva;

    public void initialize() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        colunaEstacao.setCellValueFactory(new PropertyValueFactory<>("estacao"));
        colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colunaHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colunaTemperaturaMedia.setCellValueFactory(new PropertyValueFactory<>("temperaturaMedia"));
        colunaUmidadeMedia.setCellValueFactory(new PropertyValueFactory<>("umidadeMedia"));
        colunaVelVento.setCellValueFactory(new PropertyValueFactory<>("velVento"));
        colunaDirVento.setCellValueFactory(new PropertyValueFactory<>("dirVento"));
        colunaChuva.setCellValueFactory(new PropertyValueFactory<>("chuva"));

        List<RegistroDto> registros = buscarRegistrosDoBancoDeDados();
        tabelaRegistros.setItems(FXCollections.observableArrayList(registros));
    }

    private List<RegistroDto> buscarRegistrosDoBancoDeDados() {
        IniciaBanco banco = new IniciaBanco();
        return banco.selecionarTodosRegistros();
    }

    @FXML
    public void retornarPrincipal() {
        try {
        App.setRoot("principal");
        } catch (IOException e) {
        System.err.format("Erro ao abrir a tela de registros: %s", e.getMessage());
        }
    }

}
