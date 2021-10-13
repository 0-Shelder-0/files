<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Files</title>
    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
        rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
        crossorigin="anonymous"
    >
    <link rel="stylesheet" href="../styles/default-styles.css">
    <script src="../scripts/filesScript.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <h2 class="col">${appTime}</h2>
        <button type="submit" class="col col-md-1 btn btn-dark float-end" onclick="logout()">Exit</button>
    </div>
    <br/>
    <h1>${path}</h1>
    <hr/>
    <div>
        <img src="../images/back.png" class="small-icon" alt="back"/>
        <a class="file-name" onclick="moveBack()">Вверх</a>
    </div>
    <c:if test="${files.size() != 0}">
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
                        <img
                            src="https://i.pinimg.com/originals/d0/78/22/d078228e50c848f289e39872dcadf49d.png"
                            class="file-icon"
                            alt="file-icon"
                        />
                        <a class="file-name" onclick="getFile(event)">${file.name}</a>
                    </c:if>
                    <c:if test="${!file.isFile}">
                        <img
                            src="https://www.kindpng.com/picc/m/200-2007626_file-icon-hd-png-download.png"
                            class="directory-icon"
                            alt="directory-icon"
                        />
                        <a class="file-name" onclick="setPath(event)">${file.name}</a>
                    </c:if>
                </td>
                <td class="text-center">${file.size}</td>
                <td class="text-center">${file.createdDate}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</div>
</body>
</html>
