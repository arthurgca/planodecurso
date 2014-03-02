mainApp = angular.module("mainApp", ["ui.bootstrap", "ui.sortable"])

mainApp.service "Disciplina", ($http) ->

  @disciplinas = []

  @query = ->
    $http(method: "GET", url: "/disciplinas")
      .success (data, status, headers, config) =>
        popularDisciplinas(data)

  popularDisciplinas = (disciplinas) =>
    @disciplinas = disciplinas

  this

mainApp.service "PlanoDeCurso", ($http, Disciplina) ->

  @periodos = []

  @disciplinas =  []

  @query = ->
    $http(method: "GET", url: "/curso")
      .success (data, status, headers, config) =>
        popularDisciplinas(data.disciplinas)
        popularPeriodos(data.alocacoes)

  @disciplinasDisponiveis = ->
    disciplinasAlocadasIds = _.map @disciplinas, (disciplina) ->
      disciplina.id
    disciplinasDisponiveis = _.filter Disciplina.disciplinas, (disciplina) ->
      not _.contains disciplinasAlocadasIds, disciplina.id
    _.sortBy disciplinasDisponiveis, (disciplina) ->
      disciplina.nome

  @alocarDisciplina = (semestre, disciplina) ->
    $http method: "POST", url: "/curso/#{semestre}/#{disciplina.id}"

  @moverDisciplina = (semestre, disciplina) ->
    $http method: "PUT", url: "/curso/#{semestre}/#{disciplina.id}"

  @desalocarDisciplina = (semestre, disciplina) ->
    $http(method: "DELETE", url: "/curso/#{semestre}/#{disciplina.id}")

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

    alocacoes = _.sortBy alocacoes, (alocacao) ->
      alocacao.semestre

    semestreAlocacoes = _.groupBy(alocacoes, "semestre")

    _.each [1..14], (semestre) ->
      semestreAlocacoes[semestre] ||= []

    @periodos = []

    _.each semestreAlocacoes, (alocacoes, semestre) =>
      @periodos.push (criarPeriodo semestre, alocacoes)

  popularDisciplinas = (disciplinas) =>
    @disciplinas = disciplinas

  this

mainApp.controller "ModalAlocarDisciplinaCtrl", ($scope, $modalInstance, semestre, disciplinas) ->

  $scope.semestre = semestre

  $scope.disciplinas = disciplinas

  $scope.disciplinaSelecionada = disciplinas[0]

  $scope.ok = (disciplina) ->
    $modalInstance.close disciplina

  $scope.cancelar = ->
    $modalInstance.dismiss 'cancelar'

mainApp.controller "PlanoDeCursoCtrl", ($scope, $modal, $sce, PlanoDeCurso, Disciplina) ->

  $scope.alertas = []

  criarAlerta = (type, msg) ->
    $scope.alertas[0] = type: type, msg: $sce.trustAsHtml msg

  $scope.fecharAlerta = (index) ->
    $scope.alertas.splice(index)

  $scope.planoDeCurso = PlanoDeCurso

  $scope.modalAlocarDisciplina = (semestre) ->
    modalInstance = $modal.open
      templateUrl: "ModalAlocarDisciplina.html"
      controller: "ModalAlocarDisciplinaCtrl"
      resolve:
        semestre: -> semestre
        disciplinas: -> PlanoDeCurso.disciplinasDisponiveis()
    modalInstance.result
      .then (disciplina) ->
        processaRequisicao (PlanoDeCurso.alocarDisciplina semestre, disciplina)

  $scope.desalocarDisciplina = (semestre, disciplina) ->
    requisitoIds = []
    _.each PlanoDeCurso.disciplinas, (disciplinaAlocada) ->
      requisitoIds = requisitoIds.concat _.map(disciplinaAlocada.requisitos, (r) -> r.id)
    if _.contains requisitoIds, disciplina.id
      if !confirm "Isso vai desalocar disciplinas que tem #{disciplina.nome} como pré-requisito. Tem certeza?"
        return
    processaRequisicao (PlanoDeCurso.desalocarDisciplina semestre, disciplina)

  $scope.sortableOptions =
    connectWith: ".periodo .list-group"

  processaRequisicao = (promise) ->
    promise.success (data) ->
      PlanoDeCurso.query()
      criarAlerta "success", data.message
    promise.error (data) ->
      PlanoDeCurso.query()
      criarAlerta "danger", data.message
    promise

  criaMovimento = (origem, disciplina, destino) ->
    origem: origem
    disciplina: disciplina
    destino: destino

  coletaMovimentos = ->
    iterator = (memo, periodo) ->
      alocacoes = _.filter periodo.alocacoes, (alocacao) ->
        (parseInt periodo.semestre) != (parseInt alocacao.semestre)
      movimentos = _.map alocacoes, (alocacao) ->
        criaMovimento alocacao.semestre, alocacao.disciplina, periodo.semestre
      memo.concat movimentos

    _.reduce PlanoDeCurso.periodos, iterator, []

  observaMovimentos = ->
    coletaMovimentos().length

  processaMovimentos = ->
    _.map coletaMovimentos(), (movimento) ->
      console.log "movendo", movimento
      processaRequisicao (
        PlanoDeCurso.moverDisciplina movimento.destino, movimento.disciplina)

  $scope.$watch observaMovimentos, processaMovimentos

  bootstrap = ->
    Disciplina.query()
      .success ->
        PlanoDeCurso.query()

  bootstrap()
