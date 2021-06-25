
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
	<title>Patient Portal</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" >
</head>
<body>
	<div class="container">
		<label>Enter Oxygen Levels :</label>
		<input type="text" class="form-control form-control-sm"  name="vital" id="vital" style="display:inline-block; width:300px;margin-top:25px;"">
		
		<button onclick="sendVitals();" class="btn btn-success btn-sm">Submit</button>
		<br /><br />
		<table id="example" class="table">
			<thead>
				<tr>
					<th>Doctor</th>
					<th>Medicine</th>
					<th>Description</th>
				</tr>
			</thead>
			<tbody>
				<tr>
				</tr>
			</tbody>
		</table>
	</div>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" ></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" ></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" ></script>
	<script>
		var websocket=new WebSocket("ws://localhost:8080/micro-project5/VitalCheckEndPoint");
		websocket.onmessage=function processVital(vital){
			var jsonData=JSON.parse(vital.data);
			if(jsonData.message!=null)
			{
				var details=jsonData.message.split(',');
				var row=document.getElementById('example').insertRow();
				if(details.length>2)
				{
					row.innerHTML="<td>"+details[0]+"</td><td>"+details[1]+"</td><td>"+details[2]+"</td>";		
				}
				else
				{
					alert(details[0]+" has summoned an ambulance");
					row.innerHTML="<td>"+details[0]+"</td><td></td><td>"+details[1]+"</td>";		
				}
			}
		}
		function sendVitals()
		{
			websocket.send(vital.value);
			
			vital.value="";
			vital1.value= "";
		}
	</script>
</body>
</html>
