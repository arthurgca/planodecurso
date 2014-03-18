mainApp = angular.module "mainApp"

mainApp.factory "PlanoDeCurso", (ArrayOf, Periodo) ->

  PlanoDeCurso = adt.data ->
    PlanoDeCurso:
      periodos: ArrayOf Periodo

  PlanoDeCurso.apply = (ctx, args) ->
    @fromResponse args[0]

  PlanoDeCurso.fromResponse = (response) ->
    PlanoDeCurso.PlanoDeCurso.create
      periodos: _.map response.periodos, Periodo.fromResponse

  PlanoDeCurso.prototype.disciplinas = () ->
    fun = (memo, periodo) ->
      memo.concat periodo.disciplinas
    _.reduce @periodos, fun, []

  PlanoDeCurso.prototype.requisitosSatisfeitos = (periodo) ->
    fun = (memo, each) =>
      if each.semestre < periodo.semestre
        memo.concat each.disciplinas
      else
        memo
    _.reduce @periodos, fun, []

  PlanoDeCurso.prototype.isRequisito = (disciplina) ->
    isRequisito = (disciplinaA, disciplinaB) ->
       _.some disciplinaB.requisitos, (requisito) ->
         requisito.id == disciplinaA.id

    isRequisitoLista = (disciplinaA, disciplinas) ->
       _.some disciplinas, (disciplinaB) ->
         isRequisito disciplinaA, disciplinaB

    isRequisitoLista disciplina, @disciplinas()

  PlanoDeCurso.prototype.isRequisitoSatisfeito = (requisito, periodo) ->
   _.some @requisitosSatisfeitos(periodo), (disciplinaPaga) ->
    disciplinaPaga.id == requisito.id

  return PlanoDeCurso

mainApp.service "PlanoDeCursoService", ($http, PlanoDeCurso) ->

  @planoDeCurso = undefined

  @get = () =>
    $http(method: "GET", url: "/curso")
      .then (response) =>
        @planoDeCurso = PlanoDeCurso response.data

  @alocarDisciplina = (periodo, disciplina) ->
    $http(
      method: "POST",
      url: "/curso/#{periodo.semestre}",
      params:
        disciplina: disciplina.id)

  @moverDisciplina = (dePeriodo, paraPeriodo, disciplina) ->
    $http(
      method: "PUT",
      url: "/curso/#{paraPeriodo.semestre}/#{disciplina.id}"
      params:
        dePeriodo: dePeriodo.semestre)

  @desalocarDisciplina = (periodo, disciplina) ->
    $http(method: "DELETE", url: "/curso/#{periodo.semestre}/#{disciplina.id}")

  return this

mainApp.controller "PlanoDeCursoCtrl", (
  $scope,
  PlanoDeCursoService,
  DisciplinaService) ->

  $scope.isSatisfeito = (requisito, periodo) ->
    $scope.planoDeCurso.isRequisitoSatisfeito requisito, periodo

  refresh = ->
    PlanoDeCursoService.get().then (planoDeCurso) ->
      $scope.planoDeCurso = planoDeCurso

  $scope.$on "disciplinaAlocada", (event, args) ->
    refresh()

  $scope.$on "disciplinaMovimentada", (event, args) ->
    refresh()

  $scope.$on "disciplinaDesalocada", (event, args) ->
    refresh()

  bootstrap = () ->
    DisciplinaService.query()
    refresh()

  bootstrap()
