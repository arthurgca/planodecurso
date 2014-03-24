mainApp = angular.module "mainApp"

mainApp.factory "Curriculos", ($resource) ->
  $resource "/curriculos/:curriculoId", { curriculoId: "@id" },
