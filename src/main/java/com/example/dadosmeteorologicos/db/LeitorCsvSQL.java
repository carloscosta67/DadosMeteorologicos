package com.example.dadosmeteorologicos.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.dadosmeteorologicos.model.RegistroDto;

public class LeitorCsvSQL extends IniciaBanco{
    private Connection conn;

    public LeitorCsvSQL() {
        this.conn = super.conectarBanco();
    }

    public void salvarRegistro(List<RegistroDto> listaRegistroDto) {
   
        try {
            if (conn != null) {
                conn.setAutoCommit(false); // Inicia uma transação
    
                String sql = "INSERT INTO Registro" + 
                "(cidade, estacao, data, hora, temperaturaMedia, umidadeMedia, velVento, dirVento, chuva, temperaturaSuspeita, umidadeSuspeita, velocidadeVentoSuspeita, direcaoVentoSuspeita, chuvaSuspeita)"+
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                 // PreparedStatement é uma interface usada para executar consultas SQL parametrizadas. 
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    for (RegistroDto registro : listaRegistroDto) {
                        pstmt.setString(1, registro.getCidade());
                        pstmt.setString(2, registro.getEstacao());
                        pstmt.setDate(3, java.sql.Date.valueOf(registro.getData()));
                        pstmt.setTime(4, java.sql.Time.valueOf(registro.getHora()));
                        setDoubleOrNull(pstmt, 5, registro.getTemperaturaMedia());
                        setDoubleOrNull(pstmt, 6, registro.getUmidadeMedia());
                        setDoubleOrNull(pstmt, 7, registro.getVelVento());
                        setDoubleOrNull(pstmt, 8, registro.getDirVento());
                        setDoubleOrNull(pstmt, 9, registro.getChuva());
                        pstmt.setBoolean(10, registro.isTemperaturaSuspeita());
                        pstmt.setBoolean(11, registro.isUmidadeSuspeita());
                        pstmt.setBoolean(12, registro.isVelocidadeVentoSuspeita());
                        pstmt.setBoolean(13, registro.isDirecaoVentoSuspeita());
                        pstmt.setBoolean(14, registro.isChuvaSuspeita());
                        
                        // "Batch" refere-se a um lote de comandos SQL que são enviados ao banco de dados como um único grupo para serem 
                        // executados. Isso é feito para melhorar o desempenho, especialmente quando você tem muitos comandos SQL 
                        // semelhantes para executar.
                        pstmt.addBatch();
                    }
    
                    pstmt.executeBatch();
                    conn.commit(); // Finaliza a transação
                } catch (SQLException e) {
                    conn.rollback(); // Desfaz as alterações se ocorrer um erro
                    throw e;
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL Stateee: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    private void setDoubleOrNull(PreparedStatement pstmt, int parameterIndex, Double value) throws SQLException {
        if (value != null) {
            pstmt.setDouble(parameterIndex, value);
        } else {
            pstmt.setNull(parameterIndex, java.sql.Types.DOUBLE);
        }
    }

    public void CriarCidadeEstacaoCsv(String nomeCidadeInserido, String siglaCidadeInserida, String numeroEstacaoInserido){
        // Este método verifica se a cidade e a estação inseridas já existem no banco de dados.
        // Se não existirem, ele cria a cidade e/ou a estação. A estação contém uma chave estrangeira para a cidade.

        try {
            // Verifica se a conexão com o banco de dados está estabelecida
            if (conn!=null){
                // Prepara a consulta SQL para verificar se a cidade já existe no banco de dados
                String sql = "SELECT * FROM Cidade WHERE nome = ? AND sigla = ?";
                 // PreparedStatement é uma interface usada para executar consultas SQL parametrizadas. 
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nomeCidadeInserido);
                stmt.setString(2, siglaCidadeInserida);
                
                // Se a cidade não existir no banco de dados, insere a nova cidade
                // !stmt.executeQuery().next() será true se a consulta SQL não retornar nenhuma linha e false se retornar pelo menos uma linha.
                if (!stmt.executeQuery().next()){
                    sql = "INSERT INTO Cidade (nome, sigla) VALUES (?, ?)";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, nomeCidadeInserido);
                    stmt.setString(2, siglaCidadeInserida);
                    stmt.executeUpdate();
                }
                // Prepara a consulta SQL para obter o id da cidade
                sql = "SELECT id FROM Cidade WHERE sigla = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, siglaCidadeInserida);
                ResultSet rs = stmt.executeQuery();
    
                // Se a cidade existir, obtém o id da cidade
                if (rs.next()) {
                    int idCidade = rs.getInt("id");

                    // Verifica se a estação já existe no banco de dados
                    sql = "SELECT * FROM Estacao WHERE numero = ? AND idCidade = ?";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, numeroEstacaoInserido);
                    stmt.setInt(2, idCidade);
                    ResultSet rsEstacao = stmt.executeQuery();
    
                    /// Se a estação não existir no banco de dados, insere a nova estação
                    if (!rsEstacao.next()) {
                        sql = "INSERT INTO Estacao (numero, idCidade) VALUES (?, ?)";
                        stmt = conn.prepareStatement(sql);
                        stmt.setString(1, numeroEstacaoInserido);
                        stmt.setInt(2, idCidade);
                        stmt.executeUpdate();
                    }
                }
            }        
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
                

    }

}
