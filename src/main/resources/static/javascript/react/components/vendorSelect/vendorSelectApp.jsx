/**
 * Created by macbook on 4/4/17.
 */


/**
 * Created by abdulhakim on 11/20/16.
 */
var Api = require('../../util/Api');

var _ =require('lodash');
import React from 'react';
import TimeAgo from 'react-timeago';
import Vendors from  './Vendors';
class VendorSelectApp extends React.Component {

    constructor() {
        super();

        this.state={

            vpas : [],
        };

    }

    componentDidMount() {
        var th = this;
        Api.get('/api/vpas')
            .then(function (response) {
                console.log(response);
                th.state.vpas = response;
                th.setState({vpas:th.state.vpas})
            })
            .catch(function (error) {
                console.log(error);
            });

    }

    render() {
        // console.log(JSON.stringify(this.state.comments));

        return (<div>

                <Vendors vendors={this.props.config.vendors} vpas={this.state.vpas} />


        <div className="mb30"></div>

            <div className="panel-footer">
            <button className="btn btn-primary mr5">Complete Registration</button>

        </div>
            </div>
        );

    }
}
;

export default VendorSelectApp;
