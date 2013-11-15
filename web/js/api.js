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
            self.emit("add", data);
        });
    };

    self.edit = function(data) {
        db.update(data.id, data).done(function(data) {
            self.emit("edit", data);
        });
    };

    self.remove = function(id) {
        db.destroy(id).done(function(data) {
            self.emit("remove", id);
        });
    };

    self.get = function(id, callback) {
        db.read(id).done(function(data) {
            callback(data);
        });
    };

    self.items = function(callback) {
        db.read().done(function(data) {
            if (Array.isArray(data)) // TODO called twice at start, why?
                callback(data);
        });
    };
}
