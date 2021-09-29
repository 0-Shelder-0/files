<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="styles/default-styles.css">
    <script src="scripts/main.js"></script>
</head>
<body>
<h2>${appTime}</h2>
<br/>
<h1>${path}</h1>
<hr/>
<div>
    <img src="images/back.png" class="small-icon" alt="back"/>
    <a class="file-name" onclick="moveBack()">Вверх</a>
</div>
<table>
    <colgroup>
        <col class="col-width">
        <col class="col-width">
        <col class="col-width">
    </colgroup>
    <tr>
        <th>Файл</th>
        <th>Размер</th>
        <th>Дата</th>
    </tr>
    <c:forEach var="file" items="${files}">
        <tr>
            <td>
                <c:if test="${file.isFile}">
                    <img src="images/file.png" class="icon" alt="file-icon"/>
                </c:if>
                <c:if test="${!file.isFile}">
                    <img src="images/directory.png" class="icon" alt="directory-icon"/>
                </c:if>
                <a class="file-name" onclick="setPath(event)">${file.name}</a>
            </td>
            <td class="text-center">${file.size}</td>
            <td class="text-center">${file.createdDate}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
