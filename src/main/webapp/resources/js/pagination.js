/**
 * Removes all elements from pagination nav
 */
function removePagination(paginationId){
    $("#"+paginationId+" ul").text("");
}

/**
 *
 * @param paginationId
 * @param from
 * @param to
 * @param selected
 */
function addPagination(paginationId, from, to, selected){
    for (i = from; i <= to; i++) {
        var pageIndex=i-1;
        $("#"+paginationId+" ul").append(
            $('<li>').append(
                $('<a>').attr('onclick',"loadPage("+pageIndex+");").append(
                    $('<span>').attr('class', 'tab').append(i+"")
                ).attr("href","#")
            )
        );
        var selectedIndex = selected-from+1;
        $("#"+paginationId+" ul :nth-child("+selectedIndex+")").attr('class', 'active');
    }
}