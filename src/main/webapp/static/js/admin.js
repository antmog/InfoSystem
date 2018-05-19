(function () {

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

        $(".searchUserByNumber").on("click", function () {
            if ($('.searchUserByNumberInput').val() === "") {
                alert("Enter phone number.");
            } else {
                searchUserByNumber();
            }
        });

        function searchUserByNumber() {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            var phoneNumber = $('.searchUserByNumberInput').val();
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
                alert(jqXHR.responseText);

            });
        }

        function updateUserTable(msg) {
            document.location.href = "/adminPanel/user/" + msg.id;
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
        $('#superButton').click(function () {
            document.location.href = "/adminPanel/addOptionS"
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
                alert(msg);
            }).fail(function (jqXHR, textStatus) {
                alert(jqXHR.responseText);

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
                document.location.href = "/adminPanel"
            }).fail(function (jqXHR, textStatus) {
                alert(jqXHR.responseText);
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
                alert(msg);
                $('#tariffTable').find('tr:eq(1)').find('td:eq(1)').html(newTariffId);
                getOptionsForTariff(newTariffId, header, token);
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
                url: "/adminPanel/contract/addOptions",
                // The key needs to match your method's input parameter (case-sensitive).
                data: JSON.stringify({tariffOptionDtoList: table.tableToJSON(), contractId: contract_id})
            }).done(function (msg) {
                alert(msg);
                var tr = $("#contractAvailableOptions tr.add-tariff-table-selected").clone();
                tr.removeClass('add-tariff-table-selected');
                $("#contractCurrentOptions").append(tr);
            }).fail(function (jqXHR, textStatus) {
                alert(jqXHR.responseText);
            });
            $('#parseTable tr.move-row').remove();
        });

        $('#contractDelOption').on('click', function () {
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
                url: "/adminPanel/contract/delOptions",
                // The key needs to match your method's input parameter (case-sensitive).
                data: JSON.stringify({tariffOptionDtoList: table.tableToJSON(), contractId: contract_id})
            }).done(function (msg) {
                alert(msg);
                var tr = $("#contractCurrentOptions tr.add-tariff-table-selected").remove();
            }).fail(function (jqXHR, textStatus) {
                alert(jqXHR.responseText);
            });
            $('#parseTable tr.move-row').remove();
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
            alert(jqXHR.responseText);
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
                var tr = $("#addTariffAvailableOptions tr.add-tariff-table-selected").clone();
                tr.removeClass('add-tariff-table-selected');
                $("#addTariffAddedOptions").append(tr);
            });
            $('#addTariffDelOption').on('click', function () {
                var tr = $("#addTariffAddedOptions tr.add-tariff-table-selected").remove().clone();
                tr.removeClass('add-tariff-table-selected');
                $("#addTariffAvailableOptions").append(tr);
            });
        }

        function addTariff() {
            var part1 = $('#addTariffAddedOptions').tableToJSON();
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
                alert(msg);
            }).fail(function (xhr, a, error) {
                alert(xhr.responseText);
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
            var tariffId = $("#addContractTariffs tr.add-tariff-table-selected").find('td:first').html();
            var part2 = {userId: $('#userId').val(), phoneNumber: $('#phoneNumber').val(), tariffId: tariffId};
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
                data: JSON.stringify({tariffOptionDtoList: part1, contractDto: part2})
            }).done(function (msg) {
                alert(msg);
            }).fail(function (xhr, a, error) {
                alert(xhr.responseText);
                //415 warning validation
                //400 error wrong param
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
                    alert(jqXHR.responseText);
                });
            });

            $('#addContractAddOption').on('click', function () {
                var tr = $("#addContractAvailableOptions tr.add-tariff-table-selected").clone();
                tr.removeClass('add-tariff-table-selected');
                $("#addContractAddedOptions").append(tr);
            });
            $('#addContractDelOption').on('click', function () {
                $("#addContractAddedOptions tr.add-tariff-table-selected").remove();
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
                alert(msg);
            }).fail(function (jqXHR, textStatus) {
                alert(jqXHR.responseText);

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
                    alert(msg);
                    var tr = $("#tariffAvailableOptions tr.add-tariff-table-selected").clone();
                    tr.removeClass('add-tariff-table-selected');
                    $("#tariffAddedOptions").append(tr);
                }).fail(function (jqXHR, textStatus) {
                    alert(jqXHR.responseText);
                });
                $('#parseTable tr.move-row').remove();
            });

            $('#tariffDelOption').on('click', function () {
                var tr = $("#tariffAddedOptions tr.add-tariff-table-selected").clone();
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
                    alert(msg);
                    var tr = $("#tariffAddedOptions tr.add-tariff-table-selected").remove();
                }).fail(function (jqXHR, textStatus) {
                    alert(jqXHR.responseText);
                });
                $('#parseTable tr.move-row').remove();
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
                alert(msg);
            }).fail(function (jqXHR, textStatus) {
                alert(jqXHR.responseText);
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
                    // OPTIONS RULES\
                    var tr = availableOptionsTable.find("tr.add-tariff-table-selected").clone();
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
                        url: "/adminPanel/option/addOptions",
                        // The key needs to match your method's input parameter (case-sensitive).
                        data: JSON.stringify({
                            tariffOptionDtoList: table.tableToJSON(),
                            tariffOptionId: option_id,
                            rule: rule
                        })
                    }).done(function (msg) {
                        alert(msg);
                        var tr = availableOptionsTable.find("tr.add-tariff-table-selected").clone();
                        tr.removeClass('add-tariff-table-selected');
                        addedOptionsTable.append(tr);
                    }).fail(function (jqXHR, textStatus) {
                        alert(jqXHR.responseText);
                    });
                    $('#parseTable tr.move-row').remove();
                });

                delButton.on('click', function () {
                    // OPTIONS RULES

                    var tr = addedOptionsTable.find("tr.add-tariff-table-selected").clone();
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
                        alert(msg);
                        var tr = addedOptionsTable.find("tr.add-tariff-table-selected").remove();
                    }).fail(function (jqXHR, textStatus) {
                        alert(jqXHR.responseText);
                    });
                    $('#parseTable tr.move-row').remove();
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


    function notify(title, msg) {
        $.notify({
            icon: 'fas fa-search',
            title: title,
            message: msg,
            url: 'https://github.com/mouse0270/bootstrap-notify',
            target: '_blank'
        }, {
            // settings
            element: 'body',
            position: null,
            type: "info",
            allow_dismiss: true,
            newest_on_top: false,
            showProgressbar: false,
            placement: {
                from: "top",
                align: "right"
            },
            offset: 20,
            spacing: 10,
            z_index: 1031,
            delay: 5000,
            timer: 1000,
            url_target: '_blank',
            mouse_over: null,
            animate: {
                enter: 'animated fadeInDown',
                exit: 'animated fadeOutUp'
            },
            onShow: null,
            onShown: null,
            onClose: null,
            onClosed: null,
            icon_type: 'class',
            template: '<div data-notify="container" class="col-xs-11 col-sm-3 alert alert-{0}" role="alert">' +
            '<button type="button" aria-hidden="true" class="close" data-notify="dismiss">×</button>' +
            '<span data-notify="icon"></span> ' +
            '<span data-notify="title">{1}</span> ' +
            '<span data-notify="message">{2}</span>' +
            '<div class="progress" data-notify="progressbar">' +
            '<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
            '</div>' +
            '<a href="{3}" target="{4}" data-notify="url"></a>' +
            '</div>'
        });
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
            alert(jqXHR.responseText);

        });
    }

    optionPanel();
    adminPanel();
    userPanel();
    contractPanel();
    tariffPanel();
    addTariffPanel();
    addContractPanel();

})();