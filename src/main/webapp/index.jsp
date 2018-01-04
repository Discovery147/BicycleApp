<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <body>
        <jsp:forward page="${pageContext.request.contextPath}/InvokerServlet?command=fill_main"/>
    </body>
</html>