$(function () {
    var typeCheckDelay = 800;
    var typingTimer = 0;
    var $input = $("#MathInput");
    var $errorRow = $("div.mathjax-error-row");

    /**
     * Function which is called after user is done with typing
     */
    var doneTyping = function () {
        $("#mathbuffer").text($input.val());
        $errorRow.addClass('invisible');
        MathJax.Hub.Queue(["Typeset", MathJax.Hub, "mathbuffer"], copyRendered);
    };

    /**
     * Callback for typesetting. When math rendering is done to
     * mathbuffer element then it simply copies the output to visible element
     */
    var copyRendered = function () {
        $("#mathpreview").html($("#mathbuffer").html());
        console.log("copied");
    };

    /**
     * Listener for user type action. It also checks for errors produces by error hook
     */
    $input.on("keyup", function () {
        clearTimeout(typingTimer);
        typingTimer = setTimeout(doneTyping, typeCheckDelay);
    });

    /**
     * Listener for user type action
     */
    $input.on("keydown", function () {
        clearTimeout(typingTimer);
    });


    // this is because if user has requested any math it is copied into
    // #mathbuffer, so we need to trigger fake typing which will copy buffer
    // into render field.
    doneTyping();

    MathJax.Hub.Register.MessageHook("TeX Jax - parse error", function (data) {
        $errorRow.find("input").val(data[1]);
        $errorRow.removeClass('invisible');
    });
});