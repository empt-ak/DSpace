/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$('#my-email').html(function () {
    var e = "help";
    var a = "@";
    var d = "dml";
    var c = ".cz";
    var h = 'mailto:' + e + a + d + c;
    $(this).parent('a').attr('href', h);
    return e + a + d + c;
});

$(document).ready(function () {
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
//    $("#aspect_discovery_SimpleSearch_div_search-filters").submit(function (e) {
//        $("#dummyFilterRow").remove();
//        //console.log($(this).serializeArray());
//
//        e.preventDefault();
//    });
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
//    var preview = Preview.Init({preview: "mathPreview", buffer: "mathBuffer", input: "mathInput"});
////    $("#mathInput").on('keyup', function () {
////        /**                 $.get('https://mir.fi.muni.cz/cgi-bin/latex-to-mathml-via-latexml.cgi', {'formula':$("#mathInput").val()}, function(response){
////         console.log(response); */
////    });
//
//    preview.update();

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
});
