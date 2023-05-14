<%@ page
        contentType="text/html;charset=UTF-8"
        pageEncoding="UTF-8"
        import="com.smartclock.Horario, java.util.List"
%>
<% String table = request.getParameter("table"); %>
<% List<Horario> list = (List<Horario>) request.getAttribute(table); %>
<% if (!list.isEmpty()) { %>
<div class="table-responsive">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Entrada</th>
            <th>Saída</th>
        </tr>
        </thead>

        <tbody>
        <% for (Horario horario : list) { %>
        <tr>
            <td class="w-50"><%= horario.getPrimeiroHorario() %>
            </td>
            <td class="w-50"><%= horario.getSegundoHorario() %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
<% } else { %>
<div class="p-5 text-center empty-message">
    Nenhum horário cadastrado
</div>
<% } %>
