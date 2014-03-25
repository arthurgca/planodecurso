mainApp = angular.module "mainApp"

mainApp.service "Alertas", ($growl) ->

  @sucesso = (mensagem) =>
    ($growl.box "Sucesso",  mensagem,
      class: 'success'
      sticky: false
      timeout: 2500).open()

  @erro = (mensagem) =>
    ($growl.box "Erro", mensagem,
      class: 'danger'
      sticky: false
      timeout: 2500).open()

  return this

mainApp.service "ModalProgramarDisciplina", ($modal) ->

  @abrir = (resolve) ->
    $modal.open
      resolve: resolve
      controller: "ModalProgramarDisciplinaCtrl"
      templateUrl: "periodo/modal-programar-disciplina.html"

  this

mainApp.controller "ModalProgramarDisciplinaCtrl", (
  $scope,
  $modalInstance,
  periodo,
  disciplinas) ->

  $scope.periodo = periodo

  $scope.disciplinas = disciplinas

  $scope.disciplina = $scope.disciplinas[0]

  $scope.ok = (disciplina) ->
    $modalInstance.close disciplina

  $scope.cancelar = ->
    $modalInstance.dismiss "cancelar"
