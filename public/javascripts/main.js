var mainApp = angular.module("mainApp");

mainApp.controller("PlanoDeCursoCtrl", function($scope, $http) {
  var MINIMO_CREDITOS = 14;

  var MAXIMO_CREDITOS = 28;

  $scope.disciplinasOfertadas = [];

  $scope.periodos = [];

  $scope.errors = [];

  $scope.sortableOptions = {
    connectWith: ".sortable-list"
  };

  $scope.addPeriodo = function(semestre) {
    $scope.periodos.push({
      "semestre": semestre,
      "disciplinas": [],
    });
  };

  $scope.getTotalCreditos = function(semestre) {
    return _.reduce($scope.getPeriodo(semestre).disciplinas, function(memo, disciplina) {
      if (disciplina == null) {
        return memo;
      } else {
        return memo + disciplina.creditos;
      }
    }, 0);
  };

  $scope.getPeriodo = function(semestre) {
    return _.find($scope.periodos, function(periodos) {
      return semestre === periodos.semestre;
    });
  };

  $scope.getDisciplina = function(disciplinaId) {
    return _.find($scope.disciplinasOfertadas, function(disciplina) {
      return disciplina.id === disciplinaId;
    });
  };

  $scope.addDisciplina = function(semestre, disciplinaId) {
    $scope.getPeriodo(semestre)
      .disciplinas.push($scope.getDisciplina(disciplinaId));

    $scope.cleanupPeriodos();
  };

  $scope.removeDisciplina = function(periodo, disciplinaId) {
    var toDelete = [];

    _.each($scope.periodos, function(periodo) {
      _.each(periodo.disciplinas, function(disciplina) {
        if (disciplina.id === disciplinaId) {
          toDelete.push(disciplina)
        } else if (_.contains(_.pluck(disciplina.dependencias, "id"), disciplinaId)) {
          toDelete.push(disciplina);
        }
      });
    });

    if (toDelete.length > 1) {
      if (!confirm("Apagar essa e as disciplinas que dependem dessa também?")) {
        return;
      }
    }

    var toDeleteIds = _.pluck(toDelete, "id");

    _.each($scope.periodos, function(periodo) {
      periodo.disciplinas = _.filter(periodo.disciplinas, function(disciplina) {
        return !_.contains(toDeleteIds, disciplina.id);
      });
    });

    $scope.disciplinasOfertadas = $scope.disciplinasOfertadas.concat(toDelete);

    $scope.cleanupPeriodos();
  };

  $scope.trimPeriodos = function() {
    var filled = [];
    var blanks = [];

    _.each($scope.periodos, function(periodo) {
      if (periodo.disciplinas.length === 0) {
        blanks.push(periodo);
      } else {
        if (blanks.length !== 0) {
          filled = filled.concat(blanks);
          blanks = [];
        }

        filled.push(periodo);
      }
    });
  };

  $scope.appendNextPeriodo = function() {
    if ($scope.periodos.length !== 0) {
      if (_.last($scope.periodos).disciplinas.length !== 0) {
        $scope.addPeriodo(_.last($scope.periodos).semestre + 1);
      }
    }
  };

  $scope.cleanupPeriodos = function() {
    $scope.trimPeriodos();
    $scope.appendNextPeriodo();
  };

  $scope.hasErrors = function() {
    return $scope.errors.length !== 0;
  };

  $scope.validate = function() {
    $scope.errors = [];

    $scope.validateMininumCreditos();
    $scope.validateMaximumCreditos();
    $scope.validateRequisitos();
  };

  $scope.validateMininumCreditos = function() {
    _.each($scope.periodos, function(periodo) {
      if (periodo.disciplinas.length !== 0 && $scope.getTotalCreditos(periodo.semestre) < MINIMO_CREDITOS) {
        $scope.errors.push(periodo.semestre + "º Período deve ter um mínimo de " + MINIMO_CREDITOS + " créditos.");
      }
    });
  };

  $scope.validateMaximumCreditos = function() {
    _.each($scope.periodos, function(periodo) {
      if (periodo.disciplinas.length !== 0 && $scope.getTotalCreditos(periodo.semestre) > MAXIMO_CREDITOS) {
        $scope.errors.push(periodo.semestre + "º Período deve ter um máximo de " + MAXIMO_CREDITOS + " créditos.");
      }
    });
  };

  $scope.validateRequisitos = function() {
    var satisfied = [];
    _.each($scope.periodos, function(periodo) {
      if (periodo.disciplinas.length !== 0) {
        _.each(periodo.disciplinas, function(disciplina){
          _.each(disciplina.dependencias, function (dependencia) {
            if (!_.contains(satisfied, dependencia.id)) {
              $scope.errors.push(disciplina.nome + " requer " + dependencia.nome);
            }
          });
        });
      }

      satisfied = satisfied.concat(_.pluck(periodo.disciplinas, "id"));
    });
  };

  $scope.$watch(function() {
    return _.reduce($scope.periodos, function(memo, periodo) {
      return memo + periodo.disciplinas.length;
    }, 0);
  }, function() {
    $scope.cleanupPeriodos();
    $scope.validate();
  });

  var bootstrap = function() {
    $http({method: "GET", url: "/disciplinas.json"})
      .success(function(data, status, headers, config) {
        $scope.disciplinasOfertadas = data;
      })
      .error(function(data, status, headers) {
        console.error("Não foi possível reaver as Disciplinas Ofertadas.");
      })
      .then(function (data) {
        $scope.addPeriodo(1);
        $scope.addDisciplina(1, "CALCULO1");
        $scope.addDisciplina(1, "VETORIAL");
        $scope.addDisciplina(1, "LPT");
        $scope.addDisciplina(1, "P1");
        $scope.addDisciplina(1, "IC");
        $scope.addDisciplina(1, "LP1");

        $scope.cleanupPeriodos();
      });
  };

  bootstrap();
});
