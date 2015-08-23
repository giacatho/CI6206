/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci6206.servlet;

import ci6206.dao.UserDao;
import ci6206.model.User;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nguyentritin
 */
@WebServlet(name = "GetUser", urlPatterns = {"/get-users"})
public class GetUser extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDao dao = new UserDao();
        String jspPath = null;
        
        
        String by = request.getParameter("by");
        
        switch (by) {
            case "firstname":
                String partialTitle = request.getParameter("firstname");
                List<User> users = dao.findByFirstname(partialTitle);
                
                if (users.size() >0)
                {
                    request.setAttribute("users", users);
                    jspPath = "WEB-INF/jsp/many-users.jsp";
                } else {
                    jspPath = "WEB-INF/jsp/no-user.jsp";
                }
                
                break;
            
                
            case "id":
                int id = Integer.parseInt(request.getParameter("id"));
                User user = dao.findById(id);
                
                if (user!=null) {
                    request.setAttribute("user", user);
                    jspPath = "WEB-INF/jsp/one-user.jsp";
                } else {
                    jspPath = "WEB-INF/jsp/no-user.jsp";
                }
                
                break;
            
                
            default:    
                jspPath = "WEB-INF/jsp/no-user.jsp";
                break;
        }
        
        request.getRequestDispatcher(jspPath).forward(request, response);
    }

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
        processRequest(request, response);
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
        processRequest(request, response);
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
