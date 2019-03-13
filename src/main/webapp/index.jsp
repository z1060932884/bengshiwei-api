<%--
  Created by IntelliJ IDEA.
  User: JamesZhang
  Date: 2018/5/4
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  $END$
  <form action="${pageContext.request.contextPath}/api/files/upload" method="post" enctype="multipart/form-data">
    <p>
      文件 :<input type="file" name="file"/><br />
      用户名: <input type="text" name="username"/><br />
    </p>
    <input type="submit" value="上传" />
  </form>
  </body>
</html>
