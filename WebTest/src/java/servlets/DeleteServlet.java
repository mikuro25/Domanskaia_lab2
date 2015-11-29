/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import restaurant.core.entity.Event;
import restaurant.core.entity.Type;
import restaurant.dao.DB;
import restaurant.dao.EventDAO;
import restaurant.dao.TypeDAO;


/**
 *
 * @author Admin
 */
@WebServlet(name = "DeleteServlet", urlPatterns = {"/DeleteServlet"})
public class DeleteServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("windows-1251");
        try {
            DB.Connect();
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(SelectServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>DeleteServlet</title>");            
        out.println("</head>");
        out.println("<body>");  
        out.println("Select rows to delete<br><br>");
        out.println("<form action='DeleteServlet' method='post'>");
        out.println("<table border='1px' style='margin-left: 10px; border-spacing: 0px;'>");
        
        
        String dbTable = (String) request.getSession().getAttribute("dbTable");
        switch (dbTable){
            case "event":
                EventDAO meal = new EventDAO();
                ArrayList<Event> m = meal.select();
                out.println("<tr>");
                out.println("<td>id</td>"); out.println("<td>name</td>"); out.println("<td>type</td>"); out.println("<td>date</td>"); /*out.println("<td>alarm</td>"); */out.println("<td>to DELETE</td>");
                out.println("</tr>");
                for(int i=0; i<m.size(); i++){
                    out.println("<tr>");
                        out.println("<td>"+m.get(i).getEventid()+"</td>");
                        out.println("<td>"+m.get(i).getName()+"</td>");
                        out.println("<td>"+m.get(i).getType()+"</td>");
                        out.println("<td>"+m.get(i).getDate()+"</td>");
                        //out.println("<td>"+m.get(i).getAlarm()+"</td>");
                        out.println("<td><input type='checkbox' name='delete' value='"+m.get(i).getEventid()+"'></td>");
                    out.println("</tr>");
                }
                break;
            case "type":
                TypeDAO type = new TypeDAO();
                ArrayList<Type> c = type.select();
                out.println("<tr>");
                out.println("<td>id</td>"); out.println("<td>name</td>"); out.println("<td>to DELETE</td>");
                out.println("</tr>");
                for(int i=0; i<c.size(); i++){
                    out.println("<tr>");
                        out.println("<td>"+c.get(i).getTypeid()+"</td>");
                        out.println("<td>"+c.get(i).getName()+"</td>");
                        out.println("<td><input type='checkbox' name='delete' value='"+c.get(i).getTypeid()+"'></td>");
                    out.println("</tr>");
                }
                break;
        }
        out.println("</table>");
        out.println("<br><input type='submit' name='delete' value='delete' style='width: 80px'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String dbTable = (String) request.getSession().getAttribute("dbTable");
        try {
            DB.Connect();
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(SelectServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] selectedRows = request.getParameterValues("delete");
        
        if(selectedRows != null){
            switch (dbTable){
                case "event":
                    EventDAO event = new EventDAO();
                    for(int i=0; i<selectedRows.length-1; i++){
                        System.out.println(selectedRows[i]);
                        event.delete(Integer.parseInt(selectedRows[i]));
                    }
                    break;
                case "type":
                    TypeDAO type = new TypeDAO();
                    for(int i=0; i<selectedRows.length-1; i++){
                        System.out.println(selectedRows[i]);
                        type.delete(Integer.parseInt(selectedRows[i]));
                    }
                    break;
            }
        }
        out.println("DELETE executed successfully!");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
