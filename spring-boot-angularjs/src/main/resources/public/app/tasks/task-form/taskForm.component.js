(function(angular) {
  'use strict';

  angular.module('taskApp').component('taskForm', {
    templateUrl: 'app/tasks/task-form/taskForm.html',
    bindings: {
      'onCreate': '&'
    },
    controller: TaskFormController,
    controllerAs: 'tf'
  });

  function TaskFormController(Task) {
    var tf = this;
    tf.task = new Task();
    tf.create = create;

    ////////

    function create(task, form) {
      tf.onCreate({ task: task });
      tf.task = new Task();
      form.$setPristine();
    }
  }

  TaskFormController.$inject = ['Task'];
}(angular));