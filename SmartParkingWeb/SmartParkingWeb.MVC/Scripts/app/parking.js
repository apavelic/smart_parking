var parkingmanager = {
    init: function () {
        setInterval(this.update, 100);
        this.update();
    },
    update: function () {
        $.ajax({
            url: "/Home/GetParking",
            dataType: "json",
            method: "GET",
            success: function (data) {
                $(data).each(function (key, value) {
                    var id = value.Id;
                    var state = value.State;

                    if (state == 0) {
                        $("#" + id).css("background", "green");
                    } else {
                        $("#" + id).css("background", "red");
                    }
                });
            }
        });
    }
}

$(function () {
    parkingmanager.init();
});