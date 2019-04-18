var iataCodes = new Bloodhound({
    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name code'),
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    // prefetch: {
    //     url: '/api/v1/iata-code/all',
    //     cache: true
    // },
    remote: {
        url: '/api/v1/iata-code?name=%QUERY',
        wildcard: '%QUERY'
    }
});

iataCodes.initialize();

$('#fromAirport').typeahead(null, {
    hint: false,
    minLength: 2,
    name: 'airports',
    display: 'name',
    limit: 7,
    source: iataCodes,
    templates: {
        notFound: '<div class="empty-message">Not Found - try another name</div>', /* Rendered if 0 suggestions are available */
        pending: '<div>Loading...</div>', /* Rendered if 0 synchronous suggestions available
                                           but asynchronous suggestions are expected */
        header: '<div>Airports:</div>', /* Rendered at the top of the dataset when
                                           suggestions are present */
        suggestion: function (data) {       /* Used to render a single suggestion */
            return '<div><strong>' + data.name + '</strong> - (' + data.code + ')</div>'
        }
        /*footer: '<div>Footer Content</div>' Rendered at the bottom of the dataset
                                           when suggestions are present. */
    }
}).on('typeahead:select', function (ev, suggestion) {
    $('#fromCode').val(suggestion.code);
});

$('#toAirport').typeahead(null, {
    hint: false,
    minLength: 2,
    name: 'airports',
    display: 'name',
    limit: 7,
    source: iataCodes,
    templates: {
        notFound: '<div class="empty-message">Not Found - try another name</div>',
        pending: '<div>Loading...</div>',
        header: '<div>Airports:</div>',
        suggestion: function (data) {
            return '<div><strong>' + data.name + '</strong> - (' + data.code + ')</div>'
        }
    }
}).on('typeahead:select', function (ev, suggestion) {
    $('#toCode').val(suggestion.code);
});

$.fn.datepicker.defaults.format = "dd.mm.yyyy.";
$.fn.datepicker.defaults.autoclose = true;
$.fn.datepicker.defaults.weekStart = 1;
$.fn.datepicker.defaults.todayHighlight = true;

$('.datepicker').datepicker({});

$(function () {
    $("#submitBtn").click(function () {
        $("#loading").fadeIn();
    });
});

(function ($) {
    "use strict";
    // Scroll to top button appear
    $(document).on('scroll', function () {
        var scrollDistance = $(this).scrollTop();
        if (scrollDistance > 100) {
            $('.scroll-to-top').fadeIn();
        } else {
            $('.scroll-to-top').fadeOut();
        }
    });

// Smooth scrolling using jQuery easing
    $(document).on('click', 'a.scroll-to-top', function (e) {
        var $anchor = $(this);
        $('html, body').stop().animate({
            scrollTop: ($($anchor.attr('href')).offset().top)
        }, 1000, 'easeInOutExpo');
        e.preventDefault();
    });
})(jQuery);
