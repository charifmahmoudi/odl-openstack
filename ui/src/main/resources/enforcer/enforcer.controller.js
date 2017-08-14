/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

define(['app/enforcer/enforcer.module','app/enforcer/enforcer.services'], function(enforcerApp) {

  enforcerApp.register.controller('enforcerCtrl', ['$scope', '$rootScope', 'enforcerSvc', function($scope, $rootScope, enforcerSvc) {

    $rootScope['section_logo'] = 'assets/images/logo_node.gif'; // Add your topbar logo location here such as 'assets/images/logo_topology.gif'

    $scope.enforcerInfo = {};
    
    $scope.inputlabel = "Test";

    	var inparam = {
			    enforce: {
			        input: {
			            source: $scope.inputlabel
			        }
			    }
			};
    	
    	$scope.outputs = enforcerSvc.getResult(inparam);

    $scope.data = "enforcer";
    enforcerSvc.getAllNodes().then(function(data) {
        $scope.data = data.nodes.node;
      });

  }]);


});
