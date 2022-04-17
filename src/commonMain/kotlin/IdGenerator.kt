import kotlin.random.Random

object IdGenerator
{
    fun generateId(): String  /** реализовать надёжный генератор */
    {
        var id: String = ""
        for (i in 0..7)
            id += ('a'..'z').random()
        return id
    }
}