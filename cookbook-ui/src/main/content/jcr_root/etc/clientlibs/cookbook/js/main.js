var CB = {
    unloaded: false,

    init: function () {
        console.log("...init your js ");
    },
    
 // Ref: http://www.quirksmode.org/js/cookies.html
    createCookie: function (name, value, days) {
        var expires = '';

        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            expires = '; expires=' + date.toGMTString();
        }

        if (expires !== '') {
            document.cookie = name + '=' + value + expires + '; path=/';
        }
        else {
            document.cookie = name + '=' + value + '; path=/';
        }
    },

    // Ref: http://www.quirksmode.org/js/cookies.html
    readCookie: function (name) {
        var nameToken = name + '=';
        var cookies = document.cookie.split(';');

        for (var i = 0; i < cookies.length; i++) {
            var cookie = cookies[i];

            while (cookie.charAt(0) == ' ') {
                cookie = cookie.substring(1, cookie.length);
            }

            if (cookie.indexOf(nameToken) == 0) {
                return cookie.substring(nameToken.length, cookie.length);
            }
        }

        return null;
    }
};

$(document).ready(function () {
    CB.init();
});