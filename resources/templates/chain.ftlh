<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>

<body>
<h1>${chain.name}</h1>
<br/>
<table border="1" style="border-collapse: collapse;">
<tr style="font-size: 1.5em; font-style:bold; background:lightgray">
    <td>Action</td>
    <td>Adapter</td>
    <td>Description</td>
    <td></td>
</tr>
<#list steps as step>
<tr>
    <td><a href="https://itwiki.kabeldeutschland.de/display/CCBSE/${step.adapter.name}+-+Action+${step.action}">${step.action}</a></td>
    <td>${step.adapter.name}</td>
    <td>${step.description}</td>
<#if step.adapter.name == "decision-adapter">
    <td>
    <#list decisions as footprint, footprintDecisions>
        <#if footprintDecisions[step.name]?? && (footprintDecisions[step.name]?size gt 0)>
        <table border="1">
            <tr style="font-style:bold; background:lightgray">
                <td colspan="2">Decisions ${footprint}</td>
            </tr>
            <tr style="font-style:bold; background:lightgray">
                <td>xpath</td>
                <td>chain</td>
            </tr>
            <#list footprintDecisions[step.name] as stepDecision>
            <tr>
                <td>${stepDecision.xpathExpression}</td>
                <td><a href="https://itwiki.kabeldeutschland.de/display/CCBSE/WCD+-+Chain+${stepDecision.nextInvocationChain.name}">${stepDecision.nextInvocationChain.name}</a></td>
            </tr>
            </#list>
        </table>
        </#if>
    </#list>
    </td>
</#if>
</tr>
</#list>
</table>

<#if errors?size gt 0>
<h4>error chains</h4>
<table border="1" style="border-collapse: collapse;">
    <tr style="font-size: 1.5em; font-style:bold; background:lightgray">
        <td>Error Code</td>
        <td>Error Adapter</td>
        <td>Chain</td>
        <td>Footprint</td>
    </tr>
    <#list errors as error>
    <tr>
        <td>${(error.errorCode!"-")}</td>
        <td>${(error.errorAdapter!"-")}</td>
        <td><a href="https://itwiki.kabeldeutschland.de/display/CCBSE/WCD+-+Chain+${error.errorInvocationChain.name}">${error.errorInvocationChain.name}</a></td>
        <td>${error.errorInvocationChain.footprint}</td>
    </tr>
    </#list>
</table>
</#if>

</body>
</html>
