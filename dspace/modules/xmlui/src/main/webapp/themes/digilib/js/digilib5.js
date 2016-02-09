/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    $.cookieBar({
        fixed: true,
        declineBUtton: true,
        bottom: true
    });
    
    (function () {
        if (typeof Cookies.get('digilibVisited') === 'undefined') {
            console.log('cookie not set');
            Cookies.set('digilib.phil.muni.cz', '1', {expires: 14});
        } else
        {
            console.log(Cookies.get('digilib.phil.muni.cz'));
        }
    })();

    $('.contact-email').email();

    $(".show-advanced-filters").click(function (event) {
        $("#aspect_discovery_SimpleSearch_div_search-filters").show();
        $(this).hide();
        $(".hide-advanced-filters").show();
    });

    $(".hide-advanced-filters").click(function (event) {
        $("#aspect_discovery_SimpleSearch_div_search-filters").hide();
        $(this).hide();
        $(".show-advanced-filters").show();
    });

    $(this).on('click', '.filter-remove', function (event) {
        if ($('.row .in-use').length > 1) {
            $(this).closest('.row .in-use').remove();
        }
    });

    $(this).on('click', '.filter-add', function () {
        var $row = $("div.in-use:last").clone();
        console.log($row);
        $.each($row.find(':input[name]'), function () {
            var no = parseInt($(this).attr('name').match(/\d+/)) + 1;
            $(this).attr('name', $(this).attr('name').replace(/(\d+)(?!.*\d)/g, no));
            $(this).attr('id', $(this).attr('id').replace(/(\d+)(?!.*\d)/g, no));
            $(this).find('option:selected').removeAttr("selected");
            $(this).val("");
        });
        $row.insertBefore('.button-row');
    });


    $("#aspect_discovery_SimpleSearch_div_search-controls-gear a.gear-option").on('click', function (e) {
        e.stopPropagation();
        if ($(this).not(".gear-option-selected").length)
        {
            var params = $(this).attr('href').split('&');
            var form = $("#aspect_discovery_SimpleSearch_div_main-form");
            $.each(params, function (i, val) {
                var param = val.split('=')[0];
                var value = val.split('=')[1];
                form.find('input[name="' + param + '"]').val(value);
            });
            form.submit();
        }
        e.preventDefault();
    });

    $("#aspect_artifactbrowser_ConfigurableBrowse_div_browse-controls a").on('click', function () {
        var name = $(this).data('name');
        var val = $(this).data('value');
        $("#aspect_artifactbrowser_ConfigurableBrowse_div_browse-controls select[name='" + name + "']").val(val).change();
        $("#aspect_artifactbrowser_ConfigurableBrowse_div_browse-controls").submit();
    });

    $(".used-filters span.label").on('click', function () {
        var removeID = $(this).data('remove-input');

        $(".used-filters input").each(function () {
            if ($(this).data('remove') == removeID) {
                $(this).remove();
            }
        });

        $("#aspect_discovery_SimpleSearch_div_general-query").submit();
    });


    $('.landing-view-switch').on('click', function () {
        console.log($(this).data('view'));
        if ($(this).data('view') === 'list')
        {
            $('.list-view').show();
            $('.tab-view').hide();
        } else {
            $('.tab-view').show();
            $('.list-view').hide();
        }
    });

    $(".fa-info-circle").tooltip();
    $(".scroll-top").scrollTop(0);

    $(".banner-uvt").on(
            {
                mouseover: function () {
                    $(this).toggleClass('banner-uvt-colored');
                },
                mouseout: function () {
                    $(this).toggleClass('banner-uvt-colored');
                }
            });

    $(".toggle-abstracts").on('click',function(){
        $(".issue-item-abstract").toggle();
        $(this).blur();
    });
    
    $(".minify-list").on('click',function(){
        $(".media-left").toggle();
        $(".media-right").toggle();
        $(".media-abstract").toggle(); 
    });

//    $(".slider").bxSlider();
});


(function ($) {
    $.fn.email = function (options) {
        var defaults = {user: 'digi', a: '@', domain: 'phil.muni', suffix: ".cz"};

        var output = $.extend({}, defaults, options);
        var href = 'mailto:';
        var label = '';
        $.each(output, function (k, v) {
            label += v;
        });
        href += label;
        $(this).parent('a').attr('href', href);
        $(this).html(label);
        return this;
    };
}(jQuery));