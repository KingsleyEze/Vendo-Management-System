var path = require("path");
var webpack = require('webpack');
var BundleTracker = require('webpack-bundle-tracker')
;


var LodashModuleReplacementPlugin = require('lodash-webpack-plugin');


var CleanWebpackPlugin = require('clean-webpack-plugin');

module.exports = {
  context: __dirname,

  entry: {

      vendorselect :['babel-polyfill', "./components/vendorSelect/App.jsx"]
      //enterVendorSelect :[ "./components/vendorSelect/vendorSelectApp.jsx"],




},
    // entry point of our app. assets/js/index.js should require other js modules and dependencies it needs

  output: {
      path: path.resolve('../../bundles/'),
      filename: "[name].js",
  },

  plugins: [

       new LodashModuleReplacementPlugin,
       new webpack.optimize.OccurrenceOrderPlugin,
    new BundleTracker({filename: '../../webpack-stats.json'}),
    new webpack.DefinePlugin({
     'process.env':{
       'NODE_ENV': JSON.stringify('production')
     }
   }),

    new CleanWebpackPlugin(['bundles'], {
     root: '/Users/macbook/java_projects/vdmsNG/src/main/resources/static/',
     verbose: true,
     dry: false
   }),
   //makes jQuery available in every module
           new webpack.ProvidePlugin({
               $: 'jquery',
               jQuery: 'jquery',
               'window.jQuery': 'jquery'
           }),
   /*new webpack.optimize.UglifyJsPlugin({
    compress: {
        warnings: false
    }
})*/
  ],

  module: {
    loaders: [
      { test: /\.jsx?$/, exclude: /node_modules/, loader: 'babel',
      query:
            {
                 'plugins': ['lodash'],
              presets:['es2015','react','stage-0']
            }
       }, // to transform JSX into JS
    ],
  },

  resolve: {
    modulesDirectories: ['node_modules', 'bower_components'],
    extensions: ['','.js',  '.jsx']
  },
}
;


