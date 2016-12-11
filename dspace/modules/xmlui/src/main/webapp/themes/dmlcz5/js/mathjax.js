/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



//
//  Use a closure to hide the local variables from the
//  global namespace
//
(function () {
    var QUEUE = MathJax.Hub.queue;  // shorthand for the queue
    var math = null, box = null;    // the element jax for the math output, and the box it's in

    //
    //  Hide and show the box (so it doesn't flicker as much)
    //
    var HIDEBOX = function () {box.style.visibility = "hidden"}
    var SHOWBOX = function () {box.style.visibility = "visible"}

    //
    //  Get the element jax when MathJax has produced it.
    //
    QUEUE.Push(function () {
        math = MathJax.Hub.getAllJax("MathOutput")[0];
        box = document.getElementById("box");
        SHOWBOX(); // box is initially hidden so the braces don't show
    });

    //
    //  The onchange event handler that typesets the math entered
    //  by the user.  Hide the box, then typeset, then show it again
    //  so we don't see a flash as the math is cleared and replaced.
    //
    window.UpdateMath = function (TeX) {
        QUEUE.Push(
            HIDEBOX,
            ["resetEquationNumbers",MathJax.InputJax.TeX],
            ["Text",math,"\\displaystyle{"+TeX+"}"],
            SHOWBOX
        );
    }
})();
