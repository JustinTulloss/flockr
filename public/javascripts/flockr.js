Cobra.install();

var Flockr = new Singleton({
    __init__: function(self) {
        $(document).ready(self.initialize)
    },

    /* Executed when the DOM is ready */
    initialize: function(self) {
        $('.feed .title').bind("click", self.toggleFeed);
        $('.feed .title .remove').bind("click", self.removeFeed);
        $('#col1, #col2').sortable({
            connectWith: '.feed-column',
            placeholder: 'feed-panel panel-placeholder'
        });
    },

    toggleFeed: function(self, e) {
        e.stopPropagation();
        $(e.target).siblings(".tweets").slideToggle('fast');
    },

    removeFeed: function(self, e) {
        e.stopPropagation();
        $(e.target).parents('.feed').remove();
    }
});
