/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

define(['app/enforcer/enforcer.module'],function(enforcerApp) {


	enforcerApp.register.factory('NodeRestangular', function(Restangular, ENV) {
	    return Restangular.withConfig(function(RestangularConfig) {
	      RestangularConfig.setBaseUrl(ENV.getBaseURL("MD_SAL"));
	    });
	  });
	
	enforcerApp.register.factory('enforcerSvc', function(NodeRestangular, $http, ENV) {
		/*
		var svc = {
				base: ENV.getBaseURL("MD_SAL") + "/restconf/"
		};

		Result: {{serviceCall()}}
		 * You can define all of your REST API interactions here.
		 */
		var svc = {
				base: function() {
					//return NodeRestangular.one('restconf').one('operational').one('opendaylight-inventory:nodes');
					
					return NodeRestangular.one('restconf').one('operations').one('enforcer');
					
				},
				data : null
		};

		svc.getCurrentData = function() {
			return svc.data;
		};

		svc.getAllNodes = function() {
			svc.data = svc.base().get();
			return svc.data;
		};

		svc.getResult = function(input) {
			return svc.base().post("enforce", input);
		};

		return svc;
	});

});
