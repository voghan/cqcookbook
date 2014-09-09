var locations = {
    init: function() {

        var that = this;
        
        $("#loactionSearch").submit(function(event) {
        	  /* stop form from submitting normally */
        	  event.preventDefault();
        	  
        	  console.log("...loactionSearch submitted");
              that.search(this);
        });

    },
    search: function(form) {
    	var that = this;
    	
    	var values = $(this).serialize();
    	console.log(values);
    	
    	//Empty the results list
    	$('.location-results-list').empty();
    	
    	$.ajax({
		      url: "/services/cookbook/stores",
		      type: "get",
		      data: values,
		      success: function(data, textStatus, jqXHR){
		          that.processLocationData(data);
		      },
		      error:function(){
		          alert("failure");
		      }   
		    }); 
    },
    processLocationData: function(data) {
    	var that = this;
        
        $.each(data.data, function (index, location) {
        	var template = $('#location-template').clone(true);

        	template.find('.results-location_url').attr('href',location.path+'.html');
        	template.find('[data-selector="city"]').text(location.city);
        	template.find('[data-selector="address"]').text(location.address);
        	template.find('[data-selector="state"]').text(location.state);
        	template.find('[data-selector="phone"]').text(location.phone);
        	template.find('[data-selector="address"]').text(location.address);
        	template.find('[data-selector="hours"]').text(location.hours);

            template.appendTo('.location-results-list');
        });
    }
}