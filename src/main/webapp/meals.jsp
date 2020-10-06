<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title><%= request.getAttribute("action")%>
    </title>
</head>
<body>
<a href="index.html">HOME</a><br><br>
<input type="button" value="New Meal" onClick='location.href="meals?action=new"'>

<table border="1">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <jsp:useBean id="formatter" scope="request" type="java.time.format.DateTimeFormatter"/>
    <jsp:useBean id="mealToList" scope="request" type="java.util.List"/>
    <c:forEach var="meal" items="${mealToList}">

        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr bgcolor="${meal.excess ? "red" : "green"}">
            <td>${formatter.format(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
