import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {
    Connection conn;
    PreparedStatement prep;
    ResultSet resultado; // Adicionando declaração do ResultSet

    public void cadastrarProduto(ProdutosDTO produto){
        conn = new conectaDAO().connectDB();
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + erro.getMessage());
        } finally {
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos(){
        conn = new conectaDAO().connectDB();
        String sql = "SELECT * FROM produtos";

        ArrayList<ProdutosDTO> listaProdutos = new ArrayList<>();

        try {
            prep = conn.prepareStatement(sql);
            resultado = prep.executeQuery(); // Atribuindo o resultado da consulta ao ResultSet

            while (resultado.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultado.getInt("id"));
                produto.setNome(resultado.getString("nome"));
                produto.setValor(resultado.getInt("valor"));
                produto.setStatus(resultado.getString("status"));

                listaProdutos.add(produto);
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + erro.getMessage());
        } finally {
            try {
                if (resultado != null) resultado.close(); // Fechando o ResultSet
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listaProdutos;
    }
    
    
    
    
    
    
    
    public void venderProduto(int idProduto) {
    conn = new conectaDAO().connectDB();
    String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

    try {
        prep = conn.prepareStatement(sql);
        prep.setInt(1, idProduto);
        int rowsAffected = prep.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Produto não encontrado!");
        }

    } catch (SQLException erro) {
        JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + erro.getMessage());
    } finally {
        try {
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

    
    
    
    
    
    
    
    
    public ArrayList<ProdutosDTO> listarVendas() {
    conn = new conectaDAO().connectDB();
    String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";

    ArrayList<ProdutosDTO> listaVendas = new ArrayList<>();

    try {
        prep = conn.prepareStatement(sql);
        resultado = prep.executeQuery();

        while (resultado.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultado.getInt("id"));
            produto.setNome(resultado.getString("nome"));
            produto.setValor(resultado.getInt("valor"));
            produto.setStatus(resultado.getString("status"));

            listaVendas.add(produto);
        }
    } catch (SQLException erro) {
        JOptionPane.showMessageDialog(null, "Erro ao listar vendas: " + erro.getMessage());
    } finally {
        try {
            if (resultado != null) resultado.close();
            if (prep != null) prep.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return listaVendas;
}

    
    
}
