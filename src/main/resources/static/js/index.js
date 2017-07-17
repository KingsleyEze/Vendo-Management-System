/**
 * Created by macbook on 4/4/17.
 */


import React from 'react';

import  App from './components/App';
import Actions from './actions';
import ReactDom from 'react-dom';

//setInterval(Actions.getComments(config.loggedInUser), 2000);





//ReactDom.render(<App />, document.querySelector('#searcFm'));
ReactDom.render(<App  pollInterval={3000}  config={config} loggedInUser={config.loggedInUser} staticUrl={config.staticUrl} base_url={config.baseUrl} />, document.querySelector('#comments'));


setInterval(Actions.getComments.bind(window, config.appointmentId), 4000);

//console.log(config.staticUrl);
