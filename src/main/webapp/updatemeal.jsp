<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Update meal</h2>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="action" value="update"/>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal"/>
    <input type="hidden" name="meal" value=${requestScope.meal}>
    <input type="hidden" name="id" value=${meal.id}>

    <dl> DataTime <input type="datetime-local" name="dateTime" value=${meal.dateTime}></dl>
    <dl> Description <input type="text" name="description" size="55" value=${meal.description}></dl>
    <dl> Calories <input type="number" name="calories" size="55" value=${meal.calories}></dl>
    <button type="submit">Save</button>
    <button onclick="window.history.go(-1); return false;">Cancel</button>
</form>
</body>
</html>