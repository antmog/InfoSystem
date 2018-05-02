(function () {
    $('#addUserButton').click(function () {
        document.location.href = "/adminPanel/addUser"
    });
    $('#addContractButton').click(function () {
        document.location.href = "/adminPanel/addContract"
    });
    $('#addTariffButton').click(function () {
        document.location.href = "/adminPanel/addTariff"
    });
    function addTariffTableBehavior(){
        $("#addedOptions").on("click","tr.move-row", function () {
            if ( $(this).hasClass('add-tariff-table-selected')) {
                $(this).removeClass('add-tariff-table-selected');
            } else {
                $(this).addClass('add-tariff-table-selected');
            }
        });
        $("#availableOptions").on("click","tr.move-row", function () {
            if ( $(this).hasClass('add-tariff-table-selected')) {
                $(this).removeClass('add-tariff-table-selected');
            } else {
                $(this).addClass('add-tariff-table-selected');
            }
        });

        $('#addOption').on('click', function () {
            var tr = $("#availableOptions tr.add-tariff-table-selected").remove().clone();
            tr.removeClass('add-tariff-table-selected');
            $("#addedOptions").append(tr);
        });
        $('#delOption').on('click', function () {
            var tr = $("#addedOptions tr.add-tariff-table-selected").remove().clone();
            tr.removeClass('add-tariff-table-selected');
            $("#availableOptions").append(tr);
        });
    }


    $('#addTariff').on('click', function () {
        addOptions();
    });

    <!--- How to catch suceess? always fail -->
    function addOptions(){
        var part1 = $('#addedOptions').tableToJSON() ;
        var part2 = {name: $('#name').val(), price: $('#price').val()};
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            beforeSend:function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'text/html; charset=utf-8'
            },
            type: "POST",
            url: "/adminPanel/addTariff",
            // The key needs to match your method's input parameter (case-sensitive).
            data: JSON.stringify({getOptionsAsJsonDtoList:part1, tariffDto:part2}) ,
            contentType:  "application/json; charset=utf-8",
            dataType: "json"
        }).fail(function (xhr,a,error) {
            alert(error);
        })
    }

    $("#usersTable").on("click","tr.user-row", function () {
        document.location.href = "/adminPanel/user/"+$(this).find("td:first").html();
    });
    $(".contracts-table").on("click","tr.contract-row", function () {
        document.location.href = "/adminPanel/contract/"+$(this).find("td:first").html();
    });


    addTariffTableBehavior();




})();