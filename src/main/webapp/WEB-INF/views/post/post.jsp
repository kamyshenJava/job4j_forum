<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Форум job4j</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
</head>
<body>

<div class="container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container">
            <a class="navbar-brand me-2" href="<c:url value="/index"/>">
                Форум job4j
            </a>
            <div class="d-flex justify-content-end" id="navbarButtonsExample">
                <div class="d-flex align-items-center">
                    <c:if test="${user.name == 'guest'}">
                        <a class="btn btn-link text-decoration-none px-3 me-2" href="<c:url value="/login"/>">
                            Login
                        </a>
                    </c:if>
                    <c:if test="${user.name == 'guest'}">
                        <a class="btn btn-primary me-3" href="<c:url value="/signup"/>">
                            Signup
                        </a>
                    </c:if>
                    <c:if test="user.name != 'guest'">
                        <a class="nav-link" href="<c:url value="/logout"/>">
                            <span>${user.name}</span>
                            <span>| Log out</span>
                        </a>
                    </c:if>
                </div>
            </div>
        </div>
    </nav>

    <div class="col-md-6 col-lg-4 offset-lg-4 offset-md-3 mt-5">
        <div class="bg-light p-5 border shadow">
            <h4 class="text-center mb-3">THE POST</h4>
                <input type="hidden" name="id" value="${post.id}">
                <div class="mb-4">
                    <input type="text" class="form-control" value="${post.name}" name="name" required>
                </div>
                <div class="mb-4">
                    <input type="text" class="form-control" value="${post.description}" name="description" required>
                </div>
        </div>
    </div>
</div>
</body>
</html>

