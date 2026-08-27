package model.dao;

import db.DB;
import model.dao.impl.usuarioDaoImpl;

public class DaoFactory {
    public static usuarioDao createusuarioDao(){
        return new usuarioDaoImpl(DB.getConnections());
    }
}
