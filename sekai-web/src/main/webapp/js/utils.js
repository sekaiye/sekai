function msgBox(msg){
	layer.msg(msg);
}
function 	showDialog(title, url){
	var index = showDialogEx(title,url,'85%','80%');
	return index;
}
function showDialogSmall(title, url){
	var index = showDialogEx(title,url,'500px','400px');
	return index;
}
function showDialogEx(title, url, width, height){
	var index;
	var obj=layer.ready(function(){ 
		index=layer.open({
		    type: 2,
		    title: title,
		    maxmin: true,
		    area: [width, height],
		    content: url/*,
		    end: function(){
		      layer.tips('Hi', '#about', {tips: 1})
		    }*/
		  });
		});
	return index;
}
function closeDialog(layerIndex){
	layer.close(layerIndex);
}