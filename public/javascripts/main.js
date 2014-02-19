var mainApp = angular.module("mainApp", ["ui.bootstrap"]);

mainApp.controller("PlanoDeCursoCtrl", function($scope, $http) {
  $scope.ui = {
    planoDeCurso: [],

    disciplinasNaoAlocadas: [],

    btnSelecionado: undefined,

    mensagem: undefined,
  };

  $scope.fecharMensagem = function() {
    $scope.ui.mensagem = undefined;
  };

  var criarPayload = function() {
    var payload = [];

    _.each($scope.ui.planoDeCurso, function(periodo) {
      payload.push({
        semestre: periodo.semestre,
        disciplinas: _.map(periodo.disciplinas, function(disciplina) {
          return disciplina.id;
        }),
      });
    });

    return payload;
  };

  var playTransformRequest = function(data) {
    var result = [];
    var i = 0;
    var j = 0;
    _.each(data, function(periodo) {
      result.push("periodos[" + i + "].semestre=" + periodo.semestre);
      _.each(periodo.disciplinas, function(id) {
        result.push("periodos[" + i + "].disciplinas[" + j + "]=" + id);
        j = j + 1;
      });
      i = i + 1;
      j = 0;
    });
    return result.join("&");
  }

  $scope.alocar = function(semestre, e) {
    e.preventDefault();
    e.stopPropagation();

    var postURL = alocarURLTemplate({
      "semestre": semestre,
      "disciplina": $scope.ui.btnSelecionado.id,
    });

    var config = {
      "method": "POST",
      "url": postURL,
      "data": criarPayload(),
      "headers": {'Content-Type': 'application/x-www-form-urlencoded'},
      "transformRequest": playTransformRequest,
    };

    $http(config).
      success(function(data, status, headers, config) {
        popularDisciplinasNaoAlocadas(data.disciplinasNaoAlocadas);
        popularPlanoDeCurso(data.periodos);
        exibirMensagem("success", "Disciplina alocada com sucesso!")
      }).
      error(function(data, status, headers) {
        exibirMensagem("danger", "Houve um problema ao alocar a disciplina: " + data.message);
      });
  };

  $scope.desalocar = function(disciplina) {
    if (!confirm("Tem certeza que deseja desalocar \"" + disciplina.nome + "\", e todas que dependem desta?"))
      return;

    var postURL = desalocarURLTemplate({
      "disciplina": disciplina.id,
    });

    var config = {
      "method": "POST",
      "url": postURL,
      "data": criarPayload(),
      "headers": {'Content-Type': 'application/x-www-form-urlencoded'},
      "transformRequest": playTransformRequest,
    };

    $http(config).
      success(function(data, status, headers, config) {
        popularDisciplinasNaoAlocadas(data.disciplinasNaoAlocadas);
        popularPlanoDeCurso(data.periodos);
        exibirMensagem("success", "Disciplina desalocada com sucesso!")
      }).
      error(function(data, status, headers) {
        exibirMensagem("danger", "Houve um problema ao desalocar. Bug?");
      });
  };

  var alocarURLTemplate = _.template(
    "/alocarDisciplina/<%= semestre %>/<%= disciplina %>"
  );

  var desalocarURLTemplate = _.template(
    "/desalocarDisciplina/<%= disciplina %>"
  );

  var tipTemplate = _.template(
    ["<b>Créditos</b>: <%= creditos %>",
     "<b>Dificuldade</b>: <%= dificuldade %>",
     "<b>Requisitos</b>: <%= requisitos %>"].join("<br/>"));

  var criarTipDisciplina = function(disciplina) {
    return tipTemplate({
      "creditos": disciplina.creditos,
      "dificuldade": disciplina.dificuldade,
      "requisitos": _.map(disciplina.dependencias, function(dep) {
        return dep.nome
      }).join() || "Nenhum",
    });
  };

  var criarBtnDisciplina = function(disciplina){
    return _.extend({
        "tooltip": criarTipDisciplina(disciplina),
      }, disciplina);
  };

  var popularDisciplinasNaoAlocadas = function(disciplinasNaoAlocadas) {
    var disciplinas = _.sortBy(disciplinasNaoAlocadas, function(disciplina) {
      return disciplina.periodo;
    });

    var periodos = _.groupBy(_.map(disciplinas, function(disciplina) {
      return criarBtnDisciplina(disciplina);
    }), "periodo");

    $scope.ui.disciplinasNaoAlocadas = [];

    _.each(periodos, function(disciplinas, semestre){
      $scope.ui.disciplinasNaoAlocadas.push({
        "semestre": semestre,
        "disciplinas": disciplinas,
      });
    });
  };

  var popularPlanoDeCurso = function(planoDeCurso) {
    var periodos = _.sortBy(planoDeCurso, function(periodo) {
      return periodo.semestre;
    });

    $scope.ui.planoDeCurso = [];

    _.each(periodos, function(periodo) {
      periodo.disciplinas = _.map(periodo.disciplinas, function(disciplina) {
        return criarBtnDisciplina(disciplina);
      });

      $scope.ui.planoDeCurso.push(periodo);
    });
  };

  var exibirMensagem = function(type, text) {
    $scope.ui.mensagem = {
      "type": type,
      "text": text,
    };
  };

  var bootstrap = function() {
    $http({method: "GET", url: "/planoInicial"})
      .success(function(data, status, headers, config) {
        popularDisciplinasNaoAlocadas(data.disciplinasNaoAlocadas);
        popularPlanoDeCurso(data.periodos);
      })
      .error(function(data, status, headers) {
        console.error("Não foi possível acessar o Plano de Curso inicial.");
      })
      .then(function (data) {
        exibirMensagem(
          "info",
          "Dica: Escolha e clique em um botão da coluna \"Disciplinas Ofertadas\"."
        );
      });
  };

  bootstrap();
});
