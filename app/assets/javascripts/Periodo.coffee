mainApp = angular.module "mainApp"

mainApp.factory "Periodo", (ArrayOf, Disciplina) ->

  Periodo = adt.data ->
    Periodo:
      semestre: adt.only Number
      totalCreditos: adt.only Number
      disciplinas: ArrayOf Disciplina

  Periodo.apply = (ctx, args) ->
    @fromResponse args[0]

  Periodo.fromResponse = (response) ->
    Periodo.Periodo.create
      semestre: response.semestre
      totalCreditos: response.totalCreditos
      disciplinas: _.map response.disciplinas, Disciplina.fromResponse

  return Periodo

mainApp.controller "PeriodoCtrl", (
  $scope,
  $rootScope,
  Alertas,
  DisciplinaService,
  PlanoDeCursoService,
  ModalAlocarDisciplina) ->

  $scope.alocar = () ->
    modal = ModalAlocarDisciplina.abrir
      periodo: -> $scope.periodo
      disciplinas: -> disciplinasDisponiveis()
    modal.result.then (disciplina) ->
      alocarDisciplina $scope.periodo, disciplina

  $scope.desalocar = (disciplina) ->
    if $scope.planoDeCurso.isRequisito(disciplina)
      mensagem = """
        Isso vai remover disciplinas que tem #{disciplina.nome} como requisito.
        Tem certeza?
      """
      if not confirm mensagem
        return
    desalocarDisciplina $scope.periodo, disciplina

  alocarDisciplina = (periodo, disciplina) ->
    onSuccess = (response) ->
      emitirEvento "disciplinaAlocada",
        disciplina: disciplina
      alertarSucesso response.data.message
    onError = (response) ->
      alertarErro response.data.message
    PlanoDeCursoService.alocarDisciplina(periodo, disciplina)
      .then onSuccess, onError

  moverDisciplina = (dePeriodo, paraPeriodo, disciplina) ->
    onSuccess = (response) ->
      emitirEvento "disciplinaMovimentada",
        dePeriodo: dePeriodo
        paraPeriodo: paraPeriodo
        disciplina: disciplina
      alertarSucesso response.data.message
    onError = (response) ->
      alertarErro response.data.message
    PlanoDeCursoService.moverDisciplina(dePeriodo, paraPeriodo, disciplina)
      .then onSuccess, onError

  desalocarDisciplina = (periodo, disciplina) ->
    onSuccess = (response) ->
      emitirEvento "disciplinaDesalocada",
        periodo: periodo
        disciplina: disciplina
      alertarSucesso response.data.message
    onError = (response) ->
      alertarErro response.data.message
    PlanoDeCursoService.desalocarDisciplina(periodo, disciplina)
      .then onSuccess, onError

  emitirEvento = (nome, data) ->
    $rootScope.$broadcast nome, data

  alertarSucesso = (message) ->
    Alertas.sucesso message

  alertarErro = (message) ->
    Alertas.erro message

  disciplinasDisponiveis = ->
    alocadas = PlanoDeCursoService.planoDeCurso.disciplinas()
    _.filter DisciplinaService.disciplinas, (disciplina) ->
      not _.some alocadas, (alocada) ->
        disciplina.id == alocada.id

mainApp.service "ModalAlocarDisciplina", ($modal) ->

  @abrir = (resolve) ->
    $modal.open
      resolve: resolve
      controller: "ModalAlocarDisciplinaCtrl"
      templateUrl: "periodo/modal-alocar-disciplina.html"

  this

mainApp.controller "ModalAlocarDisciplinaCtrl", (
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
