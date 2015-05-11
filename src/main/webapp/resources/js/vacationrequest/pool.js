$( document ).ready(function() {
    $('.navbar-nav > li').removeClass('active');
    $('.navbar-nav > li:nth-child(3)').addClass('active');

    createAjaxGetRequest("/activiti-app/vacationRequest/pool/page/0",onPageNumberLoad, onPageNumberLoadError);

    function onPageNumberLoad(data){
        console.log(data);
        if(data.status==="SUCCESS"){
            var res= data.result;
            removePagination("poolTasksPagination");
            addPagination("poolTasksPagination",
                res.pageIndexFrom+1,res.pageIndexTo+1,res.pageIndexSelected+1);
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

});