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
      templateUrl: "modal-programar-disciplina.html"

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

mainApp.service "ModalConfigurarPlano", ($modal) ->

  @abrir = (resolve) ->
    $modal.open
      resolve: resolve
      controller: "ModalConfigurarPlanoCtrl"
      templateUrl: "modal-configurar-plano.html"

  this

mainApp.controller "ModalConfigurarPlanoCtrl", (
  $scope,
  $modalInstance,
  plano,
  curriculos,
  criarNovo) ->

  curriculos.$promise.then (response) ->
    $scope.curriculos = response
    $scope.curriculo = $scope.curriculos[0]
    $scope.grade = $scope.curriculos[0].grades[0]

    if (plano isnt undefined)
      $scope.periodo = $scope.grade.periodos[plano.periodoAtual.semestre - 1]
    else
      $scope.periodo = $scope.grade.periodos[0]

  $scope.criarNovo = criarNovo

  $scope.ok = (curriculo, grade, periodo) ->
    $modalInstance.close curriculo: curriculo, grade: grade, periodo: periodo

mainApp.service "ModalEstatisticas", ($modal) ->

  @abrir = (resolve) ->
    $modal.open
      resolve: resolve
      controller: "ModalEstatisticasCtrl"
      templateUrl: "modal-estatisticas.html"

  this

mainApp.controller "ModalEstatisticasCtrl", (
  $scope,
  $modalInstance,
  estatisticasPagas,
  estatisticasPlanejadas) ->

  $scope.estatisticasPagas =
    "Disciplinas Obrigatórias": estatisticasPagas.estatisticasObrigatorias
    "Disciplinas Optativas": estatisticasPagas.estatisticasOptativas
    "Disciplinas Complementares": estatisticasPagas.estatisticasComplementares

  $scope.estatisticasPlanejadas =
    "Disciplinas Obrigatórias": estatisticasPlanejadas.estatisticasObrigatorias
    "Disciplinas Optativas": estatisticasPlanejadas.estatisticasOptativas
    "Disciplinas Complementares": estatisticasPlanejadas.estatisticasComplementares

  $scope.estatisticas = {
    'Pagas': $scope.estatisticasPagas,
    'Planejadas': $scope.estatisticasPlanejadas }

  $scope.ok = () ->
    $modalInstance.close()
