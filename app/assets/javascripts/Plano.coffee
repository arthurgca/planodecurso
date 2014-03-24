mainApp = angular.module "mainApp"

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
