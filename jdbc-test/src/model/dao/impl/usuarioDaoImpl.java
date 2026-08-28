package model.dao.impl;
//https://www.youtube.com/watch?v=XNNsBBTAhlE
import db.DB;
import exception.usuarioNotFound;
import model.dao.usuarioDao;
import model.entities.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class usuarioDaoImpl implements usuarioDao {
    private final Connection connection;

    public usuarioDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(usuario persona) {
        PreparedStatement preparedStatement = null;
        try{
            String sqlInsert = "INSERT INTO usuario(name, email)\n" +
                    "VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, persona.getName());
            preparedStatement.setString(2, persona.getEmail());
            preparedStatement.executeUpdate();

        }catch (SQLException sqlException) {
            throw new RuntimeException(sqlException.getMessage());
        }finally{
            DB.closeStatement(preparedStatement);
        }

    }

    @Override
    public int updateById(usuario persona) {

        PreparedStatement preparedStatement = null;
        findById(persona.getId());
        try{
            String sqlUpdateById =  "UPDATE usuario SET name = ?, email = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sqlUpdateById);

            preparedStatement.setString(1, persona.getName());
            preparedStatement.setString(2, persona.getEmail());
            preparedStatement.setInt(3, persona.getId());

            return preparedStatement.executeUpdate();

        }catch (SQLException sqlException) {
            throw new RuntimeException(sqlException.getMessage());
        }finally{
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public int deleteById(Integer id) {
        PreparedStatement preparedStatement = null;
        findById(id);
        try{
            String sqlDeleteById = "DELETE FROM usuario WHERE id = ?";

            preparedStatement = connection.prepareStatement(sqlDeleteById);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();

        }catch (SQLException sqlException) {
            throw new RuntimeException(sqlException.getMessage());
        }finally{
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public usuario findById(Integer id){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            String sqlConsultById = "SELECT * FROM usuario WHERE id = ?";
            preparedStatement = connection.prepareStatement(sqlConsultById);
            preparedStatement.setInt(1,id);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return getusuario(resultSet);

            }else {
                throw new usuarioNotFound("usuario Not Found");
            }
        }catch (SQLException sqlException) {
            throw new RuntimeException(sqlException.getMessage());
        }finally{
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }

    }

    @Override
    public List<usuario> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<usuario> listusuario = new ArrayList<>();
        try{
            String sqlConsultall = "SELECT * FROM usuario";
            preparedStatement = connection.prepareStatement(sqlConsultall);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               listusuario.add(getusuario(resultSet));
            }
            return listusuario;
        }catch (SQLException sqlException) {
            throw new RuntimeException(sqlException.getMessage());
        }finally{
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }

    }

    private usuario getusuario(ResultSet resultSet) throws SQLException {
        usuario personas = new usuario();
        personas.setId(resultSet.getInt("id"));
        personas.setName(resultSet.getString("name"));
        personas.setEmail(resultSet.getString("email"));
        return personas;




    }

}
