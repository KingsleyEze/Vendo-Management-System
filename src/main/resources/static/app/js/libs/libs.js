window.$ = window.jQuery = require('jquery');

window.angular = require('angular');
window.moment = require('moment');

require('angular-route');
require('angular-resource');


require('../../client');
require('../../follow');

require('when');

const root =  window.config.baseUrl + 'api';
