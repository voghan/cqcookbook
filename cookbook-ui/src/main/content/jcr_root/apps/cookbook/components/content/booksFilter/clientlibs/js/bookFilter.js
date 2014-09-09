var booksFilter = {
    init: function() {

        var that = this;

        $('.authorFilter').change(function() {
            console.log("...item selected on authorSelector");
            that.filterChanged();
        });
        
        $('.genreFilter').change(function() {
            console.log("...item selected on genreFilter");
            that.filterChanged();
        });
    },

    filterChanged: function () {
    	var that = this;
    	
    	//Empty the results list
    	$('.book-results-list').empty();
    	
		var authorTag = $('.authorFilter').val();
		var genreTag = $('.genreFilter').val();
		
		var values = "authorTagId=" +authorTag +"&genreTagId=" +genreTag;
		
		$.ajax({
		      url: "/services/cookbook/books",
		      type: "get",
		      data: values,
		      success: function(data, textStatus, jqXHR){
		          that.processBookData(data);
		      },
		      error:function(){
		          alert("failure");
		      }   
		    }); 

	},
	
	processBookData: function (data) {
        var that = this;
        
        $.each(data.data, function (index, book) {
        	var template = $('#book-template').clone(true);

        	template.find('[data-selector="bookTitle"]').text(book.title);
        	template.find('[data-selector="bookAuthor"]').text(book.author);
        	template.find('.results-book_url').attr('href',book.path+'.html');
            template.appendTo('.book-results-list');
        });
    }

}


