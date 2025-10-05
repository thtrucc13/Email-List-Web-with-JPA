<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Join Our Email List</title>
    <link rel="stylesheet" href="styles/main.css">
</head>
<body>
<h1 style="margin-top: 2em;">Join Our Email List</h1>
<%
    String message = (String) request.getAttribute("message");
    String messageType = (String) request.getAttribute("messageType");
    if (message != null) {
%>
<div class="message <%= messageType %>"><%= message %></div>
<% } %>
<form action="emailList" method="post">
    <input type="hidden" name="action" value="insert">
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required>
    <br>
    <label for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" required>
    <br>
    <label for="lastName">Last Name:</label>
    <input type="text" id="lastName" name="lastName" required>
    <br>
    <input type="submit" value="Add User">
</form>
<div style="text-align: center; margin-top: 1em;">
    <a href="userList.jsp">→ View User List</a>
</div>
</body>
</html>