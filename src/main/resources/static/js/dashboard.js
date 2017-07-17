jQuery(document).ready(function() {

    "use strict";

    function showTooltip(x, y, contents) {
        jQuery('<div id="tooltip" class="tooltipflot">' + contents + '</div>').css({
            position: 'absolute',
            display: 'none',
            top: y + 5,
            left: x + 5
        }).appendTo("body").fadeIn(200);
    }


    /***** BAR CHART *****/

    var m3 = new Morris.Bar({
        // ID of the element in which to draw the chart.
        element: 'bar-chart',
        // Chart data records -- each entry in this array corresponds to a point on
        // the chart.
        data: [
            {y: 'January', a: 0, b: 0},
            {y: 'February', a: 0, b: 0},
            {y: 'March', a: 0, b: 0},
            {y: 'April', a: 0, b: 0},
            {y: 'May', a: 50, b: 40},
            {y: 'June', a: 75, b: 65},
            {y: 'July', a: 75, b: 65},
            {y: 'August', a: 0, b: 0},
            {y: 'September', a: 0, b: 0},
            {y: 'October', a: 0, b: 0},
            {y: 'November', a: 0, b: 0},
            {y: 'December', a: 0, b: 0}
        ],
        xkey: 'y',
        ykeys: ['a', 'b'],
        labels: ['Vendors', 'Profiles'],
        lineWidth: '1px',
        fillOpacity: 0.8,
        smooth: false,
        hideHover: true,
        resize: true
    });

    var delay = (function () {
        var timer = 0;
        return function (callback, ms) {
            clearTimeout(timer);
            timer = setTimeout(callback, ms);
        };
    })();

    jQuery(window).resize(function () {
        delay(function () {
            m3.redraw();
        }, 200);
    }).trigger('resize');


    // This will empty first option in select to enable placeholder
    jQuery('select option:first-child').text('');

});