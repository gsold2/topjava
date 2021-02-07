<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ru">
<head>
    <title>Meal list</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <jsp:useBean id="mealsTo" scope="request" type="java.util.List"/>
    <c:forEach items="${mealsTo}" var="mealTo">
        <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
        <c:choose>
            <c:when test="${mealTo.excess == true}">
                <c:set var="color" value="color:#ff0000"/>
            </c:when>
            <c:otherwise>
                <c:set var="color" value="color:#008000"/>
            </c:otherwise>
        </c:choose>
        <fmt:parseDate value="${mealTo.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
        <tr style=${color}>
            <td><fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}"/></td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>