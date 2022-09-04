<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Форум job4j</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
                    <c:if test="${user.name != 'guest'}">
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
                <div class="mb-4">
                    <text><c:out value="${post.name}"/></text>
                </div>
                <div class="mb-4">
                    <text><c:out value="${post.description}"/></text>
                </div>
        </div>
    </div>

    <div class="container mt-5 mb-5">
        <div class="row height d-flex justify-content-center align-items-center">
            <div class="col-md-7">
                <div class="card">
                    <div class="p-3">
                        <h6>Comments</h6>
                    </div>

                    <form action="<c:url value='/comment'/>" method="POST">
                        <div class="mt-3 d-flex flex-row align-items-center p-3 form-color">
                            <input type="hidden" name="post_id" value="${post.id}">
                            <input type="text" class="form-control" placeholder="Enter your comment..." name="body" required>
                        </div>

                        <div onclick="submit_form()"></div>
                    </form>

                    <c:forEach items="${post.comments}" var="comment">
                    <div class="mt-2">
                        <div class="d-flex flex-row p-3">
                            <div class="w-100">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="d-flex flex-row align-items-center">
                                        <h6 class="mr-2"><c:out value="${comment.author.name}"/></h6>
                                    </div>
                                    <small><c:out value="${comment.createdTimeAgo}"/></small>
                                </div>
                                <p class="text-justify comment-text mb-0"><c:out value="${comment.body}"/></p>
                            </div>
                        </div>
                    </div>
                    </c:forEach>

                </div>
            </div>
        </div>
    </div>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>
    function submit_form(){
        var form = document.getElementById("my_form");
        form.submit();
    }
</script>
</body>
</html>

