(function () {

    $("#prevPage").on("click", function () {
        if (pageNumber - 1 > 0) {
            document.location.href = "/adminPanel/all" + entities + "/" + (pageNumber - 1);
        }
    });

    $("#nextPage").on("click", function () {
        if (pageNumber + 1 <= pageCount) {
            document.location.href = "/adminPanel/all" + entities + "/" + (pageNumber + 1);
        }
    });

    $(".users-table").on("click", "tr.user-row", function () {
        document.location.href = "/adminPanel/user/" + $(this).find("td:first").html();
    });
    $(".contracts-table").on("click", "tr.contract-row", function () {
        document.location.href = "/adminPanel/contract/" + $(this).find("td:first").html();
    });
    $(".tariffs-table").on("click", "tr.tariff-row", function () {
        document.location.href = "/adminPanel/tariff/" + $(this).find("td:first").html();
    });
    $(".options-table").on("click", "tr.option-row", function () {
        document.location.href = "/adminPanel/option/" + $(this).find("td:first").html();
    });

    function tableSelectionRules() {
        for (var i = 0; i < arguments.length; i++) {
            arguments[i].on("click", "tr.move-row", function () {
                if ($(this).hasClass('add-tariff-table-selected')) {
                    $(this).removeClass('add-tariff-table-selected');
                } else {
                    $(this).addClass('add-tariff-table-selected');
                }
            });
        }
    }

    function adminPanel() {
        $("#searchUserByPhoneNumberForm").on("submit", function (e) {
            e.preventDefault();
            if ($("#searchUserByNumberInput").val() === "") {
                notify("", "Enter phone number.", "primary", "fas fa-search");
            } else {
                searchUserByNumber();
            }
        });

        function searchUserByNumber() {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            var phoneNumber = $('#searchUserByNumberInput').val();
            $.ajax({
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                headers: {
                    'Content-Type': 'application/json'
                },
                type: "POST",
                url: "/adminPanel/user/searchUserByNumber",
                // The key needs to match your method's input parameter (case-sensitive).
                data: JSON.stringify({phoneNumber: phoneNumber}),
                dataType: "json"
            }).done(function (msg) {
                updateUserTable(msg);
            }).fail(function (jqXHR, textStatus) {
                notify("", jqXHR.responseText, "primary", "fas fa-search");
            });
        }

        function updateUserTable(msg) {
            document.location.href = "/adminPanel/user/" + msg;
        }


        $('#addUserButton').click(function () {
            document.location.href = "/adminPanel/addUser"
        });
        $('#addContractButton').click(function () {
            document.location.href = "/adminPanel/addContract"
        });
        $('#addTariffButton').click(function () {
            document.location.href = "/adminPanel/addTariff"
        });
        $('#addOptionButton').click(function () {
            document.location.href = "/adminPanel/addOption"
        });
    }


    function userPanel() {
        $("#deleteUser").on("click", function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                headers: {
                    'Content-Type': 'text/html; charset=utf-8',
                    'Accept': 'text/html; charset=utf-8'
                },
                type: "POST",
                url: "/adminPanel/user/deleteUser",
                data: user_id.toString()
            }).done(function (msg) {
                notify("Leave page please:", msg, "success", "fas fa-thumbs-up");
            }).fail(function (jqXHR, textStatus) {
                notify("", jqXHR.responseText, "info");
            });
        });
        var newStatus;
        $('#addContractToUserButton').click(function () {
            document.location.href = "/adminPanel/addContractToUser/" + user_id
        });
        $('#blockUserButton').click(function () {
            newStatus = 'BLOCKED';
            globalSetNewStatus("user", newStatus, user_id);
        });
        $('#unBlockUserButton').click(function () {
            newStatus = 'ACTIVE';
            globalSetNewStatus("user", newStatus, user_id);
        });
        $('#deactivateUserButton').click(function () {
            newStatus = 'INACTIVE';
            globalSetNewStatus("user", newStatus, user_id);
        });

        //deprecated
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
                        url: "/user/editUser" + editing.find("td:first").html(),
                        // The key needs to match your method's input parameter (case-sensitive).
                        data: JSON.stringify({
                            userId: user_id,
                            value: value
                        })
                    }).done(function (msg) {
                        alert(msg);
                        oldValue.html(value);
                    }).fail(function (jqXHR, textStatus) {
                        alert(jqXHR.responseText);
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
        $("#deleteContract").on("click", function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                headers: {
                    'Content-Type': 'text/html; charset=utf-8',
                    'Accept': 'text/html; charset=utf-8'
                },
                type: "POST",
                url: "/adminPanel/contract/deleteContract",
                data: contract_id.toString()
            }).done(function (msg) {
                notify("Leave the page please: ", msg, "success", "fas fa-thumbs-up");
            }).fail(function (jqXHR, textStatus) {
                notify("", jqXHR.responseText, "info");
            });
        });
        $("#contractCurrentOptions").on("click", "tbody tr", function () {
            if ($(this).hasClass('add-tariff-table-selected')) {
                $(this).removeClass('add-tariff-table-selected');
            } else {
                $(this).addClass('add-tariff-table-selected');
            }
        });
        $("#contractAvailableOptions").on("click", "tbody tr", function () {
            if ($(this).hasClass('add-tariff-table-selected')) {
                $(this).removeClass('add-tariff-table-selected');
            } else {
                $(this).addClass('add-tariff-table-selected');
            }
        });

        var newStatus;
        $('#blockContractButton').click(function () {
            newStatus = 'BLOCKED';
            globalSetNewStatus("contract", newStatus, contract_id);
        });
        $('#unBlockContractButton').click(function () {
            newStatus = 'ACTIVE';
            globalSetNewStatus("contract", newStatus, contract_id);
        });
        $('#deactivateContractButton').click(function () {
            newStatus = 'INACTIVE';
            globalSetNewStatus("contract", newStatus, contract_id);
        });

        // + logic in addContract page - same class items
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
                url: "/adminPanel/contract/switchTariff",
                // The key needs to match your method's input parameter (case-sensitive).
                data: JSON.stringify({
                    tariffId: newTariffId,
                    contractId: contract_id
                })
            }).done(function (msg) {
                notify("", msg, "success", "fas fa-thumbs-up");
                $('#tariffTable').find('tr:eq(1)').find('td:eq(1)').html(newTariffId);
                getOptionsForTariff(newTariffId, header, token);
            }).fail(function (jqXHR, textStatus) {
                notify("", jqXHR.responseText, "info");
            });
        });

        $('#contractAddOption').on('click', function () {
            // OPTIONS RULES
            var tr = $("#contractAvailableOptions tr.add-tariff-table-selected").clone();
            $('#parseTable tbody tr').remove();
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
                url: "/adminPanel/contract/addOptions",
                // The key needs to match your method's input parameter (case-sensitive).
                data: JSON.stringify({tariffOptionDtoList: table.tableToJSON(), contractId: contract_id})
            }).done(function (msg) {
                notify("", msg, "success", "fas fa-thumbs-up");
                var tr = $("#contractAvailableOptions tr.add-tariff-table-selected").clone();
                tr.removeClass('add-tariff-table-selected');
                $("#contractCurrentOptions").append(tr);
            }).fail(function (jqXHR, textStatus) {
                notify("", jqXHR.responseText, "info");
            });
        });

        $('#contractDelOption').on('click', function () {
            var tr = $("#contractCurrentOptions tr.add-tariff-table-selected").clone();
            $('#parseTable tbody tr').remove();
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
                url: "/adminPanel/contract/delOptions",
                // The key needs to match your method's input parameter (case-sensitive).
                data: JSON.stringify({tariffOptionDtoList: table.tableToJSON(), contractId: contract_id})
            }).done(function (msg) {
                notify("", msg, "success", "fas fa-thumbs-up");
                var tr = $("#contractCurrentOptions tr.add-tariff-table-selected").remove();
            }).fail(function (jqXHR, textStatus) {
                notify("", jqXHR.responseText, "info");
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
            location.reload();
        }).fail(function (jqXHR, textStatus) {
            notify("", jqXHR.responseText, "info");
        });
    }

    function addTariffPanel() {
        $('#addTariff').on('click', function () {
            addTariff();
        });

        function addTariffTableBehavior() {
            $("#addTariffAddedOptions").on("click", "tr.move-row", function () {
                if ($(this).hasClass('add-tariff-table-selected')) {
                    $(this).removeClass('add-tariff-table-selected');
                } else {
                    $(this).addClass('add-tariff-table-selected');
                }
            });
            $("#addTariffAvailableOptions").on("click", "tr.move-row", function () {
                if ($(this).hasClass('add-tariff-table-selected')) {
                    $(this).removeClass('add-tariff-table-selected');
                } else {
                    $(this).addClass('add-tariff-table-selected');
                }
            });
            $('#addTariffAddOption').on('click', function () {
                var success = true;
                var tr = $("#addTariffAvailableOptions tr.add-tariff-table-selected");
                if (tr.length === 0) {
                    notify("", "Chose option to add.", "info");
                }
                target = $("#addTariffAddedOptions");
                table = target.tableToJSON();
                for (var i = 0; i < table.length; i++) {
                    $.each(tr, function () {
                        if ($(this).find("td:first")[0].innerText === table[i].Id) {
                            notify("", "Option " + table[i].Name + " is already in list.", "info");
                            success = false;
                        }
                    });
                }
                if(success){
                    tr.removeClass('add-tariff-table-selected');
                    target.append(tr.clone());
                }
            });
            $('#addTariffDelOption').on('click', function () {
                var tr = $("#addTariffAddedOptions tr.add-tariff-table-selected").remove().clone();
                if (tr.length === 0) {
                    notify("", "Chose option to delete.", "info");
                }
                tr.removeClass('add-tariff-table-selected');
            });
        }

        function addTariff() {
            var part1 = $('#addTariffAddedOptions').tableToJSON();
            part1.forEach(function (element) {
                element["costofadd"] = element["Cost of add"];
                delete element["Cost of add"];
                element.id = element.Id;
                delete element.Id;
                element.name = element.Name;
                delete element.Name;
                element.price = element.Price;
                delete element.Price;
            });
            var part2 = {name: $('#name').val(), price: $('#price').val()};
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
                url: "/adminPanel/addTariff",
                // The key needs to match your method's input parameter (case-sensitive).
                data: JSON.stringify({tariffOptionDtoList: part1, tariffDto: part2})
            }).done(function (msg) {
                notify("", msg, "success", "fas fa-thumbs-up");
            }).fail(function (xhr, a, error) {
                notify("", xhr.responseText, "info");
            })
        }

        addTariffTableBehavior();
    }


    function addContractPanel() {
        $('#addContract').on('click', function () {
            addContract();
        });

        function addContract() {
            var part1 = $('#addContractAddedOptions').tableToJSON();
            part1.forEach(function (element) {
                element["costofadd"] = element["Cost of add"];
                delete element["Cost of add"];
                element.id = element.Id;
                delete element.Id;
                element.name = element.Name;
                delete element.Name;
                element.price = element.Price;
                delete element.Price;
            });
            var tariffId = null;
            tariffId = $("#addContractTariffs tr.add-tariff-table-selected").find('td:first').html();
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
                url: "/adminPanel/addContract",
                data: JSON.stringify({
                    tariffOptionDtoList: part1,
                    userId: $('#userId').val(),
                    tariffId: tariffId,
                    phoneNumber: $('#phoneNumber').val()
                })
            }).done(function (msg) {
                notify("", msg, "success", "fas fa-thumbs-up")
            }).fail(function (xhr) {
                notify("", xhr.responseText, "primary")
            })
        }

        function addContractTableBehavior() {
            $("#addContractAddedOptions").on("click", "tbody tr", function () {
                if ($(this).hasClass('add-tariff-table-selected')) {
                    $(this).removeClass('add-tariff-table-selected');
                } else {
                    $(this).addClass('add-tariff-table-selected');
                }
            });
            $("#addContractAvailableOptions").on("click", "tbody tr", function () {
                if ($(this).hasClass('add-tariff-table-selected')) {
                    $(this).removeClass('add-tariff-table-selected');
                } else {
                    $(this).addClass('add-tariff-table-selected');
                }
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
                        'Content-Type': 'text/html; charset=utf-8'
                    },
                    type: "POST",
                    url: "/tariffOptions",
                    // The key needs to match your method's input parameter (case-sensitive).
                    data: $(this).find('td:first').html(),
                    dataType: "json"
                }).done(function (json) {
                    $("#addContractAddedOptions tbody tr").remove();
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
                    notify("Error:", jqXHR.responseText, "danger");
                });
            });

            $('#addContractAddOption').on('click', function () {
                var success = true;
                var tr = $("#addContractAvailableOptions tr.add-tariff-table-selected");
                if (tr.length === 0) {
                    notify("", "Chose option to add.", "info");
                }
                target = $("#addContractAddedOptions");
                table = target.tableToJSON();
                for (var i = 0; i < table.length; i++) {
                    $.each(tr, function () {
                        if ($(this).find("td:first")[0].innerText === table[i].Id) {
                            notify("", "Option " + table[i].Name + " is already in list.", "info");
                            success = false;
                        }
                    });
                }
                if(success){
                    tr.removeClass('add-tariff-table-selected');
                    target.append(tr.clone());
                }
            });
            $('#addContractDelOption').on('click', function () {
                tr = $("#addContractAddedOptions tr.add-tariff-table-selected");
                if (tr.length === 0) {
                    notify("", "Chose option to delete.", "info");
                }
                tr.remove();
            });
        }

        addContractTableBehavior();
    }

    function tariffPanel() {
        $("#archiveTariff").on("click", function () {
            newStatus = 'INACTIVE';
            globalSetNewStatus("tariff", newStatus, tariff_id);
        });
        $("#unArchiveTariff").on("click", function () {
            newStatus = 'ACTIVE';
            globalSetNewStatus("tariff", newStatus, tariff_id);
        });
        $("#deleteTariff").on("click", function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                headers: {
                    'Content-Type': 'text/html; charset=utf-8',
                    'Accept': 'text/html; charset=utf-8'
                },
                type: "POST",
                url: "/adminPanel/tariff/deleteTariff",
                data: tariff_id.toString()
            }).done(function (msg) {
                notify("Leave the page please:", msg, "success", "fas fa-thumbs-up");
            }).fail(function (jqXHR, textStatus) {
                notify("", jqXHR.responseText, "info");
            });
        });

        function tariffTableBehavior() {
            $("#tariffAddedOptions").on("click", "tr.move-row", function () {
                if ($(this).hasClass('add-tariff-table-selected')) {
                    $(this).removeClass('add-tariff-table-selected');
                } else {
                    $(this).addClass('add-tariff-table-selected');
                }
            });
            $("#tariffAvailableOptions").on("click", "tr.move-row", function () {
                if ($(this).hasClass('add-tariff-table-selected')) {
                    $(this).removeClass('add-tariff-table-selected');
                } else {
                    $(this).addClass('add-tariff-table-selected');
                }
            });

            $('#tariffAddOption').on('click', function () {
                var tr = $("#tariffAvailableOptions tr.add-tariff-table-selected").clone();
                $('#parseTable tbody tr').remove();
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
                    url: "/adminPanel/tariff/addOptions",
                    // The key needs to match your method's input parameter (case-sensitive).
                    data: JSON.stringify({tariffOptionDtoList: table.tableToJSON(), tariffId: tariff_id})
                }).done(function (msg) {
                    notify("", msg, "success", "fas fa-thumbs-up");
                    var tr = $("#tariffAvailableOptions tr.add-tariff-table-selected").clone();
                    tr.removeClass('add-tariff-table-selected');
                    $("#tariffAddedOptions").append(tr);
                }).fail(function (jqXHR, textStatus) {
                    notify("", jqXHR.responseText, "info");
                });

            });

            $('#tariffDelOption').on('click', function () {
                var tr = $("#tariffAddedOptions tr.add-tariff-table-selected").clone();
                $('#parseTable tbody tr').remove();
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
                    url: "/adminPanel/tariff/delOptions",
                    // The key needs to match your method's input parameter (case-sensitive).
                    data: JSON.stringify({tariffOptionDtoList: table.tableToJSON(), tariffId: tariff_id})
                }).done(function (msg) {
                    notify("", msg, "success", "fas fa-thumbs-up");
                    var tr = $("#tariffAddedOptions tr.add-tariff-table-selected").remove();
                }).fail(function (jqXHR, textStatus) {
                    notify("", jqXHR.responseText, "info");
                });
            });
        }

        tariffTableBehavior();
    }

    function optionPanel() {
        $("#deleteOption").on("click", function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                headers: {
                    'Content-Type': 'text/html; charset=utf-8',
                    'Accept': 'text/html; charset=utf-8'
                },
                type: "POST",
                url: "/adminPanel/option/deleteOption",
                data: option_id.toString()
            }).done(function (msg) {
                notify("Leave page please:", msg, "success", "fas fa-thumbs-up");
            }).fail(function (jqXHR, textStatus) {
                notify("", jqXHR.responseText, "info");
            });
        });

        function optionRules() {
            var rule, addButton, delButton, availableOptionsTable, addedOptionsTable;

            function optionTablesSelectionBehavior() {
                tableSelectionRules($("#optionAvailableOptionsRelated"), $("#optionAddedOptionsRelated"),
                    $("#optionAvailableOptionsExcluding"), $("#optionAddedOptionsExcluding"));
            }

            // selecting table rows processed in tariff page function
            function editOptions(rule, addButton, delButton, availableOptionsTable, addedOptionsTable) {
                addButton.on('click', function () {
                    var success = true;
                    var tr = availableOptionsTable.find("tr.add-tariff-table-selected").clone();
                    if (tr.length === 0) {
                        notify("", "Chose option to add.", "info");
                    }
                    var checktable = addedOptionsTable.tableToJSON();
                    for (var i = 0; i < checktable.length; i++) {
                        $.each(tr, function () {
                            if ($(this).find("td:first")[0].innerText === checktable[i].Id) {
                                notify("", "Option " + checktable[i].Name + " is already in list.", "info");
                                success = false;
                            }
                        });
                    }
                    if(!success) {
                        return;
                    }
                    $('#parseTable tbody tr').remove();
                    var table = $('#parseTable');
                    for (i = 0; i < tr.length; i++) {
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
                        url: "/adminPanel/option/addOptions",
                        // The key needs to match your method's input parameter (case-sensitive).
                        data: JSON.stringify({
                            tariffOptionDtoList: table.tableToJSON(),
                            tariffOptionId: option_id,
                            rule: rule
                        })
                    }).done(function (msg) {
                        notify("", msg, "success", "fas fa-thumbs-up");
                        var tr = availableOptionsTable.find("tr.add-tariff-table-selected").clone();
                        tr.removeClass('add-tariff-table-selected');
                        addedOptionsTable.append(tr);
                    }).fail(function (jqXHR, textStatus) {
                        notify("", jqXHR.responseText, "info");
                    });
                });
                delButton.on('click', function () {
                    var tr = addedOptionsTable.find("tr.add-tariff-table-selected").clone();
                    $('#parseTable tbody tr').remove();
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
                        url: "/adminPanel/option/delOptions",
                        // The key needs to match your method's input parameter (case-sensitive).
                        data: JSON.stringify({
                            tariffOptionDtoList: table.tableToJSON(),
                            tariffOptionId: option_id,
                            rule: rule
                        })
                    }).done(function (msg) {
                        notify("", msg, "success", "fas fa-thumbs-up");
                        var tr = addedOptionsTable.find("tr.add-tariff-table-selected").remove();
                    }).fail(function (jqXHR, textStatus) {
                        notify("", jqXHR.responseText, "info");
                    });
                });
            }

            function relatedOptions() {
                rule = "RELATED";
                addButton = $("#optionAddOptionRelated");
                delButton = $("#optionDelOptionRelated");
                availableOptionsTable = $("#optionAvailableOptionsRelated");
                addedOptionsTable = $("#optionAddedOptionsRelated");
                editOptions(rule, addButton, delButton, availableOptionsTable, addedOptionsTable);
            }

            function excludingOptions() {
                rule = "EXCLUDING";
                addButton = $("#optionAddOptionExcluding");
                delButton = $("#optionDelOptionExcluding");
                availableOptionsTable = $("#optionAvailableOptionsExcluding");
                addedOptionsTable = $("#optionAddedOptionsExcluding");
                editOptions(rule, addButton, delButton, availableOptionsTable, addedOptionsTable);
            }

            optionTablesSelectionBehavior();
            excludingOptions();
            relatedOptions();
        }

        optionRules();

    }

    function getOptionsForTariff(newTariffId, header, token) {
        $.ajax({
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            headers: {
                'Content-Type': 'text/html; charset=utf-8'
            },
            type: "POST",
            url: "/tariffOptions",
            // The key needs to match your method's input parameter (case-sensitive).
            data: newTariffId,
            dataType: "json"
        }).done(function (json) {
            $("#contractAvailableOptions tbody").remove();
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
            $("#contractAvailableOptions").append(tbl_body);
        }).fail(function (jqXHR, textStatus) {
            notify("Error:", jqXHR.responseText, "danger");
        });
    }

    $('#addFunds').click(function () {
        var amount = $('#addFundsInput').val();
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
            url: "/adminPanel/addFunds",
            // The key needs to match your method's input parameter (case-sensitive).
            data: JSON.stringify({
                amount: amount,
                userId: user_id
            })
        }).done(function (msg) {
            notify("", msg, "success", "fas fa-thumbs-up");
            updateCartBalance();
        }).fail(function (jqXHR) {
            notify("", jqXHR.responseText, "info")
        });
    });

    function updateCartBalance() {
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
            url: "/adminPanel/getBalance",
            data: JSON.stringify({
                userId: user_id
            })
        }).done(function (msg) {
            $("#walletBalance").html(msg);
        }).fail(function (jqXHR, textStatus) {
            notify("Error", jqXHR.responseText, "danger");
        });
    }

    $("#addOptionSubmit").on("submit", function (e) {
        if ($("#addOptionPrice").val() === "" || $("#addOptionCostOfAdd").val() === "") {
            e.preventDefault();
            notify("", "Enter price and cost of add.", "info");
        }
    });

    optionPanel();
    adminPanel();
    userPanel();
    contractPanel();
    tariffPanel();
    addTariffPanel();
    addContractPanel();

})();