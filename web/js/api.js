var filterJson = function(json) {
    var data = [];
    $.each(json, function(index, item) {
        if (typeof item === "object")
            data.push(item);
    });
    return data;
};

/* User model */
function User(db) {
    var self = $.observable(this);

    self.login = function(login, password) {
        db.read('login/'+login+'/'+password).done(function(data) {
            self.emit("login", data);
        });
    };

    self.logout = function() {
        db.read('logout').done(function(data) {
            self.emit("logout", data);
        });
    };

    self.register = function(data) {
        db.read('register/'+data.login+'/'+data.password+'/'+data.firstname+'/'+data.lastname).done(function(data) {
            self.emit("register", data);
        });
    };
}

/* Gamebook model */
function Gamebook(db) {
    var self = $.observable(this);

    self.local = [];

    self.add = function(data) {
        if (!data.id) {
            data.id = "_" + ("" + Math.random()).slice(2);
        }

        db.create(data).done(function(data) {
            self.emit("add", filterJson(data));
        }).fail(function(data) {
            self.emit("add", JSON.parse(data.responseText).msg);
        });;
    };

    self.edit = function(data) {
        db.update(data.id, data).done(function(data) {
            self.emit("edit", filterJson(data));
        }).fail(function(data) {
            self.emit("edit", JSON.parse(data.responseText).msg);
        });
    };

    self.remove = function(id) {
        db.destroy(id).done(function(data) {
            self.emit("remove", id);
        }).fail(function(data) {
            self.emit("remove", JSON.parse(data.responseText).msg);
        });
    };

    self.get = function(id, callback) {
        db.read(id).done(function(data) {
            callback(data);
        });
    };

    self.items = function(callback) {
        db.read().done(function(data) {
            if (data) { // TODO called twice at start, why?
                data = filterJson(data);
                self.local = data;
                callback(data);
            }
        });
    };
}
