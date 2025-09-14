package pl.kuczabinski.weights.weight

import pl.kuczabinski.weights.weight.WeightEntry

data class ApiResponse(
    val status: String,
    val data: List<WeightEntry>
)