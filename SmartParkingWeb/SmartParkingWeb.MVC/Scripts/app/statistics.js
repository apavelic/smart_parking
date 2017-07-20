var statisticmanager = {
    seriesOptions: [],
    minHours: 100000,
    maxHours: 0,
    minProfit: 100000,
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
            beforeSend: function () {
                statisticmanager.showLoader(true);
            },
            complete: function () {
                statisticmanager.showLoader(false);
            },
            url: "/Home/GetStatistics",
            data: data,
            dataType: "text",
            method: "GET",
            success: function (jsonString) {
                var data = $.parseJSON(jsonString);
                $(".chart").addClass("chart-options");

                var seriesOptions = statisticmanager.prepareMultipleHighchartData(data.MultipleHighchartData);
                statisticmanager.showMultipleHighchart(seriesOptions);

                seriesOptions = statisticmanager.prepareHistoricalHighchartData(data.HistoricalHighchartData);
                statisticmanager.showHistoricalHighchart(seriesOptions);

                statisticmanager.minProfit = 100000;

                seriesOptions = statisticmanager.prepareHistogramHighchartData(data.HistogramHighchartData);
                statisticmanager.showHistogramHighchart(seriesOptions);
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
            return [[Number(index), value]]
        });

        $(array).each(function (key, value) {
            if (value[1] < statisticmanager.minProfit) {
                statisticmanager.minProfit = value[1];
            }
        });

        return array;
    },
    prepareHistogramHighchartData: function (data) {
        var array = $.map(data, function (value, index) {
            return [[index, value]];
        });

        $(array).each(function (key, value) {
            if (value[1] < statisticmanager.minProfit) {
                statisticmanager.minProfit = value[1];
            }
        });

        return array;
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
        Highcharts.chart('historical_chart', {
            chart: {
                type: 'spline'
            },
            credits: {
                enabled: false
            },
            title: {
                text: 'Profit per day report'
            },
            xAxis: {
                type: 'datetime',
                dateTimeLabelFormats: {
                    month: '%e. %b',
                    year: '%b'
                },
                title: {
                    text: 'Date'
                }
            },
            yAxis: {
                title: {
                    text: 'Profit (HRK)'
                },
                min: statisticmanager.minProfit - 50
            },
            tooltip: {
                headerFormat: '<b>{series.name}</b><br>',
                pointFormat: '{point.x:%e. %b}: {point.y:.2f} HRK'
            },
            plotOptions: {
                spline: {
                    marker: {
                        enabled: true
                    }
                }
            },
            series: [{
                name: 'Parking location: Ilica 242',
                data: data
            }]
        });
    },
    showHistogramHighchart: function (data) {
        Highcharts.chart('histogram_chart', {
            chart: {
                type: 'column'
            },
            credits: {
                enabled: false
            },
            title: {
                text: 'Profit per month report'
            },
            xAxis: {
                type: 'category',
                labels: {
                    rotation: -45,
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Profit (HRK)'
                }
            },
            legend: {
                enabled: false
            },
            tooltip: {
                pointFormat: '<b>{point.y:.2f} HRK</b>'
            },
            series: [{
                name: 'Months',
                data: data,
                dataLabels: {
                    enabled: true,
                    rotation: -90,
                    color: '#FFFFFF',
                    align: 'right',
                    format: '{point.y:.2f} HRK', 
                    y: 10,
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
            }]
        });
    },
    setDatePicker: function () {

        var today = new Date();
        var yesterday = new Date();
        yesterday.setDate(today.getDate() - 1);

        $('#dateFrom').datetimepicker({
            format: 'DD.MM.YYYY',
            defaultDate: yesterday
        });
        $('#dateTo').datetimepicker({
            useCurrent: false, //Important! See issue #1075
            format: 'DD.MM.YYYY',
            defaultDate: today,
            maxDate: today
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
    showLoader: function (value) {
        if (value) {
            $('#FormPostLoaderModal').modal({ backdrop: 'static', keyboard: false }, 'show');
        } else {
            $('#FormPostLoaderModal').modal('hide');
        }
    }
}


$(function () {
    statisticmanager.init();
});