function notify(title, msg, type, icon) {
    $.notify({
        icon: icon,
        title: title,
        message: msg,
        //url: 'https://github.com/mouse0270/bootstrap-notify',
        target: '_blank'
    }, {
        // settings
        element: 'body',
        position: null,
        type: type,
        allow_dismiss: true,
        newest_on_top: false,
        showProgressbar: false,
        placement: {
            from: "top",
            align: "right"
        },
        offset: {
            x: 10,
            y: 70
        },
        spacing: 10,
        z_index: 1031,
        delay: 3000,
        timer: 800,
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
        '<button type="button" aria-hidden="true" class="close fas fa-window-close" data-notify="dismiss"></button>' +
        '<span data-notify="icon"></span> ' +
        '<span data-notify="title">{1}</span> ' +
        '<span data-notify="message">{2}</span>' +
        '<div class="progress" data-notify="progressbar">' +
        '<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
        '</div>' +
        // '<a href="{3}" target="{4}" data-notify="url"></a>' +
        '</div>'
    });
}
(function () {


    function randomInteger(min, max) {
        var rand = min + Math.random() * (max + 1 - min);
        rand = Math.floor(rand);
        return rand;
    }

    var captchaValue = randomInteger(0, 10000);
    $("#captchaLabel").html(captchaValue);

    $("#sendSmsForm").on("submit", function (e) {
        e.preventDefault();
        var captcha = $("#captcha").val();
        console.log(captcha);
        console.log(captchaValue);
        if (parseInt(captcha) !== captchaValue) {
            captchaValue = randomInteger(0, 10000);
            $("#captchaLabel").html(captchaValue);
            notify("", "Wrong captcha.", "info");
            return;
        }
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        var phoneNumber = $("#phoneNumber").val();
        $.ajax({
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            headers: {
                'Content-Type': 'application/json'
            },
            type: "POST",
            url: "/sendSms",
            // The key needs to match your method's input parameter (case-sensitive).
            data: JSON.stringify({
                phoneNumber: phoneNumber
            }),
            datatype: "json"
        }).done(function (msg) {
            document.location = "/login";
        }).fail(function (jqXHR) {
            notify("", jqXHR.responseText, "info")
        });
    })
})();