/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.dao;

import restaurant.core.entity.Event;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static restaurant.dao.DB.*;

/**
 *
 * @author Julinna
 */
public class EventDAO implements DAO<Event>{
    
    @Override
    public ArrayList<Event> select() {
        ArrayList<Event> result = new ArrayList<>();
        int i = 0;
        try {
            resSet = statmt.executeQuery("SELECT * FROM event");
            while(resSet.next()){
                result.add(new Event());
                result.get(i).setEventid(resSet.getInt("id"));
                result.get(i).setName(resSet.getString("name"));
                result.get(i).setType(resSet.getString("type"));
                result.get(i).setDate(resSet.getString("date"));
               
                i++;
            }       
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public void insert(Event event) {
        try {
            resSet = statmt.executeQuery(
                    "select max(id) from event"
            );
            resSet.next();
            event.setEventid(resSet.getInt(1)+1);
            statmt.execute(
                    "INSERT INTO event VALUES("+
                            event.getEventid()+",\""+
                            event.getName()+"\",\""+
                            event.getType()+"\","+
                            event.getDate()+"\""+
                            //event.getAlarm()+
                            ")"
            );             
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        try {
            statmt.execute(
                    "DELETE FROM event WHERE id = "+id
            );
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(int id, Event updEvent) {
        try {            
            statmt.execute(
                    "UPDATE event SET name=\""+updEvent.getName()+
                            "\",type=\""+updEvent.getType()+
                            "\",date=\""+updEvent.getDate()+
                            //"\",alarm=\""+updEvent.getAlarm()+
                            "\" WHERE id ="+id
            );
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Event> searchByName(String eventName) throws SQLException{
        ArrayList<Event> result = new ArrayList<>();
        int i = 0;
        resSet = statmt.executeQuery(
                "SELECT * FROM event WHERE name LIKE '%" + eventName + "%'"
        );
        while(resSet.next()){
            result.add(new Event());
            result.get(i).setEventid(resSet.getInt("id"));
            result.get(i).setName(resSet.getString("name"));
            result.get(i).setType(resSet.getString("type"));
            result.get(i).setDate(resSet.getString("date"));
            //result.get(i).setAlarm(resSet.getString("alarm"));
            i++;
        }
        return result;
    }
    
    public ArrayList<Event> searchByType(String typeName) throws SQLException{
        ArrayList<Event> result = new ArrayList<>();
        int i = 0;
        resSet = statmt.executeQuery(
                "SELECT * FROM event WHERE type LIKE '%" + typeName + "%'"
        );
        while(resSet.next()){
            result.add(new Event());
            result.get(i).setEventid(resSet.getInt("id"));
            result.get(i).setName(resSet.getString("name"));
            result.get(i).setType(resSet.getString("type"));
            result.get(i).setDate(resSet.getString("date"));
            //result.get(i).setAlarm(resSet.getString("alarm"));
            i++;
        }
        return result;
    }
}
