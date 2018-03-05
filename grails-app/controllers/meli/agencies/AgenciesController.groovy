package meli.agencies

import groovy.json.JsonSlurper

class AgenciesController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def agenciesService

    def index() {
        agenciesService.getPaymentMethods()
    }

    def nearest(params) {
        def methodId = params.methodId
        def adress = params.adress?.replace(' ', '+')
        def distance = params.distance

        def geolocationUrl = new URL(
                "https://maps.googleapis.com/maps/api/geocode/json?address=${adress}" +
                        "&key=AIzaSyALXQwakJuPKvaeMxPLzz_9gSBvS_HfFtQ")

        def connection = (HttpURLConnection)geolocationUrl.openConnection()
        connection.setRequestMethod("GET")
        connection.setRequestProperty("Accept", "application/json")
        JsonSlurper json = new JsonSlurper()

        def location = agenciesService.getLocation(json.parse(connection.getInputStream()))

        def agenciesUrl = new URL(
                "https://api.mercadolibre.com/sites/MLA/payment_methods/${methodId}" +
                        "/agencies?near_to=${location?.lat},${location?.lng},${distance}" +
                        "&limit=10&offset=0"
        )

        connection = (HttpURLConnection)agenciesUrl.openConnection()
        connection.setRequestMethod("GET")
        connection.setRequestProperty("Accept", "application/json")

        def agencies = agenciesService.getAgencies(json.parse(connection.getInputStream()))

        ['agencyList': agencies, 'location': location, 'mapsKey': 'AIzaSyALXQwakJuPKvaeMxPLzz_9gSBvS_HfFtQ']
    }
}
