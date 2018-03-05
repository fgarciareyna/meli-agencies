<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <asset:javascript src="jquery-2.2.0.min.js"/>
    <style>
    #map {
        width: 100%;
        height: 400px;
        background-color: grey;
    }
    </style>
    <title>Agencies</title>
</head>

<body>

<div class="container">
    <h2>Agencias cercanas</h2>
    <table class="table table-hover table-bordered">
        <thead>
        <tr>
            <th>Descripción</th>
            <th>Distancia</th>
            <th>Ver</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${agencyList}">
            <tr>
                <td>${it.description}</td>
                <td>${it.distance}</td>
                <td>
                    <button type="button"
                            class="btn btn-info btn-sm"
                            data-toggle="modal"
                            data-target="#agencyDetail"
                    onclick="selectAgency(
                        '${it.description}',
                        '${it.address.addressLine}',
                        '${it.address.city}',
                        '${it.address.country}',
                        '${it.address.location.lat}',
                        '${it.address.location.lng}',
                        '${it.address.otherInfo}',
                        '${it.address.state}',
                        '${it.address.zipCode}'
                    )">Ver</button>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>

<div id="map"></div>

<!-- Modal -->
<div id="agencyDetail" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title" id="agencyDescription"></h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <ul id="details"></ul>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
            </div>
        </div>

    </div>
</div>


<g:javascript>
    function initMap() {
        var location = {lat: ${location.lat}, lng: ${location.lng}};
        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 15,
            center: location
        });
        new google.maps.Marker({
            position: location,
            label: 'Tu ubicación',
            map: map
        });
        <g:each in="${agencyList}">
            new google.maps.Marker({
                position: {lat: ${it.address.location.lat}, lng: ${it.address.location.lng}},
                label: "${it.description}",
                map: map
            });
        </g:each>
    }
</g:javascript>

<g:javascript>
    function selectAgency(description, address, city, country, lat, lng, info, state, zipCode) {
        $('#agencyDescription').text(description);

        $('#details')
                .empty()
                .append($("<li>").text('Dirección: ' + address))
                .append($("<li>").text('Ciudad: ' + city))
                .append($("<li>").text('País: ' + country))
                .append($("<li>").text('Localización: (' + lat + ', ' + lng + ')'))
                .append($("<li>").text('Otra información: ' + info))
                .append($("<li>").text('Estado: ' + state))
                .append($("<li>").text('Código postal: ' + zipCode))
    }
</g:javascript>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=${mapsKey}&callback=initMap">
</script>
</body>