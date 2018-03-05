package meli.agencies

import grails.gorm.transactions.Transactional
import groovy.json.JsonSlurper

@Transactional
class AgenciesService {

    static JsonSlurper json = new JsonSlurper()

    def getPaymentMethods() {
        try {
            def url = new URL("https://api.mercadolibre.com/sites/MLA/payment_methods")
            def connection = (HttpURLConnection) url.openConnection()
            connection.setRequestMethod("GET")
            connection.setRequestProperty("Accept", "application/json")

            def methods = json.parse(connection.getInputStream()) as List<PaymentMethod>

            return [methods: filterMethods(methods)]
        }
        catch (IOException e) {
            log.error(e.stackTrace)
            return [error: 'No se pudieron obtener los medios de pago, intente nuevamente más tarde']
        }
    }

    def filterMethods(List<PaymentMethod> methods) {
        return methods.findAll { it.payment_type_id == 'ticket' }
    }

    def getLocation(result) {
        return new Location(
                lat: result.results[0].geometry.location.lat,
                lng: result.results[0].geometry.location.lng
        )
    }

    def getAgencies(result) {
        return result.results.collect {
            new Agency(
                    description: it.description,
                    distance: it.distance,
                    address: new Adress(
                            addressLine: it.address.address_line,
                            city: it.address.city,
                            country: it.address.country,
                            otherInfo: it.address.other_info,
                            state: it.address.state,
                            zipCode: it.address.zip_code,
                            location: new Location(
                                    lat: it.address.location.split(',')[0],
                                    lng: it.address.location.split(',')[1]
                            )))
        }
    }
}