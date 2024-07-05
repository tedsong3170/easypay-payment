plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "payment"
include("application:application-payment")
findProject(":application:application-payment")?.name = "application-payment"
include("application:application-token")
findProject(":application:application-token")?.name = "application-token"
include("domain")
include("interfaces:interface-payment")
findProject(":interfaces:interface-payment")?.name = "interface-payment"
include("interfaces:interface-token")
findProject(":interfaces:interface-token")?.name = "interface-token"
include("persistence:jpa")
findProject(":persistence:jpa")?.name = "jpa"
include("utils")
