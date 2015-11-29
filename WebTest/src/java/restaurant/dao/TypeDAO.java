/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import restaurant.core.entity.Type;
import static restaurant.dao.DB.*;

/**
 *
 * @author Admin
 */
public class TypeDAO implements DAO<Type> {

    @Override
    public ArrayList select() {
        ArrayList<Type> result = new ArrayList<>();
        int i = 0;
        try {
            resSet = statmt.executeQuery("SELECT * FROM type");
            while(resSet.next()){
                result.add(new Type());
                result.get(i).setTypeid(resSet.getInt("id"));
                result.get(i).setName(resSet.getString("name"));
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public void insert(Type type) {
        try {            
            resSet = statmt.executeQuery(
                    "select max(id) from type"
            );
            resSet.next();
            type.setTypeid(resSet.getInt(1)+1);
            statmt.execute(
                    "INSERT INTO type VALUES("+
                            type.getTypeid()+",\""+
                            type.getName()+
                            "\")"
            );
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        try {
            statmt.execute(
                    "DELETE FROM type WHERE id = "+id
            );
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(int id, Type updType) {
        try {            
            statmt.execute(
                    "UPDATE type SET name=\""+updType.getName()+
                            "\" WHERE id ="+id
            );
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
