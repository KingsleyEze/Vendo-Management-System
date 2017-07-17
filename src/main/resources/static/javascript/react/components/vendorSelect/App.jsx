/**
 * Created by macbook on 4/4/17.
 */


import React from 'react';

import  VendorSelectApp from './VendorSelectApp';

import ReactDom from 'react-dom';

//setInterval(Actions.getComments(config.loggedInUser), 2000);





//ReactDom.render(<App />, document.querySelector('#searcFm'));
ReactDom.render(<VendorSelectApp  pollInterval={3000}  config={config} />, document.querySelector('#vendorselectapp'));


//setInterval(Actions.getComments.bind(window, config.appointmentId), 4000);

//console.log(config.staticUrl);
