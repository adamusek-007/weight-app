package pl.kuczabinski.weights

data class ApiResponse(
    val status: String,
    val data: List<WeightEntry>
)