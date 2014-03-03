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
    if isRequisitoLista disciplina, PlanoDeCursoService.disciplinasAlocadas
      mensagem = """
        Isso vai remover disciplinas que tem #{disciplina.nome} como requisito.
        Tem certeza?
      """
      if not confirm mensagem
        return
    desalocarDisciplina periodo, disciplina

  $scope.sortableOptions =
    connectWith: ".periodo .list-group"

  atualizar = ->
    PlanoDeCursoService.atualizar()

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
    onSuccess = (response) ->
      atualizar().then ->
        $scope.alertas.sucesso response.data.message
    onError = (response) ->
      atualizar().then ->
        $scope.alertas.erro response.data.message
    promise.then(onSuccess, onError)

  isRequisito = (disciplinaA, disciplinaB) ->
     _.some disciplinaB.requisitos, (requisito) ->
       requisito.id == disciplinaA.id

  isRequisitoLista = (disciplinaA, disciplinas) ->
     _.some disciplinas, (disciplinaB) ->
       isRequisito(disciplinaA, disciplinaB)

  observarMovimentos = ->
    coletaMovimentos = ->
      movimentos = []
      _.each PlanoDeCursoService.periodos, (periodo) ->
        _.each periodo.alocacoes, (alocacao) ->
          if (parseInt periodo.semestre) != (parseInt alocacao.semestre)
            movimentos.push
              periodo: periodo
              alocacao: alocacao
      movimentos

    observadorMovimentos = ->
      coletaMovimentos().length

    processaMovimentos = ->
      _.map coletaMovimentos(), (movimento) ->
        movimento.alocacao.semestre = movimento.periodo.semestre
        moverDisciplina(movimento.periodo, movimento.alocacao.disciplina)

    $scope.$watch observadorMovimentos, processaMovimentos

  bootstrap = ->
    atualizar()
      .then observarMovimentos

  bootstrap()

mainApp.service "PlanoDeCursoService", ($http) ->

  @periodos = []

  @disciplinasOfertadas = []

  @disciplinasAlocadas = []

  @atualizar = ->
    $http(method: "GET", url: "/curso")
      .then atualizarTudo

  @disciplinasDisponiveis = () =>
    _.filter @disciplinasOfertadas, (ofertada) =>
      not _.some @disciplinasAlocadas, (alocada) =>
        alocada.id == ofertada.id

  @alocarDisciplina = (periodo, disciplina) ->
    $http(method: "POST", url: "/curso/#{periodo.semestre}/#{disciplina.id}")

  @moverDisciplina = (periodo, disciplina) ->
    $http(method: "PUT", url: "/curso/#{periodo.semestre}/#{disciplina.id}")

  @desalocarDisciplina = (periodo, disciplina) ->
    $http(method: "DELETE", url: "/curso/#{periodo.semestre}/#{disciplina.id}")

  atualizarTudo = (res) =>

    @disciplinasAlocadas = res.data.disciplinas

    atualizarPeriodo = (periodo, alocacoesServidor) =>
      marcadoRemover = []

      _.each periodo.alocacoes, (alocacaoCliente) =>
        marcarRemover = not _.some alocacoesServidor, (alocacaoServidor) ->
          _.isEqual alocacaoCliente.disciplina.id, alocacaoServidor.disciplina.id
        marcadoRemover.push alocacaoCliente if marcarRemover

      _.each marcadoRemover, (alocacao) =>
        periodo.alocacoes.splice (_.indexOf periodo.alocacoes, alocacao), 1

      marcadoAdicionar = []

      _.each alocacoesServidor, (alocacaoServidor) =>
        marcarAdicionar = not _.some periodo.alocacoes, (alocacaoCliente) ->
          _.isEqual alocacaoCliente.disciplina.id, alocacaoServidor.disciplina.id
        marcadoAdicionar.push alocacaoServidor if marcarAdicionar

      _.each marcadoAdicionar, (alocacao) =>
        periodo.alocacoes.push alocacao

      iterador = (memo, alocacao) =>
        memo + alocacao.disciplina.creditos
      periodo.creditos = _.reduce periodo.alocacoes, iterador, 0

    alocacoes = _.groupBy res.data.alocacoes, "semestre"
    _.map @periodos, (periodo) ->
      atualizarPeriodo periodo, alocacoes[periodo.semestre]

  bootstrap = =>
    criarPeriodos = =>
      @periodos = _.map [1..14], (semestre) =>
        semestre: semestre
        creditos: 0
        alocacoes: []

    getDisciplinasOfertadas = =>
      $http(method: "GET", url: "/disciplinas")
        .then (res) =>
          @disciplinasOfertadas = res.data

    criarPeriodos()
    getDisciplinasOfertadas()

  bootstrap()

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
