mainApp = angular.module "mainApp"

mainApp.controller "PlanoCtrl", (
  $scope,
  $rootScope,
  Alertas,
  Planos,
  Curriculos,
  Grades,
  ModalProgramarDisciplina
  ModalCriarPlano) ->

  $scope.plano = undefined

  $scope.criarPlano = () ->
    modal = ModalCriarPlano.abrir
      curriculos: -> Curriculos.query()
    modal.result.then (response) ->
      criarPlanoDeCurso response.curriculo, response.grade

  $scope.programar = (periodo) ->
    modal = ModalProgramarDisciplina.abrir
      periodo: -> periodo
      disciplinas: -> periodo.ofertadas
    modal.result.then (disciplina) ->
      programarDisciplina disciplina, periodo

  $scope.desprogramar = (disciplina, periodo) ->
    if isRequisito disciplina
      mensagem = """
        Isso vai remover disciplinas que tem #{disciplina.nome} como requisito.
        Tem certeza?
      """
      if not confirm mensagem
        return
    desprogramarDisciplina disciplina, periodo

  $scope.dropped = (dragEl, dropEl) ->
    src = angular.element dragEl
    dest = angular.element dropEl
    dePeriodo = src.scope().periodo
    paraPeriodo = dest.scope().periodo
    disciplina = src.scope().disciplina
    if dePeriodo.semestre != paraPeriodo.semestre
      moverDisciplina disciplina, dePeriodo, paraPeriodo

  #

  criarPlanoDeCurso = (curriculo, grade) ->
    Planos.criar(
      curriculo: curriculo.id
      grade: grade.id).$promise
        .then((plano) -> $scope.plano = plano)

  programarDisciplina = (disciplina, periodo) ->
    Planos.programar(
      plano: $scope.plano.id,
      disciplina: disciplina.id
      periodo: periodo.semestre).$promise
        .then(emitir "disciplinaProgramada", disciplina, periodo)
        .then(alertarSucesso)
        .catch(alertarErro)

  desprogramarDisciplina = (disciplina, periodo) ->
    Planos.desprogramar(
      plano: $scope.plano.id,
      disciplina: disciplina.id,
      periodo: periodo.semestre).$promise
        .then(emitir "disciplinaDesprogramada", disciplina, periodo)
        .then(alertarSucesso)
        .catch(alertarErro)

  moverDisciplina = (disciplina, de, para) ->
    Planos.mover(
      plano: $scope.plano.id,
      disciplina: disciplina.id,
      de: de.semestre,
      para: para.semestre).$promise
        .then(emitir "disciplinaMovimentada", disciplina, de, para)
        .then(alertarSucesso)
        .catch(alertarErro)

  #

  isRequisito = (disciplina) ->
    _isRequisito = (disciplinaA, disciplinaB) ->
       _.some disciplinaB.requisitos, (requisito) ->
         requisito.id == disciplinaA.id

    _isRequisitoLista = (disciplinaA, disciplinas) ->
       _.some disciplinas, (disciplinaB) ->
         _isRequisito disciplinaA, disciplinaB

    _isRequisitoLista disciplina, disciplinasProgramadas()

  disciplinasProgramadas = () ->
    fun = (memo, periodo) ->
      memo.concat periodo.disciplinas
    _.reduce $scope.plano.periodos, fun, []

  #

  emitir = (nomeEvento, args...) ->
    (response) ->
      $rootScope.$broadcast nomeEvento, args...
      response

  alertarSucesso = (response) ->
    Alertas.sucesso response.message
    response

  alertarErro = (response) ->
    Alertas.erro response.data.message
    response

  #

  refresh = ->
    plano = Planos.get plano: $scope.plano.id, ->
      $scope.plano = plano

  $scope.$on "disciplinaProgramada", (event, args) ->
    refresh()

  $scope.$on "disciplinaDesprogramada", (event, args) ->
    refresh()

  $scope.$on "disciplinaMovimentada", (event, args) ->
    refresh()

  bootstrap = () ->
    if PLANO_CACHE is undefined
      $scope.criarPlano()
    else
      $scope.plano = PLANO_CACHE

  bootstrap()

mainApp.factory "Planos", ($resource) ->
  $resource "/planos/:plano", { plano: "@plano" },
    criar:
      url: "/planos"
      method: "POST"
      params:
        curriculo: "@curriculo"
        grade: "@grade"
    programar:
      url: "/planos/:plano/:periodo"
      method: "POST"
      params:
        plano: "@plano"
        disciplina: "@disciplina"
        periodo: "@periodo"
    mover:
      url: "/planos/:plano/:para/:disciplina"
      method: "PUT"
      params:
        plano: "@plano"
        disciplina: "@disciplina"
        de: "@de"
        para: "@para"
    desprogramar:
      url: "/planos/:plano/:periodo/:disciplina"
      method: "DELETE"
      params:
        plano: "@plano"
        disciplina: "@disciplina"
        periodo: "@periodo"
