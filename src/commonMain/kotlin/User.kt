import kotlinx.serialization.Serializable

@Serializable
data class User( // пользователь
    var userType: TypeUser?,
    var name: String? = "",
    var id: String? = IdGenerator.generateId()
)
{

    companion object {
        const val path = "/userList"
    }
}

