var xmlHttp = false;
	//获取服务器时间
	try {
		xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
		try {
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			xmlHttp = false;
		}
	}
	if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
		xmlHttp = new XMLHttpRequest();
	}
	xmlHttp.open("GET", "null.txt", false);
	xmlHttp.setRequestHeader("Range", "bytes=-1");
	xmlHttp.send(null);
	severtime = new Date(xmlHttp.getResponseHeader("Date"));
	//获取服务器日期
	var year = severtime.getFullYear();
	var month = severtime.getMonth() + 1;
	var date = severtime.getDate();
	//获取服务器时间
	var hour = severtime.getHours();
	var minu = severtime.getMinutes();
	var seco = severtime.getSeconds();
	
	//格式化输出服务器时间
	function getSeverTime() {
		seco++;
		if (seco == 60) {
			minu += 1;
			seco = 0;
		}
		if (minu == 60) {
			hour += 1;
			minu = 0;
		}
		if (hour == 24) {
			date += 1;
			hour = 0;
		}
		//日期处理
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			if (date == 32) {
				date = 1;
				month += 1;
			}
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			if (date == 31) {
				date = 1;
				month += 1;
			}
		} else if (month == 2) {
			if (year % 4 == 0 && year % 100 != 0) {//闰年处理
				if (date == 29) {
					date = 1;
					month += 1;
				}
			} else {
				if (date == 28) {
					date = 1;
					month += 1;
				}
			}
		}
		if (month == 13) {
			year += 1;
			month = 1;
		}
		sseco = addZero(seco);
		sminu = addZero(minu);
		shour = addZero(hour);
		sdate = addZero(date);
		smonth = addZero(month);
		syear = year;
		
		var one = syear+"-"+smonth+"-"+sdate+" "+shour+":"+sminu+":"+sseco;
		document.getElementById("writeTime").innerHTML =one;
		setTimeout("getSeverTime()", 1000);
	}
	function addZero(num) {
		num = Math.floor(num);
		return ((num <= 9) ? ("0" + num) : num);
	}
	
	function toHidden(){
		var p = confirm('您确定要提交本次报告吗?');
		if(p==true){
			if(document.getElementById("write_time").value=document.getElementById("writeTime").innerHTML){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	function toHidden2(){
		var p = confirm('您确定要修改本次汇报吗?');
		if(p==true){
			if(document.getElementById("write_time").value=document.getElementById("writeTime").innerHTML){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	function empty(){
		alert("empty");
		document.getElementById("myEditor").value='';
	}
	
	function initReadOnlyConfig() {  
	      var nodes = ['code','name'];//根据id设置只读      
	        for (var i=0;i<nodes.length;i++) {
	            var node = nodes[i]; 
	            node="#"+node;
	            $(node).attr({readonly:"true"});
	       }   
	 }
	 
	 function cancelReadOnly(){
	      var nodes = ['code','name'];//根据id取消只读属性     
	        for (var i=0;i<nodes.length;i++) {
	            var node = nodes[i]; 
	            node="#"+node;
	            $(node).removeAttr("readonly");
	       } 
	 }
	 
	
