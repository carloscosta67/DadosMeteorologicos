package com.example.dadosmeteorologicos.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.example.dadosmeteorologicos.model.Registro;

public class ValorMedioSQL extends IniciaBanco {

    private Connection conn;

    public ValorMedioSQL() {
        this.conn = super.conectarBanco();
    }

     public List<String[]> getCidadesMenuItem() {
        List<String[]> registros = new ArrayList<>();
        try {
            if (conn != null) {
                String sql = "SELECT " +
                            "Cidade.nome AS nome, " +
                            "Cidade.sigla AS sigla, " +
                            "MIN(Registro.data) AS data_primeiro_registro, " +
                            "MAX(Registro.data) AS data_ultimo_registro " +
                            "FROM " +
                                "Cidade " +
                            "JOIN " +
                                "Registro ON Cidade.sigla = Registro.siglaCidade " +
                            "WHERE " +
                                "Registro.siglaCidade = Cidade.sigla " +
                            "GROUP BY " +
                                "Cidade.nome, Cidade.sigla";
    
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
    
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    String sigla = rs.getString("sigla");
                    String dataPrimeiroRegistro = rs.getDate("data_primeiro_registro").toString();
                    String dataUltimoRegistro = rs.getDate("data_ultimo_registro").toString();
                    registros.add(new String[] {nome, sigla, dataPrimeiroRegistro, dataUltimoRegistro});
                }
            }
        } catch (SQLException e) {
            System.err.format(" CIDADES MENU SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return registros;
    }

    public List<Registro> getRelatorioValorMedio(String id, Date dataInicial, Date dataFinal){
        List<Registro> relatorioValorMedio = new ArrayList<>();
    
        try {
            if (conn != null) {
                String sql = "SELECT * FROM registro WHERE siglacidade = ? AND data >= ? AND data <= ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, id);
                stmt.setDate(2, new java.sql.Date(dataInicial.getTime()));
                stmt.setDate(3, new java.sql.Date(dataFinal.getTime()));
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int idRegistro = rs.getInt("id");
                    LocalDate data = rs.getDate("data").toLocalDate();
                    LocalTime hora = rs.getTime("hora").toLocalTime();
                    String cidade = rs.getString("siglacidade");
                    String estacao = rs.getString("estacao");
                    String tipo = rs.getString("tipo");
                    double valor = rs.getDouble("valor");
                    boolean suspeito = rs.getBoolean("suspeito");
                    relatorioValorMedio.add(new Registro(idRegistro, data, hora, cidade, estacao, tipo, valor, suspeito));
                }
                System.out.println("Relatorio Valor Medio: " + relatorioValorMedio.size());
            }
        } catch (SQLException e) {
            System.err.format(" RELATORIO VALOR MEDIO SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        
        return relatorioValorMedio;
    }

    

}
