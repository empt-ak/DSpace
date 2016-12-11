/*
 *  jquery-boilerplate - v4.0.0
 *  A jump-start for jQuery plugins development.
 *  http://jqueryboilerplate.com
 *
 *  Made by Zeno Rocha
 *  Under MIT License
 */
// the semi-colon before function invocation is a safety net against concatenated
// scripts and/or other plugins which may not be closed properly.
;(function ($, window, document, undefined) {

    "use strict";

    // undefined is used here as the undefined global variable in ECMAScript 3 is
    // mutable (ie. it can be changed by someone else). undefined isn't really being
    // passed in so we can ensure the value of it is truly undefined. In ES5, undefined
    // can no longer be modified.

    // window and document are passed through as local variable rather than global
    // as this (slightly) quickens the resolution process and can be more efficiently
    // minified (especially when both are regularly referenced in your plugin).

    // Create the defaults once
    var pluginName = "dmlmath",
        defaults = {
            "delay": 150,
            "preview": null,
            "buffer": null,
            "input": null,
        };

    // The actual plugin constructor
    function Plugin(element, options) {
        this.element = element;

        // jQuery has an extend method which merges the contents of two or
        // more objects, storing the result in the first object. The first object
        // is generally empty as we don't want to alter the default options for
        // future instances of the plugin
        this.settings = $.extend({}, defaults, options);
        this._defaults = defaults;
        this._name = pluginName;
        this.inputElement = null;
        this.previewElement = null;
        this.bufferElement = null;
        this.mjRunning = false;
        this.mjPending = false;
        this.timeout = 0;
        this.oldtext = null;
        this.init();
    }

    // Avoid Plugin.prototype conflicts
    $.extend(Plugin.prototype, {
        init: function () {
            this.inputElement = $(this.settings["input"]);
            this.previewElement = $(this.settings["preview"]);
            this.bufferElement = $(this.settings["buffer"]);
            // Place initialization logic here
            // You already have access to the DOM element and
            // the options via the instance, e.g. this.element
            // and this.settings
            // you can add more functions like the one below and
            // call them like the example bellow
            // this.yourOtherFunction("jQuery Boilerplate");

            console.log(this.inputElement);
            var plugin = this;
            this.inputElement.on('keyup', function () {
                if (plugin.timeout) {
                    clearTimeout(plugin.timeout)
                }
                console.log('keyyyup');
                plugin.timeout = setTimeout(function () {
                    this.fetchPreview();
                }, plugin.settings["delay"]);
            });
        },
        fetchPreview: function () {
            this.timeout = 0;
            if (!this.mjPending) {
                var text = this.inputElement.val();
                if (text !== this.oldtext) {
                    if (this.mjRunning) {
                        this.mjPending = true;
                        MathJax.Hub.Queue(["fetchPreview", this]);
                    } else {
                        this.bufferElement.text(text);
                        this.oldtext = text;
                        this.mjRunning = true;

                        MathJax.Hub.Queue(
                            ["Typeset", MathJax.Hub, this.bufferElement[0]],
                            ["previewDone", this]
                        );
                    }
                }
            }
        }.bind(this),
        previewDone: function () {
            this.mjRunning = false;
            this.mjPending = false;
            this.swapBuffers();
        },
        swapBuffers: function () {
            var buffer = this.previewElement;
            var preview = this.bufferElement;
            this.bufferElement = buffer
            this.previewElement = preview;

            buffer.css({"visibility": "hidden", "position": "aboslute"});
            preview.css({"position": "default", "visibility": "default"});
        }
    });

    // A really lightweight plugin wrapper around the constructor,
    // preventing against multiple instantiations
    $.fn[pluginName] = function (options) {
        return this.each(function () {
            if (!$.data(this, "plugin_" + pluginName)) {
                $.data(this, "plugin_" +
                    pluginName, new Plugin(this, options));
            }
        });
    };

})(jQuery, window, document);