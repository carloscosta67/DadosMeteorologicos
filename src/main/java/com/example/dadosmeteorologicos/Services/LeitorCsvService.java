package com.example.dadosmeteorologicos.Services;

import java.util.List;

import com.example.dadosmeteorologicos.db.LeitorCsvSQL;
import com.example.dadosmeteorologicos.model.RegistroDto;

public class LeitorCsvService {
    
    public void salvarRegistro(List<RegistroDto> listaRegistroDto){
        LeitorCsvSQL banco = new LeitorCsvSQL();
        banco.salvarRegistro(listaRegistroDto);
        banco.fecharConexao();
    }
}
