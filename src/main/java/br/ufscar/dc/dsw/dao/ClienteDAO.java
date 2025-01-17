package br.ufscar.dc.dsw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufscar.dc.dsw.domain.Cliente;

public class ClienteDAO extends GenericDAO {

    public void insert(Cliente cliente) {

        String sql = "INSERT INTO Pessoa (cpf, nome, telefone, sexo, data_nascimento, email, senha, cliente_admin) VALUES (?, ?, ?, ?, ?, ?, ?, 1)";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, cliente.getCpf());
            statement.setString(2, cliente.getNome());
            statement.setString(3, cliente.getTelefone());
            statement.setString(4, cliente.getSexo());
            statement.setString(5, cliente.getNascimento());
            statement.setString(6, cliente.getEmail());
            statement.setString(7, cliente.getSenha());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Cliente> getAll() {

        List<Cliente> listaCliente = new ArrayList<>();

        String sql = "SELECT * FROM Pessoa";

        try {
            Connection conn = this.getConnection();
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String cpf = resultSet.getString("cpf");
                String nome = resultSet.getString("nome");
                String telefone = resultSet.getString("telefone");
                String sexo = resultSet.getString("sexo");
                String nascimento  = resultSet.getString("nascimento");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                int admin = resultSet.getInt("cliente_admin");
                Cliente cliente = new Cliente(id, email, nome, senha, cpf, telefone, sexo, nascimento, admin);
                listaCliente.add(cliente);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaCliente;
    }

    public void delete(Cliente cliente) {
        String sql = "DELETE FROM Cliente where id = ?";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, cliente.getId());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Cliente cliente) {
        String sql = "UPDATE Pessoa SET cpf = ?, nome = ?, telefone = ?, sexo = ?, data_nascimento = ?, email = ?, senha = ?";
        sql += ", cliente_admin = ? WHERE id = ?";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, cliente.getCpf());
            statement.setString(2, cliente.getNome());
            statement.setString(3, cliente.getTelefone());
            statement.setString(4, cliente.getSexo());
            statement.setString(5, cliente.getNascimento());
            statement.setString(6, cliente.getEmail());
            statement.setString(7, cliente.getSenha());
            statement.setInt(8, cliente.getAdmin());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Cliente get(Long id) {
        Cliente cliente = null;

        String sql = "SELECT * from Pessoa where id = ? ";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String cpf = resultSet.getString("cpf");
                String nome = resultSet.getString("nome");
                String telefone = resultSet.getString("telefone");
                String sexo = resultSet.getString("sexo");
                String nascimento = resultSet.getString("data_nascimento");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                int admin = resultSet.getInt("cliente_admin");

                cliente = new Cliente(id, email, nome, senha, cpf, telefone, sexo, nascimento, admin);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cliente;
    }
}
