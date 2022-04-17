import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.reactivestreams.KMongo
import com.mongodb.ConnectionString

fun main()
{
    var userList = mutableListOf<User>(
        User(TypeUser.WORKER, "David"),
        User(TypeUser.WORKER, "Echach"),
        User(TypeUser.DRIVER, "Bob")
    )

    embeddedServer(Netty, 9090)
    {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Delete)
            anyHost()
        }
        install(Compression) {
            gzip()
        }


        routing {
            route(User.path) {
                get {
                    call.respondText(userList.toString())
                }
                post {
                    userList += call.receive<User>()
                    call.respond(HttpStatusCode.OK)
                }
                delete("/{id}") {
                    val id = call.parameters["id"] ?: error("Invalid delete request")
                    userList.removeIf { it.id == id }
                    call.respond(HttpStatusCode.OK)
                }
            }

            route("/") {
                get {
                    call.respondText(
                        this::class.java.classLoader.getResource("index.html")!!.readText(),
                        ContentType.Text.Html
                    )
                }
                static {
                    resources("")
                }
            }
        }
    }.start(wait = true)
}