var mainApp = angular.module("mainApp", ["ui.sortable"]);

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
    periodo.disciplinas = _.filter(periodo.disciplinas, function(disciplina) {
      return disciplina.id !== disciplinaId;
    });

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
    _.each($scope.periodos, function(periodo) {
      if (periodo.disciplinas.length !== 0) {
      }
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

        $scope.disciplinasOfertadasRows = _.groupBy($scope.disciplinasOfertadas, function(element, index) {
          return Math.floor(index / 6);
        });
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

$(function () {
  // -- renumber fields

  // Rename fields to have a coherent payload like:
  //
  //     periodos[0].disciplinas[0]
  //     periodos[0].disciplinas[1]
  //     periodos[0].disciplinas[2]
  //     ...
  //
  // Adapted from Play 2.x samples.
  var renumber = function() {
    $('.periodo').each(function(i) {
      $('.disciplina input', this).each(function() {
        $(this).attr('name', $(this).attr('name').replace(/periodos\[.+?\]/g, 'periodos[' + i + ']'));
      });

      $('.disciplina input', this).each(function(j){
        $(this).attr('name', $(this).attr('name').replace(/disciplinas\[.+?\]/g, 'disciplinas[' + j + ']'));
      });
    });
  };

  var submit = function() {
    $('#form').submit();
  };

  $("#disciplinas-alocadas .sortable-list").sortable({
    helper: "original",
    items: ".disciplina",
    opacity: 0.5,
    revert: true,
    stop: function() {
      renumber();
      submit();
    },
  });

  $("#disciplinas-ofertadas .disciplina").draggable({
    connectToSortable: "#disciplinas-alocadas .sortable-list",
    helper: "clone",
    opacity: 0.5,
    revert: "invalid",
    revertDuration: 250,
    snap: "#disciplinas-alocadas .sortable-list",
    snapMode: "inner"
  });

  $("#disciplinas-alocadas .disciplina").on('click', '.remove', function(e) {
    $(this).parents('.disciplina').remove();
    renumber();
    submit();
  });
});
