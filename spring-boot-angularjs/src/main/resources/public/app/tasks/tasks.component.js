(function(angular) {
  'use strict';

  angular.module('taskApp').component('taskApp', {
    templateUrl: 'app/tasks/tasks.html',
    controller: TasksController,
    controllerAs: 'tc'
  });

  function TasksController(Task, toastr) {
    var tc = this;
    tc.$onInit = onInit;
    tc.updateTask = updateTask;
    tc.deleteTask = deleteTask;
    tc.createTask = createTask;
  //
    ////////
  //
    function onInit() {
      Task.query().$promise.then(function(tasks) {
        tc.tasks = tasks;
      });
    }

    function updateTask(task) {
      task.$update();
    }

    function deleteTask(task) {
      task.$delete(function() {
        var index = tc.tasks.indexOf(task);
        if (index >= 0) {
          tc.tasks.splice(index, 1);
        }
      });
    }

    function createTask(task) {
      task.$save(function() {
        tc.tasks.push(task);
      }, function(err) {
        toastr.error(err.data.message);
      });
    }
  }

  TasksController.$inject = ['Task', 'toastr'];
}(angular));