package meli.agencies

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class AgenciesServiceSpec extends Specification implements ServiceUnitTest<AgenciesService> {

    void "test filter payment methods"() {
        setup:
        List<PaymentMethod> sampleMethods = []
        (1..3).each {n ->
            sampleMethods.add(new PaymentMethod(id: "method${n}", name: "Method ${n}", payment_type_id: 'ticket'))
        }
        (1..5).each {n ->
            sampleMethods.add(new PaymentMethod(id: "method${n}", name: "Method ${n}", payment_type_id: 'other'))
        }

        when:
        def methods = service.filterMethods(sampleMethods)

        then:
        methods.size() == 3
        methods.each {method ->
            method.payment_type_id == 'ticket'
        }
    }
}
