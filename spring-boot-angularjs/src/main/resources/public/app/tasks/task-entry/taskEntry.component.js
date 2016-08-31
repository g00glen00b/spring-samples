(function(angular) {
  'use strict';

  angular.module('taskApp').component('taskEntry', {
    templateUrl: 'app/tasks/task-entry/taskEntry.html',
    bindings: {
      'task': '<',
      'onUpdate': '&',
      'onDelete': '&'
    },
    controller: TaskEntryController,
    controllerAs: 'te'
  });

  function TaskEntryController() {

  }
}(angular));