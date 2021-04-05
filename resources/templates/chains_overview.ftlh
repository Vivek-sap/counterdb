<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>

<body>
<h1>Invocation chains</h1>
<br/>
<table border="1" style="border-collapse: collapse;">
<tr style="font-size: 1.5em; font-style:bold; background:lightgray">
    <td>Name</td>
    <td>Footprint</td>
    <td>Description</td>
</tr>
<#list chains as chain>
<tr>
    <td><a href="https://itwiki.kabeldeutschland.de/display/CCBSE/WCD+-+Chain+${chain.name}">${chain.name}</a></td>
    <td>${chain.footprint}</td>
    <td>${(chain.process.description)!"no description"}</td>
</tr>
</#list>
</table>
</body>
</html>

