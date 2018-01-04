<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <title>Error page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error/font-awesome.min.css" media="all" />
    <link href="https://fonts.googleapis.com/css?family=Quicksand:300,400,500,700" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error/style.css?version=1" type="text/css">
</head>
<body>
        <h1>Random <span>Error</span> Page</h1>
        <div class="w3layout-agileits">
            <h4>${errorStatus}</h4>
            <p>Reason:</p><h4> ${errorInfo}</h4>
            <p>Something went wrong</p>
            <p>Go back to your home page or you may also refresh the page</p>
            <a href="${pageContext.request.contextPath}/index.jsp">Return Home</a>
        </div>
</body>
</html>
