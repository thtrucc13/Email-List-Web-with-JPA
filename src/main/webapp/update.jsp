<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.User, javax.persistence.EntityManager, com.example.util.DBUtil" %>
<html>
<head>
    <title>Update User</title>
    <link rel="stylesheet" href="styles/main.css">
</head>
<body>
<h1>Update User</h1>
<%
    String message = (String) request.getAttribute("message");
    String messageType = (String) request.getAttribute("messageType");
    if (message != null) {
%>
<div class="message <%= messageType %>"><%= message %></div>
<% } %>

<%
    Long userId = Long.parseLong(request.getParameter("id"));
    EntityManager em = DBUtil.getEntityManager();
    User user = em.find(User.class, userId);
    if (user != null) {
%>
<form action="userList" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="<%= user.getId() %>">
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required>
    <br>
    <label for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" value="<%= user.getFirstName() %>" required>
    <br>
    <label for="lastName">Last Name:</label>
    <input type="text" id="lastName" name="lastName" value="<%= user.getLastName() %>" required>
    <br>
    <input type="submit" value="Save Changes">
</form>
<% } else { %>
<div class="message error">User not found.</div>
<% } %>
<div style="text-align: center; margin-top: 1em;">
    <a href="userList.jsp">← Back to User List</a>
</div>
<%
    em.close();
%>
</body>
</html>