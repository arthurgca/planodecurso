var mainApp = angular.module("mainApp", ["ui.sortable"]);

mainApp.controller("PlanoDeCursoCtrl", function($scope, $http) {
  $scope.disciplinasOfertadas = [];

  $scope.disciplinasOfertadasRows = [];

  $scope.periodos = [];

  $scope.addPeriodo = function(semestre) {
    $scope.periodos.unshift({
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
      .disciplinas.unshift($scope.getDisciplina(disciplinaId));
  };

  $scope.removeDisciplina = function(periodo, disciplinaId) {
    periodo.disciplinas = _.filter(periodo.disciplinas, function(disciplina) {
      return disciplina.id !== disciplinaId;
    });
  };

  $scope.sortableOptions = {
    connectWith: ".sortable-list"
  };

  window.scope = $scope;

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
    });

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
