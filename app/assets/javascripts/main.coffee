mainApp = angular.module "mainApp", [
  "ui.bootstrap",
  "ui.sortable"]

mainApp.controller "MainCtrl", ($scope, DisciplinaService) ->

  DisciplinaService.query()
