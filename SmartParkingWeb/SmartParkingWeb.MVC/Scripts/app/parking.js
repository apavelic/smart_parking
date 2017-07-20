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

                    $("#lblState_" + id).removeClass("free").remove("taken");

                    if (state == 0) {
                        $("#" + id).css("background", "green");
                        $("#lblState_" + id).css("color", "green");
                        $("#lblState_" + id).html("FREE");
                    } else {
                        $("#" + id).css("background", "red");
                        $("#lblState_" + id).css("color", "red");
                        $("#lblState_" + id).html("TAKEN");
                    }
                });
            }
        });

        $.ajax({
            url: "/Home/GetParkingStatus",
            dataType: "text",
            method: "GET",
            success: function (data) {
                $(".parking-status-color").removeClass("red").removeClass("green");

                if (data == "true") {
                    $(".parking-status-color").addClass("green");
                    $(".parking-status-text").html("ONLINE");
                } else {
                    $(".parking-status-color").addClass("red");
                    $(".parking-status-text").html("OFFLINE");
                }
                console.log(data);
            }
        });
    }
}

$(function () {
    parkingmanager.init();
});