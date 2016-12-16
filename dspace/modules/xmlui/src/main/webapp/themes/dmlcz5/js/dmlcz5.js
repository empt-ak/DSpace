/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.addEventListener("load", function () {
    window.cookieconsent.initialise({
        "palette": {
            "popup": {
                "background": "#237afc"
            },
            "button": {
                "background": "transparent",
                "text": "#fff",
                "border": "#fff"
            }
        }
    })
});

$(function () {
    $(".contact-email").email();

    if (typeof(Storage) !== "undefined") {
        if (localStorage.getItem("hideWelcomeBanner") == null) {
            $("div#welcome-banner").removeClass("hidden-xl-down");
        }
    }

    $("div#welcome-banner button.close").on("click", function () {
        localStorage.setItem("hideWelcomeBanner", true);
        console.log($(this));
        $(this).parent().hide();
    });

    var typingTimer;
    var $input = $("#MathInput");

    $input.on("keyup", function () {
        clearTimeout(typingTimer);

        typingTimer = setTimeout(doneTyping, 800);
    });

    $input.on("keydown", function () {
        clearTimeout(typingTimer);
    });

    var doneTyping = function () {
        $("#mathbuffer").text($input.val());

        MathJax.Hub.Queue(["Typeset", MathJax.Hub, "mathbuffer"], copyRendered);
    };

    var copyRendered = function () {
        $("#mathpreview").html($("#mathbuffer").html());
        console.log("copied");
    };

    // this is because if user has requested any math it is copied into
    // #mathbuffer, so we trigger fake typing which will copy buffer
    // into render field.
    doneTyping();

    $("span.copyright-date").text(new Date().getFullYear());

    $("a.show-math-help").on("click", function () {
        $("div#math-help").modal("toggle");
    });


    $(".toggle-filters").on('click',function () {
        $(".filters-hidden-section").toggleClass('hidden-xs-up');
    });

    $("[data-toggle='tooltip']").tooltip({
        "animation": false,
    });

    $(this).on("click", ".filter-remove", function () {
        if ($(".row .in-use").length > 1) {
            $(this).closest(".row .in-use").remove();
        }
    });

    $(this).on("click", ".filter-add", function () {
        var $row = $("div.in-use:last").clone();
        $.each($row.find(":input[name]"), function () {
            var no = parseInt($(this).attr("name").match(/\d+/)) + 1;
            $(this).attr("name", $(this).attr("name").replace(/(\d+)(?!.*\d)/g, no));
            $(this).attr("id", $(this).attr("id").replace(/(\d+)(?!.*\d)/g, no));
            $(this).find("option:selected").removeAttr("selected");
            $(this).val("");
        });
        $row.insertBefore(".button-row");
    });


    // because of mobile layout form contains select which shouldn't be submited by default
    // TODO handle mobile submit
    $("form#aspect_artifactbrowser_ConfigurableBrowse_div_browse-navigation").on("submit",function (event) {
        var $select = $(this).find("select#jump-select");
        if(!$select.is(":visible")){
            $select.attr("disabled","disabled");
        }
    });


    $("select#jump-select").on('change',function () {
       $("<form/>").attr({
           "action" : $(this).val(),
           "method" : "get",
       }).submit();
    });

    $("form#aspect_artifactbrowser_ConfigurableBrowse_div_browse-controls a").on("click", function () {
        var $form = $(this).closest("form");

        var $input = $form.find("input[name='" + $(this).data("name") + "']");

        if ($input.length) {
            $input.val($(this).data("value"));
        } else {
            $("<input/>").attr({
                "name": $(this).data("name"),
                "type": "hidden",
                "value": $(this).data("value")
            }).appendTo($form);
        }

        $form.submit();
    });

    $(".used-filters span.badge i.fa-close").on("click", function (event) {
        var removeID = $(this).data("remove-input");

        $(".used-filters input").each(function () {
            if ($(this).data("remove") === removeID) {
                $(this).remove();
            }
        });
        $("#aspect_discovery_SimpleSearch_div_general-query").submit();
    });

    var checkScrollTopButton = function () {
        if ($(document).height() > $(window).height()) {
            $("#scroll-top").parent().removeClass("hidden-xl-down");
        } else {
            $("#scroll-top").parent().addClass("hidden-xl-down");
        }
    };

    checkScrollTopButton();

    $(window).on("resize", function () {
        checkScrollTopButton();
    });

    $("#scroll-top").on("click", function (e) {
        e.preventDefault();
        $("html,body").animate({
            scrollTop: 0
        }, 400);
    });

    $("form#aspect_discovery_SimpleSearch_div_search-filters").on('submit',function(event){
        event.preventDefault();

        var $form = $(this).clone();

        // first fix selects because if they are removed
        // matching would be hard (e.g. done by finding
        // by name value)
        var $selects = $(this).find("select");

        $selects.each(function(i){
            var select = this;
            $form.find("select").eq(i).val($(select).val());
        });

        // find empty <input type="text" />
        $form.find("input:not([type=hidden])").filter(function(){
            return !this.value;
        }).closest("div.row").remove(); //and remove those rows

        // check if user has added any math, if not remove it
        if(!$.trim($form.find("#MathInput").val())){
            $form.find(".math-row").remove();
        }

        console.log($form.serialize());
        $form.submit();
    });
});


(function ($) {
    $.fn.email = function (options) {
        var defaults = {user: "webmaster", a: "@", domain: "dml", suffix: ".cz"};

        var output = $.extend({}, defaults, options);
        var href = "mailto:";
        var label = "";
        $.each(output, function (k, v) {
            label += v;
        });
        href += label;
        $(this).parent("a").attr("href", href);
        $(this).html(label);
        return this;
    };
}(jQuery));