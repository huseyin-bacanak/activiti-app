$( document ).ready(function() {
    $('.navbar-nav > li').removeClass('active');
    $('.navbar-nav > li:nth-child(3)').addClass('active');
    loadPage(0);
});

function loadPage(pageIndex){
    createAjaxGetRequest("/activiti-app/vacationRequest/pool2/page/"+pageIndex,onPageNumberLoad, onPageNumberLoadError);
}

function onPageNumberLoad(data){
    console.log(data);
    if(data.status==="SUCCESS"){
        var res= data.result;
        removePagination("poolTasksPagination");
        addPagination("poolTasksPagination",
                      res.pagination.fromIndex+1,
                      res.pagination.toIndex+1,
                      res.pagination.selectedIndex+1);
        removeExistingTasks();
        addTasks(data.result.tasks.data);
    } else {
        console.log(result.status);
    }
}

function onPageNumberLoadError(data){
    console.log(data);
}


$(document).on('click', '.statusBtn', function(event) {
    var processInstanceId=$(this).closest('tr').find(".processInstanceId").text();
    showModal(processInstanceId);
});

function showModal(processInstanceId){
    $("#processInstanceImg").attr('src', "http://localhost:9000/activiti/service/runtime/process-instances/"+processInstanceId+"/diagram");
    $('#processStatusModal').modal('show');

}

function removeExistingTasks() {
    $("#poolTable").find("tr:gt(0)").remove();
}

function addTasks(taskArray){
    var i=0;
    for(i=0;i<taskArray.length;i++){
        addTaskRow(taskArray[i]);
    }
}

function addTaskRow(task){
    $("#poolTable").find('tbody')
        .append($('<tr>')
            .append($('<td>')
                .append($('<p>')
                    .attr("class","processInstanceId")
                    .text(task.processInstanceId)))
            .append($('<td>')
                .append($('<p>').text(task.id)))
            .append($('<td>')
                .append($('<p>').text(getProcessVariable(task, "employeeName"))))
            .append($('<td>')
                .append($('<p>').text(getProcessVariable(task, "startDate"))))
            .append($('<td>')
                .append($('<p>').text(getProcessVariable(task, "numberOfDays"))))
            .append($('<td>')
                .append($('<p>').text(getProcessVariable(task, "vacationMotivation"))))
            .append($('<td>')
                .append($('<button>')
                    .attr("class", "btn statusBtn")
                    .text("show")))
            .append($('<td>')
                .append($('<button>')
                            .attr("class", "btn btn-primary")
                            .attr("onClick","loadPage(0)")))
    );
}

function getProcessVariable(task, varName){
    var i=0;
    var variables=task.variables;
    for(i=0; i<variables.length; i++){
        var variable=variables[i];
        if(variable.name===varName){
            return variable.value;
        }
    }
    return "NA";
}

function claimTask(taskId){

}