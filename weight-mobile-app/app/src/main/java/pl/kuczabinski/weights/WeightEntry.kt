package pl.kuczabinski.weights

data class WeightEntry(
    val id: Int,
    val value: Float,
    val user_id: Int,
    val created_at: String,
    val updated_at: String
)
