/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var Preview = {
    delay: 150, // delay after keystroke before updating 

    preview: null, // filled in by Init below
    buffer: null, // filled in by Init below
    input: null,
    timeout: null, // store setTimout id
    mjRunning: false, // true when MathJax is processing
    oldText: null, // used to check if an update is needed
    mathmlElement: null,
    //
    //  Get the preview and buffer DIV's
    //
    Init: function (data) {
        this.preview = document.getElementById(data.preview);
        this.buffer = document.getElementById(data.buffer);
        this.input = document.getElementById(data.input);
        this.mathmlElement = document.getElementById(data.mathmlElement);
        //console.log(this);
        return this;
    },
    //
    //  Switch the buffer and preview, and display the right one.
    //  (We use visibility:hidden rather than display:none since
    //  the results of running MathJax are more accurate that way.)
    //
    swapBuffers: function () {
        var buffer = this.preview, preview = this.buffer;
        this.buffer = buffer;
        this.preview = preview;
        buffer.style.visibility = "hidden";
        buffer.style.position = "absolute";
        preview.style.position = "";
        preview.style.visibility = "";
    },
    //
    //  This gets called when a key is pressed in the textarea.
    //  We check if there is already a pending update and clear it if so.
    //  Then set up an update to occur after a small delay (so if more keys
    //    are pressed, the update won't occur until after there has been 
    //    a pause in the typing).
    //  The callback function is set up below, after the Preview object is set up.
    //
    update: function () {
        if (this.timeout) {
            clearTimeout(this.timeout);
        }
        this.timeout = setTimeout(this.callback, this.delay);
    },
    //
    //  Creates the preview and runs MathJax on it.
    //  If MathJax is already trying to render the code, return
    //  If the text hasn't changed, return
    //  Otherwise, indicate that MathJax is running, and start the
    //    typesetting.  After it is done, call PreviewDone.
    //  
    createPreview: function () {
        Preview.timeout = null;
        if (this.mjRunning)
            return;
        var text = this.input.value;
        if (text === this.oldtext)
            return;
        this.buffer.innerHTML = this.oldtext = text;
        this.mjRunning = true;
        MathJax.Hub.Queue(
                ["Typeset", MathJax.Hub, this.buffer],
                ["previewDone", this]
                );
    },
    //
    //  Indicate that MathJax is no longer running,
    //  and swap the buffers to show the results.
    //
    previewDone: function () {
        this.mjRunning = false;
        this.swapBuffers();
    }

};

//
//  Cache a callback to the CreatePreview action
//
Preview.callback = MathJax.Callback(["createPreview", Preview]);
Preview.callback.autoReset = true;  // make sure it can run more than once