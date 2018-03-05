package meli.agencies

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class AgenciesControllerSpec extends Specification implements ControllerUnitTest<AgenciesController> {

    def setup() {
        List<PaymentMethod> sampleMethods = []
        (1..3).each {n ->
            sampleMethods.add(new PaymentMethod(id: "method${n}", name: "Method ${n}", payment_type_id: 'ticket'))
        }
        controller.agenciesService = Mock(AgenciesService) {
            getPaymentMethods() >> sampleMethods
        }
    }

    //Index

    void "test index view"() {
        when: 'The index action is executed'
        controller.index()

        then: 'The view is correct'
        view == '/agencies/index.gsp'
    }

    void "test index model"() {
        when: 'The index action is executed'
        def model = controller.index()

        then: 'The model is correct'
        model.methods
        model.methods.size() == 3
    }
}
