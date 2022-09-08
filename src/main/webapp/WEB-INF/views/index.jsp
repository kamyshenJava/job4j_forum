<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <title>Форум job4j</title>
</head>
<body>
<div class="container mt-3">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container">
            <a class="navbar-brand me-2" href="<c:url value="/index"/>">
                Форум job4j
            </a>
            <div class="d-flex justify-content-end" id="navbarButtonsExample">
                <div class="d-flex align-items-center">
                    <c:if test="${user == 'anonymousUser'}">
                        <a class="btn btn-link text-decoration-none px-3 me-2" href="<c:url value="/login"/>">
                            Login
                        </a>
                    </c:if>
                    <c:if test="${user == 'anonymousUser'}">
                        <a class="btn btn-primary me-3" href="<c:url value="/signup"/>">
                            Signup
                        </a>
                    </c:if>
                    <c:if test="${user != 'anonymousUser'}">
                        <a class="nav-link" href="<c:url value="/logout"/>">
                            <span>${user}</span>
                            <span>| Log out</span>
                        </a>
                    </c:if>
                </div>
            </div>
        </div>
    </nav>
    <div class="row">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Description</th>
                <th scope="col">Author</th>
                <th scope="col">Edit</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${posts}" var="post">
                <tr>
                    <td>
                        <a href="<c:url value="/post?id=${post.id}"/>">
                        <c:out value="${post.name}"/>
                        </a>
                    </td>
                    <td><c:out value="${post.description}"/></td>
                    <td><c:out value="${post.author.username}"/></td>
                    <c:if test="${post.author.username == user}">
                    <td><a class="btn btn-outline-success btn-sm mx-auto" href="<c:url value="/edit?id=${post.id}"/>">
                        Edit
                    </a></td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <a class="btn btn-primary ms-3 mt-3" href="<c:url value="/create"/>">Add a new post</a>
</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>