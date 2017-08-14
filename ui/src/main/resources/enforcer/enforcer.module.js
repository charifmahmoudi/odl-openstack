/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

define(['angularAMD', 'app/routingConfig', 'app/core/core.services', 'common/config/env.module'], function(ng) {
  var enforcerApp = angular.module('app.enforcer', ['app.core', 'ui.router.state','config']);

  enforcerApp.config(function($stateProvider, $compileProvider, $controllerProvider, $provide, NavHelperProvider, $translateProvider) {
    enforcerApp.register = {
      controller : $controllerProvider.register,
      directive : $compileProvider.directive,
      factory : $provide.factory,
      service : $provide.service

    };


    NavHelperProvider.addControllerUrl('app/enforcer/enforcer.controller');
    NavHelperProvider.addToMenu('enforcer', {
     "link" : "#/enforcer",
     "active" : "main.enforcer",
     "title" : "enforcer",
     "icon" : "",  // Add navigation icon css class here
     "page" : {
        "title" : "enforcer",
        "description" : "enforcer"
     }
    });

    var access = routingConfig.accessLevels;

    $stateProvider.state('main.enforcer', {
        url: 'enforcer',
        access: access.admin,
        views : {
            'content' : {
                templateUrl: 'src/app/enforcer/enforcer.tpl.html',
                controller: 'enforcerCtrl'
            }
        }
    });

  });

  return enforcerApp;
});
