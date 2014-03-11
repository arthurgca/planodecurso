mainApp = angular.module "mainApp"

mainApp.factory "Disciplina", (ArrayOf) ->

  Disciplina = adt.data ->
    Disciplina:
      id: adt.only Number
      nome: adt.only String
      creditos: adt.only Number
      requisitos: ArrayOf this

  Disciplina.apply = (ctx, args) ->
    @fromResponse args[0]

  Disciplina.fromResponse = (response) ->
    Disciplina.Disciplina.create
      id: response.id
      nome: response.nome
      creditos: response.creditos
      requisitos: _.map response.requisitos, Disciplina.fromResponse

  return Disciplina

mainApp.service "DisciplinaService", ($http, Disciplina) ->

  @disciplinas = undefined

  @query = () =>
    $http(method: "GET", url: "/disciplinas")
      .then (response) =>
        @disciplinas = _.map response.data, Disciplina

  return this
