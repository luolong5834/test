var add_var = ".";
$(function(){
  $.datetimepicker.setLocale('zh');
  var dateOption = {format:'Y/m/d',timepicker:false}
  $('#beginCreateTime').datetimepicker(dateOption);
  $('#endCreateTime').datetimepicker(dateOption);

  $('#importBtn').click(function(){
      import_fun();
  })
});

//导出
var timeTask;
var import_fun = function(){
    var startTime = $('#beginCreateTime');
    var endTime = $('#endCreateTime');
    if(!$(startTime).val()){
       alert('请选择开始时间！')
       return false;
    }
    if(!$(endTime).val()){
       alert('请选择结束时间！');
       return false;
    }
    if($(startTime).val() > $(endTime).val()){
       alert('结束时间小于开始时间，请重新选择!');
       return false;
    }
    var exportFlag = false;
    var data = {"beginCreateTime":$(startTime).val(),"endCreateTime":$(endTime).val()};
    var callback = function(msg){
       $('#progressModal').modal('hide');
       if(!msg){
         alert('导出失败，请稍后再试！');
         return false;
       }
       var msgs = msg.split(":");
       if(msgs[0] == 'success'){
          alert('导出成功');
          window.location.href="/report/sxd/getExcel";
       }else{
          alert('导出失败，失败原因：'+msgs[1]);
          return false;
       }
    }
    var url = '/report/sxd/importSxdReport';
    $.get(url,data,callback);
    $('#progressSpan').text('正在分析数据，请勿刷新！');
    $('#progressModal').modal('show');
    var progressDiv = $('#progressDiv');
    $(progressDiv).css("width",'0%');
    timeTask = setInterval('exportedPercent()',1000);

}

function exportedPercent(){
   var url = '/report/sxd/exportedPercent';
   var progressDiv = $('#progressDiv');
   var progressSpan = $('#progressSpan');
   var callback = function(msg){
       $(progressDiv).css("width",msg);
       if(msg.indexOf('100') != -1){
          clearInterval(timeTask);
          $(progressSpan).text('开始生成excel，预计1-5分钟'+add_var);
       }
       if(add_var.length == 3){
          add_var = ".";
       }else{
          add_var +=".";
       }

   }
   $.get(url,callback);
}


