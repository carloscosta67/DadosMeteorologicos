package com.example.dadosmeteorologicos.Services;

import java.sql.Date;
import java.util.List;

import com.example.dadosmeteorologicos.model.RegistroDto;
import com.example.dadosmeteorologicos.repository.IniciaBanco;

public class ValorMedioService {
  public List<String[]> getCidadesDoBancoDeDados(){
    IniciaBanco banco = new IniciaBanco();
    List<String[]> cidades = banco.getCidadesMenuItem();
    return cidades;
  }
  
  public List<RegistroDto> consultaPorIdEDatas(String id, Date dataInicial, Date dataFinal){
    IniciaBanco banco = new IniciaBanco();
    List<RegistroDto> cidades = banco.getRelatorioValorMedio(id, dataInicial, dataFinal);
    return cidades;
  }

}
