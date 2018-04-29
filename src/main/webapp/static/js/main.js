(function () {
    console.log("hello world");
    $('#addUserButton').click(function () {
        document.location.href = "/adminPanel/addUser"
    });
    $('#addContractButton').click(function () {
        document.location.href = "/adminPanel/addContract"
    });
})();