mainApp = angular.module("mainApp", ["ui.bootstrap", "ui.sortable"])

mainApp.controller "PlanoDeCursoCtrl", (
  $scope,
  AlertasService,
  ModalAlocarDisciplinaService,
  PlanoDeCursoService) ->

  $scope.alertas = AlertasService

  $scope.planoDeCurso = PlanoDeCursoService

  $scope.alocar = (periodo) ->
    modal = ModalAlocarDisciplinaService.abrir
      periodo: -> periodo
      disciplinasDisponiveis: PlanoDeCursoService.disciplinasDisponiveis

    modal.result.then (disciplina) ->
      alocarDisciplina periodo, disciplina

  $scope.desalocar = (periodo, disciplina) ->
    if isRequisitoLista disciplina, PlanoDeCursoService.disciplinas
      mensagem = """
        Isso vai remover disciplinas que tem #{disciplina.nome} como requisito.
        Tem certeza?
      """
      if not confirm mensagem
        return
    desalocarDisciplina periodo, disciplina

  $scope.sortableOptions =
    connectWith: ".periodo .list-group"

  alocarDisciplina = (periodo, disciplina) ->
    processaRequisicao (
      PlanoDeCursoService.alocarDisciplina periodo, disciplina)

  desalocarDisciplina = (periodo, disciplina) ->
    processaRequisicao (
      PlanoDeCursoService.desalocarDisciplina periodo, disciplina)

  moverDisciplina = (periodo, disciplina) ->
    processaRequisicao (
      PlanoDeCursoService.moverDisciplina periodo, disciplina)

  processaRequisicao = (promise) ->
    promise.success (data) ->
      PlanoDeCursoService.query()
      $scope.alertas.sucesso data.message
    promise.error (data) ->
      PlanoDeCursoService.query()
      $scope.alertas.erro data.message
    promise

  isRequisito = (disciplinaA, disciplinaB) ->
     _.some disciplinaB.requisitos, (requisito) ->
       requisito.id == disciplinaA.id

  isRequisitoLista = (disciplinaA, disciplinas) ->
     _.some disciplinas, (disciplinaB) ->
       isRequisito(disciplinaA, disciplinaB)

  instalaObservadorMovimentos = ->
    coletaMovimentos = ->
      movimentos = []
      _.each PlanoDeCursoService.periodos, (periodo) ->
        _.each periodo.alocacoes, (alocacao) ->
          if (parseInt periodo.semestre) != (parseInt alocacao.semestre)
            movimentos.push
              periodo: periodo
              disciplina: alocacao.disciplina
      movimentos

    observaMovimentos = ->
      coletaMovimentos().length

    processaMovimentos = ->
      _.map coletaMovimentos(), (movimento) ->
        moverDisciplina(movimento.periodo, movimento.disciplina)

    $scope.$watch observaMovimentos, processaMovimentos

  bootstrap = ->
    PlanoDeCursoService.query()
      .success ->
        instalaObservadorMovimentos()

  bootstrap()

mainApp.service "PlanoDeCursoService", ($http, DisciplinaService) ->

  @periodos = []

  @disciplinas =  []

  @query = ->
    $http(method: "GET", url: "/curso")
      .success (data, status, headers, config) =>
        popularDisciplinas(data.disciplinas)
        popularPeriodos(data.alocacoes)

  @disciplinasDisponiveis = ->
    alocadaIds = _.pluck @disciplinas, "id"
    disponiveis = _.filter DisciplinaService.disciplinas, (disciplina) ->
      not _.contains alocadaIds, disciplina.id
    _.sortBy disponiveis, "nome"

  @alocarDisciplina = (periodo, disciplina) ->
    $http method: "POST", url: "/curso/#{periodo.semestre}/#{disciplina.id}"

  @moverDisciplina = (periodo, disciplina) ->
    $http method: "PUT", url: "/curso/#{periodo.semestre}/#{disciplina.id}"

  @desalocarDisciplina = (periodo, disciplina) ->
    $http(method: "DELETE", url: "/curso/#{periodo.semestre}/#{disciplina.id}")

  popularPeriodos = (alocacoes) =>
    coletarRequisitos = (alocacao) ->
      _.map alocacao.disciplina.requisitos, (requisito) ->
        requisito.nome

    criarAlocacao = (alocacao) ->
      _.extend (requisitos: coletarRequisitos alocacao), alocacao

    totalCreditos = (alocacoes) ->
      _.reduce alocacoes, ((val, aloc) -> val + aloc.disciplina.creditos), 0

    criarPeriodo = (semestre, alocacoes) ->
      semestre: semestre
      creditos: totalCreditos(alocacoes)
      alocacoes: alocacoes

    alocacoes = _.sortBy alocacoes, "semestre"

    semestreAlocacoes = _.groupBy(alocacoes, "semestre")

    _.each [1..14], (semestre) ->
      semestreAlocacoes[semestre] ||= []

    @periodos = []

    _.each semestreAlocacoes, (alocacoes, semestre) =>
      @periodos.push (criarPeriodo semestre, alocacoes)

  popularDisciplinas = (disciplinas) =>
    @disciplinas = disciplinas

  bootstrap = ->
    DisciplinaService.query()

  bootstrap()

  this

mainApp.service "DisciplinaService", ($http) ->

  @disciplinas = []

  @query = ->
    $http(method: "GET", url: "/disciplinas")
      .success (data, status, headers, config) =>
        popularDisciplinas(data)

  popularDisciplinas = (disciplinas) =>
    @disciplinas = disciplinas

  this

mainApp.service "AlertasService", ($sce) ->

  @mensagens = []

  @sucesso = (mensagem) =>
    @mensagens[0] = tipo: "success", mensagem: $sce.trustAsHtml mensagem

  @erro = (mensagem) =>
    @mensagens[0] = tipo: "danger", mensagem: $sce.trustAsHtml mensagem

  @fecharAlerta = (index) =>
    @mensagens.splice(index)

  this

mainApp.service "ModalAlocarDisciplinaService", ($modal) ->

  @abrir = (resolve) ->
    $modal.open
      templateUrl: "ModalAlocarDisciplina.html"
      controller: "ModalAlocarDisciplinaCtrl"
      resolve: resolve

  this

mainApp.controller "ModalAlocarDisciplinaCtrl", (
  $scope,
  $modalInstance,
  periodo,
  disciplinasDisponiveis) ->

  $scope.periodo = periodo

  $scope.disciplinas = disciplinasDisponiveis

  $scope.disciplinaSelecionada = $scope.disciplinas[0]

  $scope.ok = (disciplina) ->
    $modalInstance.close disciplina

  $scope.cancelar = ->
    $modalInstance.dismiss 'cancelar'
