/* The model */
function Gamebook(db) {

    var self = $.observable(this);

    self.local = [];

    // TODO
    self.add = function(data) {
        if (!data.id) {
            data.id = "_" + ("" + Math.random()).slice(2);
        }

        db.create(data).done(function(data) {
            callback(data);
            self.emit("add", data);
        });
    };

    // TODO
    self.edit = function(item) {
        items[item.id] = item;
        self.emit("edit", item);
    };

    self.remove = function(id, callback) {
        db.destroy(id).done(function(data) {
            callback(data);
            self.emit("remove", data);
        });
    };

    self.get = function(id, callback) {
        db.read(id).done(function(data) {
            callback(data);
        });
    };

    self.items = function(callback) {
        db.read().done(function(data) {
            self.local = data;

            // TODO called twice at start, why?
            callback(data);
        });
    };
}
