package meli.agencies

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(redirect: '/agencies')
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
