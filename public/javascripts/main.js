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
