package pl.kuczabinski.weights.remote

import pl.kuczabinski.weights.weight.WeightEntry

data class ApiResponse(
    val status: String,
    val data: List<WeightEntry>
)