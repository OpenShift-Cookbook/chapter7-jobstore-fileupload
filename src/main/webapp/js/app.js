(function($) {
	var JobStore = {};
	window.JobStore = JobStore;
	
	var template = function (name) {
        return $('#' + name + '-template').html();
    };

	JobStore.IndexView = Backbone.View.extend({
		render : function() {
			$.ajax('api/v1/companies',{
				method :'GET',
				success : function(data){
					$("#companies").empty();
					$("#companyView").empty();
					if(null != data){
						_.each(data,function(json){
							var companyHTML = Mustache.to_html(template("company"), json);
                            $("#companies").append(companyHTML);
						});
					}
				}
			});
		}
	});
	
	JobStore.JobView = Backbone.View.extend({
		initialize : function(options){
			this.companyId = options.companyId;
		},
		
		render : function(){
			$("#companyView").empty();
			$.ajax('api/v1/companies/'+this.companyId+'/jobs',{
				method : 'GET',
				success : function(data){
					if(null != data){
						_.each(data, function(json){
							var jobHTML = Mustache.to_html(template("job"), json);
                            $("#companyView").append(jobHTML);
						});
					}
				}
			});
		}
	});
	
	JobStore.CompanyFormView = Backbone.View.extend({
		el : $("body"),
		events :{
			'submit': 'saveCompany'
		},
		render : function(){
			$("#companyView").html(template("company-form"));
			return this;
		},
		
		saveCompany : function(event){
			console.log('in saveCompany()');
			event.preventDefault();
			var name = $('input[name=name]').val();
			var description = $('#description').val();
			var contactEmail = $('input[name=contactEmail]').val();
			var data = {
					name: name,
					description : description,
					contactEmail: contactEmail
				};
			$.ajax({
			    type: "POST",
			    url: "api/v1/companies",
			    data: JSON.stringify(data),
			    contentType: "application/json; charset=utf-8",
			    dataType: "json",
			    success: function(data, textStatus, jqXHR){
			    	console.log(data);
			    	router.navigate("home",{trigger:true})
			    },
			    error: function(jqXHR, textStatus, errorThrown) {
			        console.log(jqXHR);
			        console.log(textStatus);
			        console.log(errorThrown);
			    }
			});
		}
	});

	JobStore.Router = Backbone.Router.extend({
		currentView : null,

		routes : {
			"" : "showAllCompanies",
			"home":"showAllCompanies",
			":companyId/jobs" : "jobsForACompany",
			"companies/new":"newCompany"
		},

		changeView : function(view) {
			if (null != this.currentView) {
				this.currentView.undelegateEvents();
				this.currentView = null;
			}
			this.currentView = view;
			this.currentView.render();
		},

		showAllCompanies : function() {
			console.log("in showAllCompanies()...");
			this.changeView(new JobStore.IndexView());
		},

		jobsForACompany : function(companyId) {
			console.log("in jobsForACompany()...");
			this.changeView(new JobStore.JobView({companyId: companyId}));
		},
		newCompany : function(){
			console.log("in newCompany()...");
			this.changeView(new JobStore.CompanyFormView());
		}
		
		
		

	});

	var router = new JobStore.Router();
	Backbone.history.start();

})(jQuery);