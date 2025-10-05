package com.example.servlet;

import com.example.db.UserDB;
import com.example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/userList")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String id = request.getParameter("id");

        try {
            if ("update".equals(action)) {
                User user = new User(firstName, lastName, email);
                if (id != null && !id.isEmpty()) {
                    user.setId(Long.parseLong(id));
                }
                UserDB.update(user);
                request.setAttribute("message", "User updated successfully!");
                request.setAttribute("messageType", "success");
            } else if ("delete".equals(action)) {
                if (id != null && !id.isEmpty()) {
                    UserDB.delete(Long.parseLong(id));
                }
                request.setAttribute("message", "User deleted successfully!");
                request.setAttribute("messageType", "success");
            } else {
                User user = new User(firstName, lastName, email);
                UserDB.insert(user);
                request.setAttribute("message", "User added successfully!");
                request.setAttribute("messageType", "success");
            }
        } catch (Exception e) {
            request.setAttribute("message", "Operation failed: " + e.getMessage());
            request.setAttribute("messageType", "error");
        }
        request.getRequestDispatcher("/userList.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        if ("update".equals(action) && id != null) {
            request.getRequestDispatcher("/update.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/userList.jsp").forward(request, response);
        }
    }
}