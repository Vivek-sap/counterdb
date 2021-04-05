<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>

<body>
<h1>Tables Overview</h1>
<br/>
<#list tables as tablename, elements>
<h2>${tablename}</h2>
<table border="1" style="border-collapse: collapse;">
<tr style="font-size: 1.5em; font-style:bold; background:lightgray">
    <td>Field</td>
    <td>Type</td>
    <td>Null</td>
    <td>Key</td>
    <td>Default</td>
</tr>
<#list elements as element>
<tr>
    <td>${element[0]}</td>
    <td>${element[1]}</td>
    <td>${element[2]}}</td>
    <td>${element[3]}</td>
    <td>${(element[4])!"null"}</td>
</tr>
</#list>
</table>
</#list>
</body>
</html>