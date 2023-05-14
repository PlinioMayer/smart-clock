class Errors {
    primeiroHorario = null;
    segundoHorario = null;

    get hasErrors() {
        return this.primeiroHorario || this.segundoHorario;
    }
}

class EditableTable {
    tableName = null;
    editableTable = null;
    table = null;
    tbody = null;
    emptyMessage = null;
    addModalButton = null;
    bootStrapAddModal = null;
    addModal = null;
    addHorarioButton = null;
    cancelHorarioButton = null;
    addPrimeiroHorarioInput = null;
    addSegundoHorarioInput = null;
    originalInputs = null;

    constructor(editableTable) {
        this.editableTable = editableTable;
        this.table = editableTable.find('table');
        this.tableName = this.table.attr('data-table');
        this.tbody = this.table.find('tbody');
        this.emptyMessage = this.editableTable.find('.empty-message');
        this.addModalButton = this.editableTable.find('.add-modal-button');
        this.bootStrapAddModal = new bootstrap.Modal(`#${this.tableName}-add-modal`);
        this.addModal = $(`#${this.tableName}-add-modal`);
        this.addHorarioButton = this.addModal.find('.add-horario');
        this.cancelHorarioButton = this.addModal.find('.cancel-horario');

        const addInputs = this.addModal.find('input');

        this.addPrimeiroHorarioInput = addInputs.eq(0);
        this.addSegundoHorarioInput = addInputs.eq(1);
        this.originalInputs = $(`#original-${this.tableName}`).children();

        this.checkEmptyMessage();
        this.checkAddModalButton();
        this.resetAddHorario();

        this.editableTable.find('input').on('focus', function () {
            EditableTable.validateInput($(this));
        });
        this.cancelHorarioButton.on('click', this.resetAddHorario.bind(this));
        this.addHorarioButton.on('click', this.addRow.bind(this));

        const that = this;

        this.tbody.find('.clear-button').on('click', function () {
            that.removeRow($(this).parent().parent());
        });

        this.tbody.find('.editable-table-value').on('click', EditableTable.clickEditableTableValue);

        this.tbody.find('.editable-table-check').on('click', function () {
            that.clickEditableTableCheck($(this).parent());
        });

        this.tbody.find('.editable-table-input-group input').on('keydown', function (event) {
            if (event.keyCode === 13) {
                event.preventDefault();
                that.clickEditableTableCheck($(this).parent());
            }
        });

        addInputs.on('keydown', function (event) {
            if (event.keyCode === 13) {
                event.preventDefault();
                that.addHorarioButton.click();
            }
        })
    }

    static invalidateInput(input, msg) {
        const feedback = input.siblings('.invalid-feedback')
        input.addClass('is-invalid');
        feedback.html(msg);
    }

    static validateInput(input) {
        const feedback = input.siblings('.invalid-feedback')
        input.removeClass('is-invalid');
        feedback.html(null);
    }

    static toggleEditableTable(editableTableValue, editableTableInputGroup) {
        if (editableTableValue.css('display') === 'none') {
            editableTableValue.css('display', 'inline-flex');
            editableTableInputGroup.css('display', 'none');
        } else {
            editableTableValue.css('display', 'none');
            editableTableInputGroup.css('display', '');
        }
    }

    static clickEditableTableValue() {
        const editableTableValue = $(this);
        const editableTableInputGroup = editableTableValue.siblings();

        EditableTable.toggleEditableTable(editableTableValue, editableTableInputGroup);
    }

    clickEditableTableCheck(editableTableInputGroup) {
        const tr = editableTableInputGroup.parent().parent();
        const currentInput = editableTableInputGroup.children('input');
        const editableTableValue = editableTableInputGroup.siblings();
        const editableTableValueSpan = editableTableValue.children('span');
        const inputs = editableTableInputGroup.parent().parent().find('input');
        const primeiroHorarioInput = inputs.eq(0);
        const segundoHorarioInput = inputs.eq(1);

        const errors = this.validateHorario(primeiroHorarioInput.val(), segundoHorarioInput.val());

        const originalValues = this.getOriginalFormValue(tr.index());

        if (currentInput.attr('name').includes('primeiroHorario')) {
            if (errors.primeiroHorario) {
                EditableTable.invalidateInput(primeiroHorarioInput, errors.primeiroHorario);
            } else {
                EditableTable.validateInput(primeiroHorarioInput);
                editableTableValueSpan.html(primeiroHorarioInput.val());
                EditableTable.toggleEditableTable(editableTableValue, editableTableInputGroup);

                if (originalValues) {
                    if (originalValues.primeiroHorario !== primeiroHorarioInput.val()) {
                        editableTableValue.addClass('text-success');
                    } else {
                        editableTableValue.removeClass('text-success');
                    }
                }
            }
        } else {
            if (errors.segundoHorario) {
                EditableTable.invalidateInput(segundoHorarioInput, errors.segundoHorario)
            } else {
                EditableTable.validateInput(segundoHorarioInput);
                editableTableValueSpan.html(segundoHorarioInput.val());
                EditableTable.toggleEditableTable(editableTableValue, editableTableInputGroup);

                if (originalValues) {
                    if (originalValues.segundoHorario !== segundoHorarioInput.val()) {
                        editableTableValue.addClass('text-success');
                    } else {
                        editableTableValue.removeClass('text-success');
                    }
                }
            }
        }
    }

