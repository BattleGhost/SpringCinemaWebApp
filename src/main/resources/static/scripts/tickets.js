function customSubmit() {
    let data = [];
    const selected_seats = $('#seat-map .selected');
    for (let i = 0; i < selected_seats.length; i++) {
        let tmp = selected_seats[i].id.split("_");
        data.push({"rowNumber": tmp[0], "seatNumber": tmp[1]});
    }
    const dataToSend = JSON.stringify(data);
    fetch("", {
        credentials: "same-origin",
        mode: "same-origin",
        method: "post",
        headers: { "Content-Type": "application/json" },
        body: dataToSend
    }).then(response => location.replace(response.url));
    return false;
}

$(document).ready(function() {
    const $cart = $('#selected-seats'),
        $counter = $('#counter'),
        $total = $('#total'),
        $sButton = $('#sButton');

    disableLink($sButton);

    const sc = $('#seat-map').seatCharts({
        map: map,
        naming : {
            top : false,
            getLabel : function (character, row, column) {
                return column;
            }
        },
        legend : {
            node : $('#legend'),
            items : [
                [ 'a', 'available', option],
                [ 'a', 'unavailable', sold]
            ]
        },
        click: function () {
            if (this.status() === 'available') {
                $('<li>'+ row + ' ' +(this.settings.row+1)+' '+ seat + ' ' +this.settings.label+'</li>')
                    .attr('id', 'cart-item-'+this.settings.id)
                    .data('seatId', this.settings.id)
                    .appendTo($cart);

                $counter.text(sc.find('selected').length+1);
                $total.text(recalculateTotal(sc)+price);
                enableLink($sButton);
                return 'selected';
            } else if (this.status() === 'selected') {

                $counter.text(sc.find('selected').length-1);

                $total.text(recalculateTotal(sc)-price);
                $('#cart-item-'+this.settings.id).remove();
                if($('ul#selected-seats li').length < 1) {
                    disableLink($("#sButton"));
                }
                return 'available';
            } else if (this.status() === 'unavailable') {
                return 'unavailable';
            } else {
                return this.style();
            }
        }
    });

    sc.get(soldItems).status('unavailable');

});

function recalculateTotal(sc) {
    let total = 0;
    sc.find('selected').each(function () {
        total += price;
    });

    return total;
}

function enableLink(link) {
    link.css({'pointer-events' : '', 'cursor' : '', 'background-color' : '', 'border-color' : ''});
}

function disableLink(link) {
    link.css({'pointer-events' : 'none', 'cursor' : 'default', 'background-color' : 'grey', 'border-color' : 'black'});

}