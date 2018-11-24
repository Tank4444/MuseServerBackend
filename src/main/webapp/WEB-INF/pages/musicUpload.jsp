<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Upload Music</title>
</head>
<body>
<form method="POST" action="/api/music/add" enctype="multipart/form-data">
    <table>
        <tr>
            <td><label>Select a music to upload</label></td>
            <td><input type="file" name="music" accept="audio/*"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit" /></td>
        </tr>
    </table>
</form>
</body>
</html>