    resetAddHorario() {
        EditableTable.validateInput(this.addPrimeiroHorarioInput);
        EditableTable.validateInput(this.addSegundoHorarioInput);
        this.addPrimeiroHorarioInput.val(null);
        this.addSegundoHorarioInput.val(null);
    }

    checkEmptyMessage() {
        if (!this.tbody.children().length) {
            this.table.css('display', 'none');
            this.emptyMessage.css('display', 'block');
        } else {
            this.table.css('display', 'block');
            this.emptyMessage.css('display', 'none');
        }
    }

    getTableValues() {
        const values = [];

        this.tbody.find('tr').each(function () {
            const inputs = $(this).find('input');
            const primeiroHorarioInput = inputs.eq(0);
            const segundoHorarioInput = inputs.eq(1);

            if (primeiroHorarioInput.parent().css('display') === 'none' && segundoHorarioInput.parent().css('display') === 'none') {
                values.push({
                    primeiroHorario: primeiroHorarioInput.val(),
                    segundoHorario: segundoHorarioInput.val()
                });
            }
        });

        return values;
    }

    validateHorario(primeiroHorarioValue, segundoHorarioValue) {
        const regex = /\d\d:\d\d/;
        const errors = new Errors();
        const horarioInvalido = 'Horário inválido';

        if (!primeiroHorarioValue.match(regex)) {
            errors.primeiroHorario = horarioInvalido;
        }

        if (!segundoHorarioValue.match(regex)) {
            errors.segundoHorario = horarioInvalido;
        }

        if (errors.hasErrors) {
            return errors;
        }

        if (primeiroHorarioValue === segundoHorarioValue) {
            errors.primeiroHorario = 'Entrada não pode ser igual a saída.';
            errors.segundoHorario = 'Saída não pode ser igual a entrada.';
            return errors;
        }

        const oldValues = this.getTableValues();

        for (const oldValue of oldValues) {
            let error = false;

            if (TimeUtil.between(primeiroHorarioValue, [oldValue.primeiroHorario, oldValue.segundoHorario])) {
                errors.primeiroHorario = `Entrada conflita com o horário ${oldValue.primeiroHorario} - ${oldValue.segundoHorario}`;
                error = true;
            }

            if (TimeUtil.between(segundoHorarioValue, [oldValue.primeiroHorario, oldValue.segundoHorario])) {
                errors.segundoHorario = `Saída conflita com o horário ${oldValue.primeiroHorario} - ${oldValue.segundoHorario}`;
                error = true;
            }

            if (error) {
                break;
            }

            if (TimeUtil.between(oldValue.primeiroHorario, [primeiroHorarioValue, segundoHorarioValue]) ||  TimeUtil.between(oldValue.segundoHorario, [primeiroHorarioValue, segundoHorarioValue])) {
                errors.primeiroHorario = `Entrada conflita com o horário ${oldValue.primeiroHorario} - ${oldValue.segundoHorario}`;
                errors.segundoHorario = `Saída conflita com o horário ${oldValue.primeiroHorario} - ${oldValue.segundoHorario}`;
                break;
            }
        }

        return errors;
    }

    checkAddModalButton() {
        const max = parseInt(this.addModalButton.attr('data-max'));

        if (max) {
            if (this.tbody.children().length < max) {
                this.addModalButton.css('display', 'flex');
            } else {
                this.addModalButton.css('display', 'none');
            }
        }
    }

    removeRow(tr) {
        this.originalInputs.eq(tr.index()).remove();
        this.originalInputs = $(`#original-${this.tableName}`).children();
        tr.remove();
        this.checkEmptyMessage();
        this.checkAddModalButton();
    }

