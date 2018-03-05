<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Agencies</title>
</head>

<body>

<div class="content">
    <g:form controller="agencies" action="nearest">
        <label for="methods">Medio de pago</label>
        <g:select name="methodId" from="${methods}" id="methods" optionValue="name" optionKey="id"/>
        <br/>
        <label for="adress">Dirección</label>
        <g:textField name="adress" id="adress" style="width: 80%"/>
        <br/>
        <label for="distance">Distancia</label>
        <g:field type="number" name="distance" id="distance"/>
        <br/>
        <input class="save" type="submit" value="Buscar" />
    </g:form>
</div>