(function(angular) {
  'use strict';

  angular.module('taskApp').factory('Task', Task);

  function Task($resource) {
    return $resource('api/tasks/:id', { id: '@id' }, {
      update: { method: 'PUT', isArray: false }
    });
  }

  Task.$inject = ['$resource'];
}(angular));