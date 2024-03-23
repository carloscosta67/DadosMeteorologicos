package com.example.dadosmeteorologicos;

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

public class PrincipalController {
    @FXML
    private Button submitButton;
    @FXML
    private TextField nome;
    @FXML
    private PasswordField senha;

    public void PegaDados(@SuppressWarnings("exports") ActionEvent actionEvent) {
        System.out.println(nome.getText());
        System.out.println(senha.getText());

        CSVResolve leitor = new CSVResolve("src/main/resources/com/example/dadosmeteorologicos/A777_TBT.csv");
        leitor.validarCSV();
        List<String[]> csvFiltrado = leitor.CsvFiltrado();

        CSVResolve leitor1 = new CSVResolve("src/main/resources/com/example/dadosmeteorologicos/8888_SP.csv");
        leitor1.validarCSV();
        List<String[]> csvFiltrado1 = leitor1.CsvFiltrado();
       
        List<RegistroDto> listaRegistroDto = RegistroDtoService.criaRegistroDto(csvFiltrado);
        List<RegistroDto> listaRegistroDto1 = RegistroDtoService.criaRegistroDto(csvFiltrado1);


            IniciaBanco banco = new IniciaBanco();
            banco.iniciarBanco();
            banco.salvarRegistro(listaRegistroDto);
            // banco.salvarRegistro(listaRegistroDto1);
            System.out.println(banco.selecionarTodosRegistros());
            System.out.println();
            System.out.println("--------------- --------------------");
            System.out.println();
            System.out.println(banco.selecionarTodosRegistrosSuspeitos());
    
    }
}

