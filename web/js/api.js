/* Fake database, will be replaced by real API */
function DB(key) {
   var store = window.localStorage;

   return {
      get: function() {
         return JSON.parse(store[key] || '{}');
      },

      put: function(data) {
         store[key] = JSON.stringify(data);
      }
   };
}

/* The model */
function Gamebook(db) {

   db = db || DB("gamebook");

   var self = $.observable(this),
      items = db.get();

   self.add = function(data) {
      if (!data.id) {
          data.id = "_" + ("" + Math.random()).slice(2);
      }
      
      items[data.id] = data;
      self.emit("add", data);
   };

   self.edit = function(item) {
      items[item.id] = item;
      self.emit("edit", item);
   };

   self.remove = function(filter) {
      var els = self.items(filter);
      $.each(els, function() {
         delete items[this.id];
      });
      self.emit("remove", els);
   };

   self.toggle = function(id) {
      var item = items[id];
      item.done = !item.done;
      self.emit("toggle", item);
   };

   // @param filter: <empty>, id, "active", "completed"
   self.items = function(filter) {
      var ret = [];
      $.each(items, function(id, item) {
         if (!filter || filter === id || filter === (item.done ? "completed" : "active")) ret.push(item);
      });
      return ret;
   };

   // sync database
   self.on("add remove toggle edit", function() {
      db.put(items);
   });

}
