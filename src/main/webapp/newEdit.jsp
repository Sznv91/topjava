<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title><%=request.getParameter("action")%></title>
</head>
<body>

<jsp:useBean id="formatter" scope="request" type="java.time.format.DateTimeFormatter"/>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<a href="index.html">HOME</a><br>
--------------------------------<br>
Edit Meal<br><br>
id:${meal.id}<br>

<form action="meals" method="post" name="doPost">
    <input type="hidden" name="action" value="<%=request.getParameter("action")%>">
    <input type="hidden" name="id" value="${meal.id}">

    Описание: <input type="text" name="description" value="${meal.description}"><br><br>
    Дата и время(Для корректного отображения необходима поддержка HTML5): <input type="datetime-local" name="dateTime" value="${meal.dateTime}"> <br><br>
    Калории: <input type="number" name="calories" value="${meal.calories}"><br><br>
    <input type="submit" name="submit" value="Send"> <input type="Reset" value="reset"> <input type="button" value="Back" onClick='location.href="meals"'>
</form>

</body>
</html>
