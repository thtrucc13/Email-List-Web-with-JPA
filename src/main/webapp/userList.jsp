<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.db.UserDB, com.example.model.User, javax.persistence.EntityManager, com.example.util.DBUtil, java.util.List" %>
<html>
<head>
    <title>User Management</title>
    <link rel="stylesheet" href="styles/main.css">
</head>
<body>
<h1>User Management</h1>
<%
    String message = (String) request.getAttribute("message");
    String messageType = (String) request.getAttribute("messageType");
    if (message != null) {
%>
<div class="message <%= messageType %>"><%= message %></div>
<% } %>
<div style="background-color: #ffffff; border: 2px solid #20B2AA; border-radius: 10px; padding: 2em; max-width: 800px; margin: 2em auto; box-shadow: 0 4px 10px rgba(0,0,0,0.1);">
    <h2 style="margin-top: 0;">Current Users</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Email</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Actions</th>
        </tr>
        <%
            EntityManager em = null;
            try {
                em = DBUtil.getEntityManager();
                List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
                if (users.isEmpty()) {
        %>
        <tr><td colspan="5" style="text-align:center;">No users found.</td></tr>
        <%
        } else {
            for (User user : users) {
        %>
        <tr>
            <td><%= user.getId() %></td>
            <td><%= user.getEmail() %></td>
            <td><%= user.getFirstName() %></td>
            <td><%= user.getLastName() %></td>
            <td>
                <a href="userList?action=update&id=<%= user.getId() %>"
                   style="background-color: #20B2AA; color: white; padding: 0.6em 1.2em; border-radius: 4px; text-decoration: none; margin-right: 0.5em; display: inline-block; font-weight: bold; border: none; cursor: pointer; font-size: 1em; line-height: 1.2; vertical-align: middle; transition: all 0.3s ease;">Update</a>
                <form action="userList" method="post" style="display: inline-block; background: none; border: none; padding: 0; margin: 0; vertical-align: middle;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="<%= user.getId() %>">
                    <input type="submit" value="Delete" onclick="return confirm('Are you sure you want to delete this user?');"
                           style="background-color: #dc3545; color: white; border: none; padding: 0.6em 1.2em; border-radius: 4px; cursor: pointer; font-weight: bold; font-size: 1em; line-height: 1.2; margin: 0; vertical-align: middle; transition: all 0.3s ease;">
                </form>
            </td>
        </tr>
        <%
                    }
                }
            } catch (Exception e) {
                out.println("<tr><td colspan='5' class='message error'>Error loading users: " + e.getMessage() + "</td></tr>");
            } finally {
                if (em != null && em.isOpen()) {
                    em.close();
                }
            }
        %>
    </table>
</div>
<div style="text-align: center; margin-top: 1em;">
    <a href="index.jsp">← Back to Home</a>
</div>
</body>
</html>