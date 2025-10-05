package com.example.servlet;

import com.example.db.UserDB;
import com.example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/emailList")
public class EmailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        User user = new User(firstName, lastName, email);
        try {
            UserDB.insert(user);
            request.setAttribute("message", "User added successfully!");
            request.setAttribute("messageType", "success");
        } catch (Exception e) {
            request.setAttribute("message", "Failed to add user: " + e.getMessage());
            request.setAttribute("messageType", "error");
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}