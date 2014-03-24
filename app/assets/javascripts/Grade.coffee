mainApp = angular.module "mainApp"

mainApp.factory "Grades", ($resource) ->
  $resource "/grades/:gradeId", { gradeId: "@id" },
