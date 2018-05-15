(function () {

    $(".contracts-table").on("click", "tr.contract-row", function () {
        document.location.href = "/customerPanel/contract/" + $(this).find("td:first").html();
    });

    function userPanel() {
        var newStatus;
        $('#unBlockUserButton').click(function () {
            newStatus = 'ACTIVE';
            globalSetNewStatus("user", newStatus, user_id);
        });
        $('#deactivateUserButton').click(function () {
            newStatus = 'INACTIVE';
            globalSetNewStatus("user", newStatus, user_id);
        });

        function updateUserInfo() {
            var input, td, button, editing, canselButton;
            var oldRow, oldValue = "";
            $(".userEditableTable").on("click", "tbody tr", function () {
                if ($(this).hasClass('editable')) {
                    oldValue = $(this).find("td:eq(1)").remove().clone();
                    input = document.createElement('input');
                    button = document.createElement('button');
                    canselButton = document.createElement('button');
                    input.type = 'text';
                    input.value = oldValue.html();
                    input.size = 10;
                    button.innerHTML = 'OK';
                    canselButton.innerHTML = 'X'
                    $(this).append(input, button, canselButton);
                    input.focus();
                    input.selectionStart = input.value.length;
                    $(this).removeClass('editable');
                    $(this).addClass('editing');
                }
            });
            $(document).on("focusout", ".userEditableTable tr.editing", function (event) {
                if (button === event.relatedTarget) {
                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    var editing = $(".userEditableTable tr.editing");
                    var value = editing.find("input").val();
                    $.ajax({
                        beforeSend: function (xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        headers: {
                            'Content-Type': 'application/json',
                            'Accept': 'text/html; charset=utf-8'
                        },
                        type: "POST",
                        url: "/user/editUser",
                        // The key needs to match your method's input parameter (case-sensitive).
                        data: JSON.stringify({
                            dataInstance: editing.find("td:first").html(),
                            value: value,
                            userId: user_id
                        })
                    }).done(function (msg) {
                        alert(msg);
                        oldValue.html(value);
                    }).fail(function (jqXHR, textStatus) {
                        alert("Request failed: " + textStatus);
                    });
                }
                $(this).removeClass('editing').addClass('editable');
                $(this).find("input").remove();
                $(this).find("button").remove();
                $(this).append(oldValue);
            });

        }

        updateUserInfo();
    }

    function contractPanel() {
        $("#contractCurrentOptions").on("click", "tr.move-row", function () {
            if ($(this).hasClass('add-tariff-table-selected')) {
                $(this).removeClass('add-tariff-table-selected');
            } else {
                $(this).addClass('add-tariff-table-selected');
            }
        });
        $("#contractAvailableOptions").on("click", "tr.move-row", function () {
            if ($(this).hasClass('add-tariff-table-selected')) {
                $(this).removeClass('add-tariff-table-selected');
            } else {
                $(this).addClass('add-tariff-table-selected');
            }
        });

        $('#switchTariff').on('click', function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            var newTariffId = $('#addContractTariffs tr.add-tariff-table-selected').find('td:first').html();
            $.ajax({
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'text/html; charset=utf-8'
                },
                type: "POST",
                url: "/customerPanel/contract/switchTariff",
                // The key needs to match your method's input parameter (case-sensitive).
                data: JSON.stringify({
                    tariffId: newTariffId,
                    contractId: contract_id
                })
            }).done(function (msg) {
                alert(msg);
                $('#tariffTable').find('tr:eq(1)').find('td:eq(1)').html(newTariffId);
            }).fail(function (jqXHR, textStatus) {
                alert(jqXHR.responseText);
            });
        });

        $('#contractAddOption').on('click', function () {
            // OPTIONS RULES
            var tr = $("#contractAvailableOptions tr.add-tariff-table-selected").clone();
            var table = $('#parseTable');
            for (var i = 0; i < tr.length; i++) {
                table.append(tr[i]);
            }
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'text/html; charset=utf-8'
                },
                type: "POST",
                url: "/customerPanel/contract/addOptions",
                // The key needs to match your method's input parameter (case-sensitive).
                data: JSON.stringify({tariffOptionDtoList: table.tableToJSON(), contractId: contract_id})
            }).done(function (msg) {
                alert(msg);
            }).fail(function (jqXHR, textStatus) {
                alert(jqXHR.responseText);
            });
            $('#parseTable tr.move-row').remove();
        });

        $('#contractDelOption').on('click', function () {
            // OPTIONS RULES

            var tr = $("#contractCurrentOptions tr.add-tariff-table-selected").clone();
            var table = $('#parseTable');
            for (var i = 0; i < tr.length; i++) {
                table.append(tr[i]);
            }
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'text/html; charset=utf-8'
                },
                type: "POST",
                url: "/customerPanel/contract/delOptions",
                // The key needs to match your method's input parameter (case-sensitive).
                data: JSON.stringify({tariffOptionDtoList: table.tableToJSON(), contractId: contract_id})
            }).done(function (msg) {
                alert(msg);
                var tr = $("#contractCurrentOptions tr.add-tariff-table-selected").remove();
            }).fail(function (jqXHR, textStatus) {
                alert("Request failed: " + textStatus);
            });
            $('#parseTable tr.move-row').remove();
        });


        var newStatus;
        $('#unBlockContractButton').click(function () {
            newStatus = 'ACTIVE';
            globalSetNewStatus("contract", newStatus, contract_id);
        });
        $('#deactivateContractButton').click(function () {
            newStatus = 'INACTIVE';
            globalSetNewStatus("contract", newStatus, contract_id);
        });
        $("#addContractTariffs").on("click", "tr.t-row", function () {
            if (!$(this).hasClass('add-tariff-table-selected')) {
                $(this).addClass('add-tariff-table-selected').siblings().removeClass('add-tariff-table-selected');
            }
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                headers: {
                    'Content-Type': 'text/html; charset=utf-8',
                },
                type: "POST",
                url: "/tariffOptions",
                // The key needs to match your method's input parameter (case-sensitive).
                data: $(this).find('td:first').html(),
                dataType: "json"
            }).done(function (json) {
                $("#addContractAvailableOptions tbody tr").remove();
                var tbl_body = document.createElement("tbody");
                var odd_even = false;
                $.each(json, function () {
                    var tbl_row = tbl_body.insertRow();
                    tbl_row.className = odd_even ? "odd" : "even";
                    $.each(this, function (k, v) {
                        var cell = tbl_row.insertCell();
                        cell.appendChild(document.createTextNode(v.toString()));
                    });
                    odd_even = !odd_even;
                });
                $("#addContractAvailableOptions").append(tbl_body);
            }).fail(function (jqXHR, textStatus) {
                alert("Request failed: " + textStatus);
            });
        });
    }

    function globalSetNewStatus(entity, status, entity_id) {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'text/html; charset=utf-8'
            },
            type: "POST",
            url: "/" + entity + "/setStatus",
            // The key needs to match your method's input parameter (case-sensitive).
            data: JSON.stringify({entityId: entity_id, entityStatus: status})
        }).done(function (msg) {
            alert(msg);
            location.reload();
        }).fail(function (jqXHR, textStatus) {
            alert("Request failed: " + jqXHR.responseText);
        });
    }


    userPanel();
    contractPanel();

})();