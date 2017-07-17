import 'whatwg-fetch';

module.exports = {
    get: function ( url) {

        return fetch(url, {
            method: 'get',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            // 'credentials': 'same-origin'
            'credentials': 'include'


        }).then(function (response) {
            // todo - check if response.status == 403 and redirect to you dont have the right to access this page
            if (response.status === 401) {
                console.log("You dont have rights");
            }
            return response.json();
        });
    },

    post: function (url, body) {

        return fetch(url, {
            method: 'post',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'credentials': 'same-origin',
            body: JSON.stringify(body)
        }).then(function (response) {
            // todo - check if response.status == 403 and redirect to yo dont have the right to access this page
            if (response.status === 401) {
                console.log("You dont have rights");
            }
            return response.json();
        });
    }
    ,
    delete: function (url, body) {

        return fetch(url, {
            method: 'delete',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'credentials': 'same-origin',
            body: JSON.stringify(body)
        }).then(function (response) {
            // todo - check if response.status == 403 and redirect to yo dont have the right to access this page
            if (response.status === 401) {
                console.log("You dont have rights");
            }
            return response.json();
        });
    }
}

