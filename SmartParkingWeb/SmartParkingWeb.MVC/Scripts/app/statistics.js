var statisticmanager = {
    init: function () {
        this.setDatePicker();
        this.addListeners();

        console.log(Date.parse('1472688000000'));
    },
    addListeners: function () {
        $('.datepicker-text').keydown(function () {
            return false;
        });

        $('.datepicker-text').click(function () {
            var span = $(this).parent().find("span");
            $(this).closest(".date").data("DateTimePicker").show();
        });

        $("#btnStatistics").click(statisticmanager.getStatistics);
        $("#cbShowAll").change(function () {
            if ($(this).is(":checked"))
                $('.datepicker-text').prop("disabled", true);
            else
                $('.datepicker-text').prop("disabled", false);
        });

    },

    getStatistics: function () {

        var data = null;        

        if (!$("#cbShowAll").is(":checked")) {
            data = {
                from: $("#dateTo").data('date'),
                to: $("#dateFrom").data('date')
            };
        }

        $.ajax({
            url: "/Home/GetStatistics",
            data: data,
            dataType: "json",
            method: "GET",
            success: function (data) {
                console.log(data);
            }
        });

    },
    setDatePicker: function () {
        $('#dateFrom').datetimepicker({
            format: 'DD.MM.YYYY',
            defaultDate: new Date()
        });
        $('#dateTo').datetimepicker({
            useCurrent: false, //Important! See issue #1075
            format: 'DD.MM.YYYY',
            defaultDate: new Date()
        });
        $("#dateFrom").on("dp.change", function (e) {
            if ($("#dateTo").data("date") < $("#dateFrom").data("date") || !$("#dateTo").data().hasOwnProperty('date')) {
                var date = $("#dateFrom").data("date");
                var arr = date.split(".");
                $("#dateTo").data("DateTimePicker").date(new Date(arr[2], arr[1] - 1, arr[0]));
            }
            $('#dateTo').data("DateTimePicker").minDate(e.date);
        });

        $("#dateTo").on("dp.change", function (e) {
            $('#dateFrom').data("DateTimePicker").maxDate(e.date);
        });

    }
}


$(function () {
    statisticmanager.init();
});