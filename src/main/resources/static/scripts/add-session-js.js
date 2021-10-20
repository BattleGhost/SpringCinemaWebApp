let alert = $('#emptyFilterAlert');
let movies_select_group = $('#movie_select_group');
let movies_select = $('#movie_select');
alert.hide();
movies_select_group.hide();

function findFilms(filter) {
    if (filter.length < 1) {
        alert.show();
        return false;
    }
    alert.hide();
    movies_select_group.show();
    fetch(window.location.origin + '/api/v1/movies?filter=' + filter)
        .then(res => res.json())
        .then(res => {
            movies_select.empty();
            jQuery.each(res, function (i, val) {
                movies_select.append("<option value='" + val['id'] + "'>"+ "(" + val['id'] + ") " + val['title'] +"</option>");
            });
        });
}

let today = new Date();
let dd = today.getDate();
let mm = today.getMonth() + 1;
let yyyy = today.getFullYear();

if (dd < 10) {
    dd = '0' + dd;
}

if (mm < 10) {
    mm = '0' + mm;
}

today = yyyy + '-' + mm + '-' + dd;
let day_year_later = yyyy + 1 + '-' + mm + '-' + dd;

let date_field = $('#date');
date_field.attr("min", today);
date_field.attr("max", day_year_later);