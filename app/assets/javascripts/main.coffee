mainApp = angular.module "mainApp", [
  "ngResource",
  "ui.bootstrap",
  "ui.growl",
  "lvl.directives.dragdrop"]


mainApp.controller "MainCtrl", ($scope) ->
