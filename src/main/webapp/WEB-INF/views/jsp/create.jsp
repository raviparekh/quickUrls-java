
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="forms" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Create shorten URL</title>
</head>
<body>
<forms:form modelAttribute="urlForm" method="POST">
    <table>
        <tr>
            <td>
                Enter URL to shorten:
            </td>
            <td>
                <forms:input path="fullURL"/>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="Submit">
            </td>
        </tr>
    </table>
</forms:form>
</body>
</html>
