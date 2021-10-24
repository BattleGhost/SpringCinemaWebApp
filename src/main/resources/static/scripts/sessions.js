$(document).ready(function(){

    $("#days a").click(function() {

        $("#days a").removeClass('active');

        $(this).addClass("active");

        $(".tab-content").hide();

        let selected_tab = $(this).attr("href");

        $(selected_tab).show();

        return false;
    });
});

function buyTicket(session) {
    for (let i = 0; i < 12; i++) {
        session = btoa(session);
    }
    location.href = "/tickets?session="+session;
}