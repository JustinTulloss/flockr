Cobra.install();

var Flockr = new Singleton({
    __init__: function(self) {
        $(document).ready(self.initialize)
    },
    /* Executed when the DOM is ready */
    initialize: function(self) {
        $('.feed .title').bind("click", self.toggleFeed);
    },

    toggleFeed: function(self, e) {
        e.stopPropagation();
        $(e.target).siblings(".tweet").slideToggle('fast');
    }
});
