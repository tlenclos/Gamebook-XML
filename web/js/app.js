String.prototype.startsWith = function(needle)
{
    return (this.indexOf(needle) === 0);
};

/* The presenter */

(function() {
    'use strict';

    // Init
    window.client = new $.RestClient('/Gamebook-XML/web/api.php/'); // TODO change Url
    window.client.add('stories');

    window.gamebook = new Gamebook(client.stories);

    // HTML for a single gamebook item
    var templateList = $("[type='html/list']").html(),
        templateForm = $("[type='html/form']").html(),
        templateStep = $("[type='html/form-step']").html(),
        root = $("#main"),
        nav = $("#filters a")
    ;

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
            list();
        } else if (url.startsWith('create')) {
            edit(null);
        } else if (url.startsWith('edit')) {
            var id = url.slice(5);
            gamebook.get(id, function(story) {
                edit(story);
            });
        }
    });

    // private functions
    // Update view after adding a model
    function add(index, item) {
        if (this.id)
            item = this;

        var el = $.el(templateList, item).appendTo(root);

        // Edit control
        $(".edit", el).click(function() {
            edit(item);
        });

        // Delete control
        $(".delete", el).click(function() {
            gamebook.remove(item.id, function(data) {
                list();
            });
        });
    };

    function edit(item) {
        var
            view,
            stepsLayout
        ;
        root.empty();
        
        if (item) {
            view = $.el(templateForm, item);
            stepsLayout = $('#steps', view);
            
            $.each(item.steps, function(index, step) {
                var subformStep = $.el(templateStep, step);
                stepsLayout.append(subformStep);
            });
        } else {
            view = $.el(templateForm);
            stepsLayout = $('#steps', view);
            var subformStep = $.el(templateStep);
            stepsLayout.append(subformStep);
        }

        // Add step
        $('#add-step', view).click(function() {
            stepsLayout.append($.el(templateStep));
        });
        
        // Save form
        $('#form-gamebook', view).submit (function(event) {
            var data = formToObject($('#form-gamebook').serializeArray());
            $.extend(data, item);
            gamebook.add(data, function(response) {
                list();
            });

            event.preventDefault();
        });
        
        view.appendTo(root);
        root.append(view);
    }

    function list() {
        // TODO sometime this method is called twice, why?
        // clear list and add new ones
        gamebook.items(function(stories) {
            root.empty();
            $.each(stories, add);
            // Update the counts
            counts();
        });
    }

    function counts() {
        var total = gamebook.local.length;
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
