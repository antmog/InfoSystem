(function () {
    function getRandomInt(min, max) {
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }

    $("#myButton").hover(function () {
        $(this).css({
            top: getRandomInt(0,$( window ).height()-100) + "px",
            left: getRandomInt(0,$( window ).width()-100) + "px"
        });
    }, function () {
    });
})();