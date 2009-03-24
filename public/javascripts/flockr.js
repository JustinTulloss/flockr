/* Copyright (C) 2009 Justin Tulloss
* 
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

Also add information on how to contact you by electronic and paper mail.
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
