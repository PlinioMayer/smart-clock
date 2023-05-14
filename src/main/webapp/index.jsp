<%@page
        contentType="text/html;charset=UTF-8"
        pageEncoding="UTF-8"
        import="com.smartclock.Horario, java.util.List"
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Smart Clock</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>

    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/img/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
</head>
<body>
<header id="header" class="z-2 position-fixed w-100 shadow d-flex align-items-center p-4">
    <button id="menu-button" class="rounded-circle p-1 border-0 ripple" type="button">
        <img class="w-100" src="${pageContext.request.contextPath}/static/img/menu-icon.svg"/>
    </button>

    <img id="logo-img" src="${pageContext.request.contextPath}/static/img/logo.png" class="ms-2"/>

    <span id="logo-name" class="ms-3">Smart Clock</span>
</header>

<nav id="menu" class="z-1 position-fixed bottom-0 top-0 shadow">
    <span class="ms-3">Navegação</span>
    <hr />

    <a href="${pageContext.request.contextPath}" class="menu-button d-block p-3">
        Meus horários
    </a>
</nav>

<div class="d-flex">
    <div id="menu-helper" class="flex-shrink-0 flex-grow-0"></div>

    <div id="main" class="d-flex justify-content-center w-100">
        <form action="${pageContext.request.contextPath}/" method="post" id="tables-wrapper">
            <ul id="table-selector" class="nav nav-tabs d-flex">
                <li class="nav-item flex-grow-1">
                    <button class="nav-link active" type="button" data-bs-toggle="tab"
                            data-bs-target="#horarios-trabalho">Horários de trabalho
                    </button>
                </li>

                <li class="nav-item flex-grow-1">
                    <button class="nav-link" type="button" data-bs-toggle="tab" data-bs-target="#marcacoes-feitas">
                        Marcações feitas
                    </button>
                </li>

                <li class="nav-item flex-grow-1">
                    <button class="nav-link" type="button" data-bs-toggle="tab" data-bs-target="#atrasos">Atrasos
                    </button>
                </li>

                <li class="nav-item flex-grow-1">
                    <button class="nav-link" type="button" data-bs-toggle="tab" data-bs-target="#horas-extras">Hora
                        extra
                    </button>
                </li>
            </ul>

            <div class="tab-content">
                <div class="tab-pane fade show active" id="horarios-trabalho">
                    <jsp:include page="components/editable-table.jsp">
                        <jsp:param name="table" value="horariosTrabalho"/>
                    </jsp:include>
                </div>

                <div class="tab-pane fade" id="marcacoes-feitas">
                    <jsp:include page="components/editable-table.jsp">
                        <jsp:param name="table" value="marcacoesFeitas"/>
                    </jsp:include>
                </div>

                <div class="tab-pane fade" id="atrasos">
                    <jsp:include page="components/table.jsp">
                        <jsp:param name="table" value="atrasos"/>
                    </jsp:include>
                </div>
                <div class="tab-pane fade" id="horas-extras">
                    <jsp:include page="components/table.jsp">
                        <jsp:param name="table" value="horasExtra"/>
                    </jsp:include>
                </div>
            </div>

            <div class="d-flex justify-content-between">
                <button type="submit" form="reset-form" class="btn btn-secondary">Limpar</button>
                <button type="submit" class="btn btn-primary">Salvar</button>
            </div>

        </form>
    </div>
</div>

<form id="reset-form" action="${pageContext.request.contextPath}/" method="post">
    <% List<Horario> horariosTrabalho = (List<Horario>) request.getAttribute("horariosTrabalho"); %>
    <% for (Horario horario : horariosTrabalho) { %>
    <input name="horariosTrabalho.primeiroHorario" type="hidden" value="<%= horario.getPrimeiroHorario() %>" />
    <input name="horariosTrabalho.segundoHorario" type="hidden" value="<%= horario.getSegundoHorario() %>" />
    <% } %>

    <% List<Horario> marcacoesFeitas = (List<Horario>) request.getAttribute("marcacoesFeitas"); %>
    <% for (Horario horario : marcacoesFeitas) { %>
    <input name="marcacoesFeitas.primeiroHorario" type="hidden" value="<%= horario.getPrimeiroHorario() %>" />
    <input name="marcacoesFeitas.segundoHorario" type="hidden" value="<%= horario.getSegundoHorario() %>" />
    <% } %>
</form>

<script src="${pageContext.request.contextPath}/static/js/time-util.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery-3.6.4.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/editable-table.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap-util.js"></script>
<script src="${pageContext.request.contextPath}/static/js/ripple.js"></script>
<script src="${pageContext.request.contextPath}/static/js/menu.js"></script>
</body>
</html>