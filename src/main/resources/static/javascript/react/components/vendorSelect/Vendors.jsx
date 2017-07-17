/**
 * Created by macbook on 4/4/17.
 */
/**
 * Created by macbook on 4/4/17.
 */


import React from 'react';
import Vpa from  './Vpa';
var Api = require('../../util/Api');

var _ = require('lodash');
class Vendors extends React.Component {
    constructor() {
        super();

        this.state={

            selVpas : [],
        };

    }


    getVendors(vendor) {
        // console.log(vendor);
        return (   <option value={vendor}>{vendor} </option>)

    }

    isAutobot(transformer) {

        return transformer.usertype === this.props.selectedVendor;
    }

    getVpaForVendor() {
        var selectedVendor = this.refs.vendorCategory.value;

    this.props.selectedVendor = selectedVendor;
        var filtered = this.props.vpas.filter(this.isAutobot.bind(this));

        console.log("THe filtered set");

        console.log(filtered);
        this.state.selVpas = filtered;
        this.setState({ selVpas : filtered});


    }

    getSelectedVpas(){

        return this.state.selVpas;
    }

    render() {

        var vndos = JSON.parse(this.props.vendors);

        return (
            <div>
                <h5 className="lg-title">Vendor Category </h5>
                <p className="mb20"> Choose any 1 of 3 Vendor Category</p>
                <div className="select2-container width300" id="s2id_select-basic">

                    <select id="select-vendors" data-placeholder="Choose One" ref="vendorCategory"
                            onChange={this.getVpaForVendor.bind(this)} className="width300 select2"
                            title="">

                        <option>--</option>
                        { Object.values(vndos.vendor).map(this.getVendors.bind())}

                    </select>


                </div>


                <div className="mb30"></div>


                <Vpa selectedVpas={this.getSelectedVpas.bind()} />
            </div>
        );
    }

}

export  default Vendors;