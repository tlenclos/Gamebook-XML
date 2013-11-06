String.prototype.startsWith = function(needle)
{
    return (this.indexOf(needle) === 0);
};

/* The presenter */

(function() {
    'use strict';
    /*
     A Model instance. Exposed to global space so it can be used
     on the browser's console. Try for example:

     gamebook.add("My gamebook story");
     */
    window.gamebook = new Gamebook();

    // HTML for a single gamebook item
    var templateList = $("[type='html/list']").html(),
            templateForm = $("[type='html/form']").html(),
            root = $("#main"),
            nav = $("#filters a");

    /* Listen to model events */
    gamebook
    .on("add", add)
    .on("remove", function(items) {
        $.each(items, function() {
            $("#" + this.id).remove();
        });
    })
    .on("edit", function(item) {
        var el = $("#" + item.id);
    })
    // counts
    .on("add remove", counts);

    // routing
    nav.click(function() {
        return $.route($(this).attr("href"));
    });

    $.route(function(hash) {
        var url = hash.slice(2);

        if (url.startsWith('list') || url === '') {
            url = url.slice('5');
            list(url);
        } else if (url.startsWith('create')) {
            edit(null);
        } else if (url.startsWith('edit')) {
            var id = url.slice(5);
            edit(gamebook.items(id)[0])
        }

        // Update the counts
        counts();
    });

    // private functions
    // Update view after adding a model
    function add(item) {
        if (this.id)
            item = this;
        var el = $.el(templateList, item).appendTo(root);

        // Edit control
        $(".edit", el).click(function() {
            edit(item);
        });

        // Delete control
        $(".delete", el).click(function() {
            console.log(item);
            gamebook.remove(item.id);
            list();
        });
    };

    function edit(item) {
        root.empty();
        var view;

        if (item) {
            view = $.el(templateForm, item);
        } else {
            view = $.el(templateForm);
        }

        $('#form-gamebook', view).submit (function(event) {
            var data = formToObject($('#form-gamebook').serializeArray());
            $.extend(data, item);
            console.log(data);
            gamebook.add(data);
            list();
            event.preventDefault();
        });

        view.appendTo(root);
    }

    function list(filter) {
        // clear list and add new ones
        root.empty() && $.each(gamebook.items(filter), add);

        // selected class
        nav.removeClass("selected").filter("[href='" + filter + "']").addClass("selected");
    }

    function counts() {
        var total = gamebook.items().length;
        $("#gamebook-count").html("<strong>" + total + "</strong>");
    }

    function formToObject(formData) {
        var dataObject = {};
        $.each(formData, function(index, data) {
            dataObject[data.name] = data.value;
        });
        return dataObject;
    }
})();
