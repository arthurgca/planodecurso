mainApp = angular.module "mainApp"

mainApp.factory "ArrayOf", () ->

  return (args) ->
    (x, field, ctr) ->
      _.map ((adt.only Array) x, field, ctr), (elem, i) ->
        (adt.only args) elem, "#{field}[#{i}]", ctr

mainApp.service "Alertas", ($sce) ->

  @mensagens = []

  @sucesso = (mensagem) =>
    @mensagens[0] = tipo: "success", mensagem: $sce.trustAsHtml mensagem

  @erro = (mensagem) =>
    @mensagens[0] = tipo: "danger", mensagem: $sce.trustAsHtml mensagem

  @fecharAlerta = (index) =>
    @mensagens.splice(index)

  return this
