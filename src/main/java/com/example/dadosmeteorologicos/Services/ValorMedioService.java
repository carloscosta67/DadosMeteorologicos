package com.example.dadosmeteorologicos.Services;

import java.sql.Date;
import java.util.List;

import com.example.dadosmeteorologicos.db.ValorMedioSQL;
import com.example.dadosmeteorologicos.model.RegistroDto;


public class ValorMedioService {
  public List<String[]> getCidadesDoBancoDeDados(){
    ValorMedioSQL banco = new ValorMedioSQL();
    List<String[]> cidades = banco.getCidadesMenuItem();
    banco.fecharConexao();
    return cidades;
  }
  
  public List<RegistroDto> consultaCidadePorIdEDatas(String id, Date dataInicial, Date dataFinal){
    ValorMedioSQL banco = new ValorMedioSQL(); 
    List<RegistroDto> cidades = banco.getRelatorioValorMedio(id, dataInicial, dataFinal);
    banco.fecharConexao();
    return cidades;
  }

}
