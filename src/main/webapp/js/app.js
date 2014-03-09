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
			$("#jobs").empty();
			$.ajax('api/v1/companies/'+this.companyId+'/jobs',{
				method : 'GET',
				success : function(data){
					if(null != data){
						_.each(data, function(json){
							var jobHTML = Mustache.to_html(template("job"), json);
                            $("#jobs").append(jobHTML);
						});
					}
				}
			});
		}
	});

	JobStore.Router = Backbone.Router.extend({
		currentView : null,

		routes : {
			"" : "showAllCompanies",
			":companyId/jobs" : "jobsForACompany"
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
		}

	});

	var router = new JobStore.Router();
	Backbone.history.start();

})(jQuery);