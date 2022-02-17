plugins {
    `maven-publish`
    signing
}

afterEvaluate {
    publishing {
        publications.all {
            if (this is MavenPublication) {
                pom {
                    name.set("ViewModel Easy Factories")
                    description.set("Instantiate ViewModels inside a trailing lambda")
                    url.set("https://github.com/olshevski/viewmodel-easy-factories")

                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://github.com/olshevski/viewmodel-easy-factories/blob/main/LICENSE")
                        }
                    }

                    developers {
                        developer {
                            id.set("olshevski")
                            name.set("Vitali Olshevski")
                            email.set("tech@olshevski.dev")
                            url.set("https://olshevski.dev")
                        }
                    }

                    scm {
                        connection.set("scm:git:https://github.com/olshevski/viewmodel-easy-factories.git")
                        developerConnection.set("scm:git:https://github.com/olshevski/viewmodel-easy-factories.git")
                        url.set("https://github.com/olshevski/viewmodel-easy-factories")
                    }
                }
            }
        }
    }
}

signing {
    useInMemoryPgpKeys(
        project.properties["signing.key"].toString(),
        project.properties["signing.password"].toString(),
    )
    sign(publishing.publications)
}