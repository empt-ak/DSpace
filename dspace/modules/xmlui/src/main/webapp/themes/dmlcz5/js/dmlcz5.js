/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function(){
    $('.contact-email').email();

    if (typeof(Storage) !== "undefined") {
        if (localStorage.getItem("hasvisited") == null) {
            $("div#welcome-banner").removeClass("hidden-xl-down");

            localStorage.setItem("hasvisited", true);
        }
    }

    $("div#welcome-banner button.close").on('click', function () {
        $(this).parent().hide();
    });

    var typingTimer;
    var $input = $("#MathInput");

    $input.on("keyup", function () {
        clearTimeout(typingTimer);

        typingTimer = setTimeout(doneTyping,800)
    });

    $input.on("keydown",function () {
        clearTimeout(typingTimer);
    });

    function doneTyping(){
        $("#mathbuffer").text($input.val());

        MathJax.Hub.Queue(["Typeset", MathJax.Hub, "mathbuffer"],copyRendered);
    }

    var copyRendered = function (){
        $("#mathpreview").html($("#mathbuffer").html());
    }

    $(".show-advanced-filters, .hide-advanced-filters").on('click',function(event){
        $(".filters-hidden-section").toggle();
    });

    $('[data-toggle="tooltip"]').tooltip();

    $(this).on('click', '.filter-remove', function (event) {
        if ($('.row .in-use').length > 1) {
            $(this).closest('.row .in-use').remove();
        }
    });

    $(this).on('click', '.filter-add', function () {
        var $row = $("div.in-use:last").clone();
        $.each($row.find(':input[name]'), function () {
            var no = parseInt($(this).attr('name').match(/\d+/)) + 1;
            $(this).attr('name', $(this).attr('name').replace(/(\d+)(?!.*\d)/g, no));
            $(this).attr('id', $(this).attr('id').replace(/(\d+)(?!.*\d)/g, no));
            $(this).find('option:selected').removeAttr("selected");
            $(this).val("");
            if ($(this).is('select[name*="filter_relational_operator_"]')) {
                $(this).attr('readonly', true);
            }
        });
        $row.insertBefore('.button-row');
    });

    $(this).on('change', 'form#aspect_discovery_SimpleSearch_div_search-filters select', function () {
        var $parent = $(this).parents("div.row:first");
        if ($(this).val() === "math") {
            var attrs = {};

            var $input = $parent.find("input:text");
            if ($input.length) {
                $.each($input[0].attributes, function (idx, attr) {
                    if (attr.nodeName !== 'type' && attr.nodeName !== 'value') {
                        attrs[attr.nodeName] = attr.nodeValue;
                    }
                });

                $input.replaceWith($("<textarea />", attrs));
            }
        } else {
            $textarea = $parent.find("textarea");
            if ($textarea.length) {
                attrs = {};
                $.each($textarea[0].attributes, function (idx, attr) {
                    attrs[attr.nodeName] = attr.nodeValue;
                });

                $.extend(attrs, {type: 'text'});

                $textarea.replaceWith($("<input />", attrs));
            }
        }
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

    $(".used-filters span.label").on('click',function(){
        var removeID = $(this).data('remove-input');

        $(".used-filters input").each(function(){
            if($(this).data('remove')==removeID){
                $(this).remove();
            }
        });

        $("#aspect_discovery_SimpleSearch_div_general-query").submit();
    });
});


(function ($) {
    $.fn.email = function (options) {
        var defaults = {user : 'webmaster', a : '@', domain : 'dml', suffix : ".cz" };
        
        var output = $.extend({},defaults,options);
        var href = 'mailto:';
        var label = '';
        $.each(output,function(k,v){
            label+=v;
        });
        href+=label;
        $(this).parent('a').attr('href', href);
        $(this).html(label);
        return this;
    };
}(jQuery));