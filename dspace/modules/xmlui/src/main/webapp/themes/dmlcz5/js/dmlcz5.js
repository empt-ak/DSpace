/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    $('#my-email').email();

    $(".show-advanced-filters").click(function () {
        $("#aspect_discovery_SimpleSearch_div_search-filters").show();
        $(this).hide();
        $(".hide-advanced-filters").show();
    });
    $(".hide-advanced-filters").click(function () {
        $("#aspect_discovery_SimpleSearch_div_search-filters").hide();
        $(this).hide();
        $(".show-advanced-filters").show();
    });

    $(this).on('click', '.filter-remove', function (e) {
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
        });
        $row.insertBefore('.button-row');
    });

    $("#extended-controls").append(function () {
        var symbols = "!\"#$%&amp;'()*+,-./0123456789:;&lt;=&gt;?@";
        var $table = $("&lt;table&gt;");
        var ul = $("&lt;tr&gt;&lt;td&gt;&lt;ul&gt;");
        var breakAfter = 5;
        $.each(symbols.split(""), function (i, v) {
            if (i % breakAfter === 0) {
                ul = $("&lt;tr&gt;&lt;td&gt;&lt;ul&gt;");
                console.log(ul);
                $table.append(ul);
            }
            var li = $("&lt;li&gt;").html(v);
            ul.append(li);
            console.log(i + ": " + v);
        });
        console.log($table);
        return $table;
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
    
    $("#aspect_artifactbrowser_ConfigurableBrowse_div_browse-controls a").on('click',function(){
        var name = $(this).data('name');
        var val = $(this).data('value');
        $("#aspect_artifactbrowser_ConfigurableBrowse_div_browse-controls select[name='"+name+"']").val(val).change();
        $("#aspect_artifactbrowser_ConfigurableBrowse_div_browse-controls").submit();
    });
});


(function ($) {
    $.fn.email = function () {
        var e = "help";
        var a = "@";
        var d = "dml";
        var c = ".cz";
        var h = 'mailto:' + e + a + d + c;
        $(this).parent('a').attr('href', h);
        $(this).html(e + a + d + c);
        return this;
    };
}(jQuery));