var CB = {
    unloaded: false,

    init: function () {
        console.log("...init your js ");
        
        CB.booksFilter !== undefined ? CB.booksFilter.init() : null;
    }
};

$(document).ready(function () {
    CB.init();
});