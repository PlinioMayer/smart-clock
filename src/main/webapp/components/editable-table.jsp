<%@ page
        contentType="text/html;charset=UTF-8"
        pageEncoding="UTF-8"
        import="com.smartclock.Horario, java.util.List"
%>
<% String table = request.getParameter("table"); %>
<div class="editable-table">
    <div class="table-responsive">
        <table class="table table-striped" data-table="<%= table %>">
            <thead>
            <tr>
                <th>Entrada</th>
                <th>Saída</th>
                <th>Ações</th>
            </tr>
            </thead>

            <tbody>
            <% List<Horario> list = (List<Horario>) request.getAttribute(table); %>
            <% for (Horario horario : list) { %>
            <tr>
                <td class="w-50">
                    <div class="editable-table-value align-items-center" style="display: inline-flex;">
                        <span><%= horario.getPrimeiroHorario() %></span>
                        <button class="editable-table-edit border-0 ripple p-1 d-inline-flex rounded-circle justify-content-center align-items-center ms-1"
                                type="button">
                            <img src="${pageContext.request.contextPath}/static/img/edit-icon.svg" class="w-100"/>
                        </button>
                    </div>

                    <div style="display: none;" class="input-group-sm input-group editable-table-input-group">
                        <input name="<%= table %>.primeiroHorario" value="<%= horario.getPrimeiroHorario() %>"
                               type="time" class="form-control editable-table-input"/>
                        <button type="button"
                                class="editable-table-check ripple input-group-text border-0 p-1 justify-content-center align-items-center">
                            <img src="${pageContext.request.contextPath}/static/img/check-icon.svg" class="w-100"/>
                        </button>
                        <span class="invalid-feedback"></span>
                    </div>
                </td>
                <td class="w-50">
                    <div class="editable-table-value align-items-center" style="display: inline-flex;">
                        <span><%= horario.getSegundoHorario() %></span>
                        <button class="editable-table-edit border-0 ripple p-1 d-inline-flex rounded-circle justify-content-center align-items-center ms-1"
                                type="button">
                            <img src="${pageContext.request.contextPath}/static/img/edit-icon.svg" class="w-100"/>
                        </button>
                    </div>

                    <div style="display: none;" class="input-group-sm input-group editable-table-input-group">
                        <input name="<%= table %>.segundoHorario" type="time" value="<%= horario.getSegundoHorario() %>"
                               class="form-control editable-table-input"/>
                        <button type="button"
                                class="editable-table-check ripple input-group-text border-0 p-1 justify-content-center align-items-center">
                            <img src="${pageContext.request.contextPath}/static/img/check-icon.svg" class="w-100"/>
                        </button>
                        <span class="invalid-feedback"></span>
                    </div>
                </td>
                <td>
                    <button class="clear-button rounded-circle p-1 border-0 ripple d-flex justify-content-center align-items-center"
                            type="button">
                        <img class="w-100" src="${pageContext.request.contextPath}/static/img/close-icon.svg"/>
                    </button>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <div class="p-5 text-center empty-message" style="display: none;">
        Nenhum horário cadastrado
    </div>

    <div class="justify-content-center add-modal-button" data-max="<%= table.equals("horariosTrabalho") ? 3 : 0 %>"
         style="display: flex;">
        <button data-bs-toggle="modal"
                data-bs-target="#<%= table %>-add-modal"
                class="add-button rounded-circle p-1 border-0 ripple d-flex justify-content-center align-items-center"
                type="button">
            <img class="w-100" src="${pageContext.request.contextPath}/static/img/add-icon.svg"/>
        </button>
    </div>

    <div class="modal fade" id="<%= table %>-add-modal">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5">Adicionar horário</h1>
                    <button type="button" class="btn-close cancel-horario" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-lg-6">
                                <label class="form-label">Entrada</label>
                                <div class="input-group">
                                    <input type="time" class="form-control"/>
                                    <span class="invalid-feedback"></span>
                                </div>
                            </div>

                            <div class="col-lg-6">
                                <label class="form-label">Saída</label>
                                <div class="input-group">
                                    <input type="time" class="form-control"/>
                                    <span class="invalid-feedback"></span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer d-flex justify-content-between">
                    <button type="button" class="btn btn-secondary cancel-horario" data-bs-dismiss="modal">Cancelar</button>
                    <button type="button" class="btn btn-primary add-horario">Adicionar</button>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="original-<%= table %>" class="d-none">
    <% for (Horario horario : list) { %>
    <div>
        <input type="hidden" value="<%= horario.getPrimeiroHorario() %>"/>
        <input type="hidden" value="<%= horario.getSegundoHorario() %>"/>
    </div>
    <% } %>
</div>
