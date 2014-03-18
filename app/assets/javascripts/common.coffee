mainApp = angular.module "mainApp"

mainApp.factory "ArrayOf", () ->

  return (args) ->
    (x, field, ctr) ->
      _.map ((adt.only Array) x, field, ctr), (elem, i) ->
        (adt.only args) elem, "#{field}[#{i}]", ctr

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