    addRow() {
        const errors = this.validateHorario(this.addPrimeiroHorarioInput.val(), this.addSegundoHorarioInput.val());


        if (errors.primeiroHorario) {
            EditableTable.invalidateInput(this.addPrimeiroHorarioInput, errors.primeiroHorario);
        }

        if (errors.segundoHorario) {
            EditableTable.invalidateInput(this.addSegundoHorarioInput, errors.segundoHorario);
        }

        if (errors.hasErrors) {
            return;
        }

        EditableTable.validateInput(this.addPrimeiroHorarioInput);
        EditableTable.validateInput(this.addSegundoHorarioInput);

        const contextPath = location.pathname.endsWith('/') ? location.pathname.substring(0, location.pathname.length - 1) : location.pathname;

        this.tbody.append(`
            <tr>
                <td class="w-50">
                    <div class="editable-table-value align-items-center text-success" style="display: inline-flex;">
                        <span>${this.addPrimeiroHorarioInput.val()}</span>
                        <button class="editable-table-edit border-0 ripple p-1 d-inline-flex rounded-circle justify-content-center align-items-center ms-1"
                                type="button">
                            <img src="${contextPath}/static/img/edit-icon.svg" class="w-100"/>
                        </button>
                    </div>
    
                    <div style="display: none;" class="input-group-sm input-group editable-table-input-group">
                        <input type="time" name="${this.tableName}.primeiroHorario" value="${this.addPrimeiroHorarioInput.val()}" class="form-control editable-table-input"/>
                        <button type="button" class="editable-table-check ripple input-group-text border-0 p-1 justify-content-center align-items-center">
                            <img src="${contextPath}/static/img/check-icon.svg" class="w-100"/>
                        </button>
                        <span class="invalid-feedback"></span>
                    </div>
                </td>
                
                <td class="w-50">
                    <div class="editable-table-value align-items-center text-success" style="display: inline-flex;">
                        <span>${this.addSegundoHorarioInput.val()}</span>
                        <button class="editable-table-edit border-0 ripple p-1 d-inline-flex rounded-circle justify-content-center align-items-center ms-1"
                                type="button">
                            <img src="${contextPath}/static/img/edit-icon.svg" class="w-100"/>
                        </button>
                    </div>
    
                    <div style="display: none;" class="input-group-sm input-group editable-table-input-group">
                        <input type="time" name="${this.tableName}.segundoHorario" value="${this.addSegundoHorarioInput.val()}" class="form-control editable-table-input"/>
                        <button type="button" class="editable-table-check ripple input-group-text border-0 p-1 justify-content-center align-items-center">
                            <img src="${contextPath}/static/img/check-icon.svg" class="w-100"/>
                        </button>
                        <span class="invalid-feedback"></span>
                    </div>
                </td>
                <td>
                    <button class="clear-button rounded-circle p-1 border-0 ripple d-flex justify-content-center align-items-center"
                            type="button">
                        <img class="w-100" src="${contextPath}/static/img/close-icon.svg"/>
                    </button>
                </td>
            </tr>
        `);

        setTimeout(() => {

        })
        const lastTr = this.tbody.find('tr').last();
        const that = this;

        lastTr.find('.clear-button').on('click', function () {
            that.removeRow($(this).parent().parent());
        });

        lastTr.find('.editable-table-value').on('click', EditableTable.clickEditableTableValue);

        lastTr.find('.editable-table-check').on('click', function () {
            that.clickEditableTableCheck($(this).parent());
        });

        lastTr.find('input').on('keydown', function (event) {
            if (event.keyCode === 13) {
                event.preventDefault();
                that.clickEditableTableCheck($(this).parent());
            }
        });

        this.checkEmptyMessage();
        this.resetAddHorario();
        this.checkAddModalButton();
        this.bootStrapAddModal.hide();
    }

    getOriginalFormValue(index) {
        if (this.originalInputs[index]) {
            const inputs = this.originalInputs.eq(index).find('input');
            const primeiroHorarioInput = inputs.eq(0);
            const segundoHorarioInput = inputs.eq(1);

            return {
                primeiroHorario: primeiroHorarioInput.val(),
                segundoHorario: segundoHorarioInput.val()
            };
        }

        return null;
    }
}

const editableTables = {
    horariosTrabalho: null,
    marcacoesFeitas: null
}

$('.editable-table').each(function () {
    const editableTable = $(this);

    editableTables[editableTable.attr('data-table')] = new EditableTable(editableTable);
});