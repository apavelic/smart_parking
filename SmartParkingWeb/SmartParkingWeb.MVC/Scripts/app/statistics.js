var statisticmanager = {
    seriesOptions: [],
    minHours: 100,
    maxHours: 0,
    init: function () {
        this.setDatePicker();
        this.addListeners();
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
                from: $("#dateFrom").data('date'),
                to: $("#dateTo").data('date')
            };
        }

        $.ajax({
            url: "/Home/GetStatistics",
            data: data,
            dataType: "text",
            method: "GET",
            success: function (jsonString) {
                var data = $.parseJSON(jsonString);
                var seriesOptions = statisticmanager.prepareMultipleHighchartData(data.MultipleHighchartsData);
                statisticmanager.showMultipleHighchart(seriesOptions);
                //seriesOptions = statisticmanager.prepareHistoricalHighchartData(data.HistoricalHighchartsData);
                //statisticmanager.showHistoricalHighchart(seriesOptions);

            }
        });

    },
    prepareMultipleHighchartData: function (data) {
        var i = 0;
        var seriesOptions = [];

        $.each(data, function (key, value) {

            var array = $.map(value, function (value, index) {
                return [[Number(index), value]];
            });


            $(array).each(function (key, value) {
                $(value[1]).each(function (key, value) {
                    if (value > statisticmanager.maxHours) {
                        statisticmanager.maxHours = value;
                    }
                    if (value < statisticmanager.minHours) {
                        statisticmanager.minHours = value
                    }
                });
            });


            seriesOptions[i++] = {
                name: key,
                data: array
            };
        });

        return seriesOptions;
    },
    prepareHistoricalHighchartData: function (data) {
        var array = $.map(data, function (value, index) {
            return [[index, value]];
        });

        return array;
    },
    setDatePicker: function () {

        var date = new Date();
        var tomorrow = new Date();
        tomorrow.setDate(date.getDate() + 1);

        $('#dateFrom').datetimepicker({
            format: 'DD.MM.YYYY',
            defaultDate: date
        });
        $('#dateTo').datetimepicker({
            useCurrent: false, //Important! See issue #1075
            format: 'DD.MM.YYYY',
            defaultDate: tomorrow
        });
        $("#dateFrom").on("dp.change", function (e) {
            var date = $("#dateFrom").data("date");
            var arr = date.split(".");
            var day = Number(arr[0]) + 1;

            var minDate = new Date(arr[2], arr[1] - 1, day);

            if ($("#dateTo").data("date") < $("#dateFrom").data("date") || !$("#dateTo").data().hasOwnProperty('date')) {
                date = new Date(arr[2], arr[1] - 1, day);
                $("#dateTo").data("DateTimePicker").date(minDate);
            }
            $('#dateTo').data("DateTimePicker").minDate(minDate);
        });

        $("#dateTo").on("dp.change", function (e) {
            var date = $("#dateTo").data("date");
            var arr = date.split(".");
            var day = Number(arr[0]) - 1;

            var maxDate = new Date(arr[2], arr[1] - 1, day);
            $('#dateFrom').data("DateTimePicker").maxDate(maxDate);
        });

    },
    showMultipleHighchart: function (data) {
        Highcharts.stockChart('multiple_chart', {
            rangeSelector: {
                selected: 4
            },
            credits: {
                enabled: false
            },
            yAxis: {

                min: statisticmanager.minHours,
                max: statisticmanager.maxHours,
                reversed: false,
                title: {
                    text: "Hours"
                }
            },
            plotOptions: {
                series: {
                    compare: 'number',
                    showInNavigator: true
                }
            },
            title: {
                text: 'Parking occupancy report'
            },
            tooltip: {
                pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> ({point.change}%)<br/>',
                valueDecimals: 2,
                split: true
            },
            series: data
        });

    },
    showHistoricalHighchart: function (data) {
        //Highcharts.chart('detail-container', {
        //    chart: {
        //        marginBottom: 120,
        //        reflow: false,
        //        marginLeft: 50,
        //        marginRight: 20,
        //        style: {
        //            position: 'absolute'
        //        }
        //    },
        //    credits: {
        //        enabled: false
        //    },
        //    title: {
        //        text: 'Historical parking profit'
        //    },
        //    xAxis: {
        //        type: 'datetime'
        //    },
        //    yAxis: {
        //        title: {
        //            text: null
        //        },
        //        maxZoom: 0.1
        //    },
        //    tooltip: {
        //        formatter: function () {
        //            var point = this.points[0];
        //            return '<b>' + point.series.name + '</b><br/>' + Highcharts.dateFormat('%A %B %e %Y', this.x) + ':<br/>' +
        //                '1 USD = ' + Highcharts.numberFormat(point.y, 2) + ' EUR';
        //        },
        //        shared: true
        //    },
        //    legend: {
        //        enabled: false
        //    },
        //    plotOptions: {
        //        series: {
        //            marker: {
        //                enabled: false,
        //                states: {
        //                    hover: {
        //                        enabled: true,
        //                        radius: 3
        //                    }
        //                }
        //            }
        //        }
        //    },
        //    series: [{
        //        name: 'Ilica 242',
        //        data: data
        //    }],
        //    exporting: {
        //        enabled: false
        //    }

        //})
    }
}


$(function () {
    statisticmanager.init();
});