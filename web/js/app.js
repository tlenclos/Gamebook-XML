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
    window.client.add('user');

    window.gamebook = new Gamebook(client.stories);
    window.user = new User(client.user);

    // HTML for a single gamebook item
    var templateList = $("[type='html/list']").html(),
        templateForm = $("[type='html/form']").html(),
        templateStep = $("[type='html/form-step']").html(),
        templateChoice = $("[type='html/form-choice']").html(),
        templateLogin = $("[type='html/form-user-login']").html(),
        templateRegister = $("[type='html/form-user-register']").html(),
        root = $("#main"),
        nav = $("#filters a")
    ;

    /* Listen to model events */
    gamebook
    .on("add", function() {
        $.notifyBar({html: 'Story saved', position: 'bottom'});
        list();
    })
    .on("remove", function(id) {
        $.notifyBar({html: 'Story deleted', position: 'bottom'});
        $("#" + id).remove();
    })
    .on("edit", function(item) {
        var el = $("#" + item.id);
        $.notifyBar({html: 'Story saved', position: 'bottom'});
    })
    // counts
    .on("add remove", counts);

    user
    .on("login", function(data) {
        if (data.success) {
            list();
        }
        
        $.notifyBar({html: data.message, position: 'bottom'});
    })
    .on("register", function(data) {
        if (data.success) {
            list();
        }
        
        $.notifyBar({html: data.message, position: 'bottom'});
    })
    .on("logout", function(data) {
        if (data.success) {
            $.notifyBar({html: data.message, position: 'bottom'});
            login();
        } else {
            $.notifyBar({html: data.message, position: 'bottom'});
        }
    });

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
        } else if (url.startsWith('login')) {
            login();
        } else if (url.startsWith('register')) {
            register();
        } else if (url.startsWith('logout')) {
            user.logout();
        }
    });

    // private functions
    function login() {
        var loginView = $.el(templateLogin);
        $('input[type="submit"]', loginView).click(function(event) {
            user.login(
                $('input[name=login]').val(),
                $('input[name=password]').val()
            );
            
            event.preventDefault();
        });
        root.html(loginView);
    }
    
    function register() {
        var registerView = $.el(templateRegister);
        
        $('input[type="submit"]', registerView).click(function(event) {
            var data = formToObject($('#form-registration', registerView).serializeArray());
            user.register(data);
            event.preventDefault();
        });
        root.html(registerView);
    }
    
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
            gamebook.remove(item.id);
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
                stepsLayout.append(renderStepLayout(step));
            });
        } else {
            view = $.el(templateForm);
            stepsLayout = $('#steps', view);
            stepsLayout.append(renderStepLayout());
        }

        // Add step
        $('#add-step', view).click(function() {
            var stepData = {
                id: stepsLayout.children().length,
                description: '',
                question: '',
                choices: []
            };
            
            stepsLayout.append(renderStepLayout(stepData));
        });
        
        // Save form
        $('#form-gamebook', view).submit (function(event) {
            var data = formToObject($('#form-gamebook').serializeArray());

            if (item) {
                gamebook.edit(data);
            } else {
                gamebook.add(data);
            }
            
            event.preventDefault();
        });
        
        view.appendTo(root);
        root.append(view);
    }

    function list() {
        // TODO sometime this method is called twice, why?
        // clear list and add new ones
        root.empty();
        gamebook.items(function(stories) {
            $.each(stories, add);
            // Update the counts
            counts();
        });
    }
    
    function renderStepLayout(data) {
        var data = data ? data : {id: 0, description: '', question: ''};
        var view = $.el(templateStep, data);
        var choicesContainer = $('.choices', view);
        
        $('.delete-step', view).click(function() {
            view.remove();
        });
        
        $('.add-choice', view).click(function() {
            var choiceView = renderChoiceLayout({id: data ? data.id : 0, choiceid: choicesContainer.children().length, choice: '', gotostep: ''});
            choicesContainer.append(choiceView);
        });
        
        if (data && data.choices) {
            $.each(data.choices, function(index, choice) {
                var choiceView = renderChoiceLayout({id: data.id, choiceid: choicesContainer.children().length, choice: choice, gotostep: index});
                choicesContainer.append(choiceView);
            });
        }
        
        return view;
    }

    function renderChoiceLayout(data) {
        var view = $.el(templateChoice, data);
        $('.delete-choice', view).click(function() {
            view.remove();
        });
        return view;
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
